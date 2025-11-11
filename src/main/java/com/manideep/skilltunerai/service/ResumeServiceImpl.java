package com.manideep.skilltunerai.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.manideep.skilltunerai.dto.ResumeJDListResDTO;
import com.manideep.skilltunerai.dto.ResumeRequestDTO;
import com.manideep.skilltunerai.dto.ResumeResponseDTO;
import com.manideep.skilltunerai.entity.Resume;
import com.manideep.skilltunerai.entity.Users;
import com.manideep.skilltunerai.exception.DuplicateValueException;
import com.manideep.skilltunerai.exception.FileLoadingException;
import com.manideep.skilltunerai.exception.FileUploadException;
import com.manideep.skilltunerai.mapper.ResumeMapper;
import com.manideep.skilltunerai.repository.ResumeRepository;
import com.manideep.skilltunerai.repository.UsersRepository;
import com.manideep.skilltunerai.util.PdfDocParserUtil;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ResumeServiceImpl implements ResumeService {

    private final AuthService authService;
    private final UsersRepository usersRepository;
    private final ResumeRepository resumeRepository;
    private final ResumeMapper resumeMapper;
    private final Cloudinary cloudinary;

    public ResumeServiceImpl(AuthService authService, ResumeRepository resumeRepository, Cloudinary cloudinary, ResumeMapper resumeMapper, UsersRepository usersRepository) {
        this.authService = authService;
        this.usersRepository = usersRepository;
        this.resumeRepository = resumeRepository;
        this.resumeMapper = resumeMapper;
        this.cloudinary = cloudinary;
    }

    public static final Logger logger = LoggerFactory.getLogger(ResumeServiceImpl.class);

    @Override
    public void saveResumeAndUpload(ResumeRequestDTO resumeRequestDTO) {

        // Fetching the currently logged in user
        Users currUser = authService.currentlyLoggedinUser();

        // Need to check if the labelled resume name is unique for each user or not
        if (resumeRepository.findByResumeTitleAndUser(
                resumeRequestDTO.getResumeTitle(),
                currUser
            ).isPresent()) {
            throw new DuplicateValueException("This resume name already exists! Try different one");
        }

        // Getting file name from the uploaded resume
        String fileName = resumeRequestDTO.getResumeFile().getOriginalFilename();
        
        // Checking the file's extention whether it matches pdf, doc, or docx
        if (fileName == null) {
            throw new FileLoadingException("Error occured while reading the uploaded file!");
        }
        String fileExtention = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
        if (!(  fileExtention.equals("pdf") || 
                fileExtention.equals("doc") || 
                fileExtention.equals("docx"))
        )
            throw new IllegalArgumentException("Only PDF, DOC, and DOCX file extensions are supported!");
            
        // Gets the texts from the resume by parsing it
        String resumeContent = parseTheResume(resumeRequestDTO.getResumeFile(), fileExtention);

        // Uploading the resume to Cloudinary and obtaining details
        Map<String, Object> uploadDetails = uploadResume(resumeRequestDTO.getResumeFile());

        // Map the resume request DTO to the resume entity
        Resume newResume = resumeMapper.resumeReqToResumeObj(
            resumeRequestDTO, 
            uploadDetails.get("secure_url").toString(),
            uploadDetails.get("public_id").toString(),
            fileExtention,
            resumeContent,
            currUser
        );

        // helper method to link the newly created resume with the user and vise versa
        currUser.addResume(newResume);

        // Saves the current user entity object to the DB, it will automatically save the resume because of cascadeType.ALL
        usersRepository.save(currUser);

    }

    // Method to upload the resume to Cloudinary and return the details including URL
    @SuppressWarnings("unchecked")
    private Map<String, Object> uploadResume(MultipartFile resume) throws FileUploadException {

        try {
            return cloudinary.uploader().upload(
                resume.getInputStream(), 
                ObjectUtils.asMap(
                    "folder", "resume/", // puts every resume in the resume folder
                    "public_id", System.currentTimeMillis()+"_"+resume.getOriginalFilename(), // sets unique id for each file in cloudinary
                    "resource_type", "auto" // automatically detects the file type
                )
            );
            
        } catch (Exception e) {
            throw new FileUploadException("Error occured while uploading or reading file!");
        }

    }

    // Method to parse the resume and return the content as string
    private String parseTheResume(MultipartFile resume, String fileExtension) throws FileLoadingException {
        
        // Gets the file name
        String fileName = resume.getOriginalFilename();
        
        if (fileName == null) throw new FileLoadingException("Invalid name of the file!");

        try {
            switch (fileExtension) {
                case "pdf":
                    return PdfDocParserUtil.extractTextFromPdf(resume);
                case "docx":
                    return PdfDocParserUtil.extractTextFromDocx(resume);
                case "doc":
                    return PdfDocParserUtil.extractTextFromDoc(resume);   
                default:
                    throw new FileLoadingException("Only PDF, DOC, and DOCX file extensions are supported!");
            }
        } catch (Exception e) {
            throw new FileLoadingException(e.getMessage());
        }
        
    }

    @Override
    public void deleteAResume(long id) throws SecurityException {
        
        Users currUser = authService.currentlyLoggedinUser();
        Resume resume = null;
        try {
            resume = getResumeByIdForCurrUser(id);
        } catch (Exception e) {
            throw new SecurityException("Can't delete resume that doesn't exists!");
        }

        // Trys to delete resume from cloudinary
        try {
            logger.info("Deleting file from Cloudinary: {}", resume.getCloudinaryPublicId());
            cloudinary.uploader().destroy(resume.getCloudinaryPublicId(), ObjectUtils.emptyMap());
        } catch (Exception e) {
            logger.warn("Failed to delete file from Cloudinary: {} {}", resume.getCloudinaryPublicId(), e);
        }
        
        // This will save the user and cascade save the resume as well
        currUser.removeResume(resume);

        // Saves the modified user which triggers a cascade delete to remove the resume from the resume table
        // No need of resumeRepository.delete(resume) as orphanRemoval is marked true in the users entity
        usersRepository.save(currUser);

    }

    @Override
    public Resume getResumeByIdForCurrUser(long id) throws EntityNotFoundException {
        Users currUser = authService.currentlyLoggedinUser();
        return resumeRepository.findByIdAndUserId(id, currUser.getId())
            .orElseThrow(() -> new EntityNotFoundException("This resume doesn't exists"));
    }

    @Override
    public List<Resume> getResumesOfCurrUser() {
        return resumeRepository
            .findAllByUser(authService.currentlyLoggedinUser());
    }
    
    @Override
    public List<ResumeResponseDTO> getResumeDTOsOfCurrUser() {
        List<Resume> resumeEntities = getResumesOfCurrUser();
        List<ResumeResponseDTO> resumeDTOs = new ArrayList<>();
        for (Resume resume : resumeEntities) {
            resumeDTOs.add(resumeMapper.resumeObjToResumeRes(resume));
        }
        return resumeDTOs;
    }

    @Override
    public ResumeResponseDTO getResumeResponseDTO(long id) {
        return resumeMapper.resumeObjToResumeRes(getResumeByIdForCurrUser(id));
    }

    @Override
    public List<ResumeJDListResDTO> getResumesOfCurrUserIfJDNotEmpty() {
        
        // Fetches only the resumes which has atleast one job description, for currently logged-in user
        List<Resume> resumesWithJD = resumeRepository.findDistinctByUserIdAndJobDescriptionsIsNotEmpty(authService.currentlyLoggedinUser().getId());

        // Map the extracted list to the appropiate DTO
        List<ResumeJDListResDTO> resumeJDListResDTOs = resumeMapper.resumeObjToListRes(resumesWithJD);

        return resumeJDListResDTOs;
        
    }

}
