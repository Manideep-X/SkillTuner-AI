package com.manideep.skilltunerai.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.manideep.skilltunerai.dto.ResumeRequestDTO;
import com.manideep.skilltunerai.dto.ResumeResponseDTO;
import com.manideep.skilltunerai.entity.Resume;
import com.manideep.skilltunerai.entity.Users;
import com.manideep.skilltunerai.exception.DuplicateValueException;
import com.manideep.skilltunerai.exception.FileLoadingException;
import com.manideep.skilltunerai.mapper.ResumeMapper;
import com.manideep.skilltunerai.repository.ResumeRepository;
import com.manideep.skilltunerai.repository.UsersRepository;
import com.manideep.skilltunerai.util.PdfDocParserUtil;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;

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

    @Override
    public void saveResumeAndUpload(ResumeRequestDTO resumeRequestDTO) throws IllegalArgumentException, IOException, PersistenceException {

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
        String fileName = null;
        try {
            fileName = resumeRequestDTO.getResumeFile().getOriginalFilename();
        } catch (Exception e) {
            throw new IOException("Error occured while reading the uploaded file!");
        }

        // Checking the file's extention whether it matches pdf, doc, or docx
        String fileExtention = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
        if (fileName == null || !fileExtention.equals("pdf") || !fileExtention.equals("doc") || !fileExtention.equals("docx"))
            throw new IllegalArgumentException("Only PDF, DOC, and DOCX file extensions are supported!");
            
        // Gets the texts from the resume by parsing it
        String resumeContent = parseTheResume(resumeRequestDTO.getResumeFile(), fileExtention);

        // Uploading the resume to Cloudinary and obtaining details
        Map<String, Object> uploadDetails = uploadResume(resumeRequestDTO.getResumeFile());

        // Map the resume request DTO to the resume entity
        Resume newResume = resumeMapper.resumeReqToResumeObj(
            resumeRequestDTO, 
            uploadDetails.get("secure_url").toString(),
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
    private Map<String, Object> uploadResume(MultipartFile resume) throws IOException, PersistenceException {

        try {

            return cloudinary.uploader().upload(
                resume.getBytes(), 
                ObjectUtils.asMap(
                    "folder", "resume/", // puts every resume in the resume folder
                    "resource_type", "auto" // automatically detects the file type
                )
            );
            
        } catch (IOException e) {
            throw new IOException("Error occured while reading the uploaded file!");
        } catch (Exception e) {
            throw new PersistenceException("Error occured while uploading the file to Cloudinary!");
        }

    }

    // Method to parse the resume and return the content as string
    private String parseTheResume(MultipartFile resume, String fileExtension) throws FileLoadingException, IOException {
        
        // Gets the file name
        String fileName = resume.getOriginalFilename().toString();
        
        if (fileName == null) throw new IOException("Invalid name of the file!");

        try {
            switch (fileExtension) {
                case "pdf":
                    return PdfDocParserUtil.extractTextFromPdf(resume);
                case "docx":
                    return PdfDocParserUtil.extractTextFromDocx(resume);
                case "doc":
                    return PdfDocParserUtil.extractTextFromDoc(resume);   
                default:
                    throw new IOException("Only PDF, DOC, and DOCX file extensions are supported!");
            }
        } catch (FileLoadingException e) {
            throw new FileLoadingException(e.getMessage());
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
        
    }

    @Override
    public void deleteAResume(long id) throws SecurityException {
        
        Users currUser = authService.currentlyLoggedinUser();
        Resume resume = getResumeById(id);

        // Checking if the resume belongs to the user
        if (!doesResumeByIdExistsForCurrUser(id)) {
            throw new SecurityException("Can't delete the resume that doesn't exists!");
        }
        
        // This will save the user and cascade save the resume as well
        currUser.removeResume(resume);

        // Saves the modified user which triggers a cascade delete to remove the resume from the resume table
        // No need of resumeRepository.delete(resume) as orphanRemoval is marked true in the users entity
        usersRepository.save(currUser);

    }

    @Override
    public Resume getResumeById(long id) throws EntityNotFoundException {
        
        // Need to check if the resume exists for current user
        if (!doesResumeByIdExistsForCurrUser(id)) {
            throw new EntityNotFoundException("This resume doesn't exists!");
        }
        
        return resumeRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("This resume doesn't exists!"));
    }

    @Override
    public List<Resume> getResumesOfCurrUser() throws EntityNotFoundException {
        return resumeRepository
            .findAllByUser(authService.currentlyLoggedinUser());
    }

    @Override
    public ResumeResponseDTO getResumeResponseDTO(long id) {
        return resumeMapper.resumeObjToResumeRes(getResumeById(id));
    }

    @Override
    public boolean doesResumeByIdExistsForCurrUser(long resumeId) throws EntityNotFoundException {
        
        Users currUser = authService.currentlyLoggedinUser();
        Resume resume = resumeRepository.findById(resumeId)
            .orElseThrow(() -> new EntityNotFoundException("This resume doesn't exists!"));

        // This checks if the currently logged-in user have this resume
        if (resume.getUser().getId() != currUser.getId()) {
            return false;
        }

        return true;
        
    }

}
