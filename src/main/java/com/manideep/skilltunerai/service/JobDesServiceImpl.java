package com.manideep.skilltunerai.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.manideep.skilltunerai.dto.JobDesRequestDTO;
import com.manideep.skilltunerai.dto.JobDesResponseDTO;
import com.manideep.skilltunerai.entity.JobDescription;
import com.manideep.skilltunerai.entity.Resume;
import com.manideep.skilltunerai.mapper.JobDesMapper;
import com.manideep.skilltunerai.repository.JobDesRepository;
import com.manideep.skilltunerai.repository.ResumeRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;

@Service
public class JobDesServiceImpl implements JobDesService {

    private final JobDesRepository jobDesRepository;
    private final JobDesMapper jobDesMapper;
    private final ResumeService resumeService;
    private final ResumeRepository resumeRepository;
    private final AnalysisResultService analysisResultService;

    public JobDesServiceImpl(JobDesRepository jobDesRepository, JobDesMapper jobDesMapper, ResumeService resumeService, ResumeRepository resumeRepository, AnalysisResultService analysisResultService) {
        this.jobDesRepository = jobDesRepository;
        this.jobDesMapper = jobDesMapper;
        this.resumeService = resumeService;
        this.resumeRepository = resumeRepository;
        this.analysisResultService = analysisResultService;
    }

    // Transactional annotation is added as there are two write operations
    @Override
    @Transactional
    public void saveJobDescription(JobDesRequestDTO jobDesRequestDTO) throws SecurityException, PersistenceException {
        
        // Checks if the resume exists for currently logged-in user or not
        Resume resume = resumeService.getResumeByIdForCurrUser(jobDesRequestDTO.getResumeId());

        // Convert the job description DTO to entity before saving it
        JobDescription jobDescription = jobDesMapper.jdRequestToJDObj(jobDesRequestDTO, resume);

        // Add the new job description to the resume entity's job description list, and set this resume to the job desciption's entity
        JobDescription newJD = resume.addJD(jobDescription);

        try {
            // Forces Hibernate to save immediately the resume to the DB, which cascade saves the job description as well
            resumeRepository.saveAndFlush(resume);

            // Generates and save the Gemini's response into the database
            analysisResultService.generateAndSaveResponse(jobDesRequestDTO.getResumeId(), newJD.getId());

        } catch (Exception e) {
            throw new PersistenceException("Error occured while persisting JD to DB", e);
        }
        } catch (Exception e) {
            throw new PersistenceException("Error occured while persisting JD to DB", e);
        }
        
    }

    @Override
    public JobDesResponseDTO getsJDByItsId(long id) throws EntityNotFoundException {
        
        JobDescription jobDescription = jobDesRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("This job description doesn't exists!"));

        return jobDesMapper.jdObjToJdResponse(jobDescription, jobDescription.getResume().getId());
        
    }

    @Override
    public List<String> getsJobTitlesByResumeId(long resumeId) {
        
        return jobDesRepository.findAllJobTitlesByResume_Id(resumeId);

    }

    @Override
    public void deleteJDByItsId(long jdId, long resumeId) throws EntityNotFoundException {
        
        JobDescription jobDescription = jobDesRepository.findById(jdId).orElseThrow(() -> new EntityNotFoundException("This job description doesn't exists!"));
        
        // Need to check if the job description exists for the given resume
        Resume resume = resumeService.getResumeByIdForCurrUser(resumeId);
        if (jobDescription.getResume().getId() != resume.getId()) {
            throw new SecurityException("Can't delete the analysis history that doesn't exists!");
        }

        // Delete remove the JD from the JD list of the resume and set JD's resume to null
        resume.removeJD(jobDescription);

        // Saving the resume will delete the JD from it's resume, and cascade delete the orphan JD
        resumeRepository.save(resume);
        
    }

    @Override
    public JobDescription getJDIfLinkedWithResume(long jdId, Resume resume) throws EntityNotFoundException {
        
        JobDescription jobDescription = jobDesRepository.findById(jdId).orElseThrow(() -> new EntityNotFoundException("This job description doesn't exists!"));
        
        if (jobDescription.getResume().getId() != resume.getId()) {
            throw new EntityNotFoundException("This job description doesn't exists!");
        }

        return jobDescription;
        
    }

}
