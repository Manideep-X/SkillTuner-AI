package com.manideep.skilltunerai.service;

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

@Service
public class JobDesServiceImpl implements JobDesService {

    private final JobDesRepository jobDesRepository;
    private final JobDesMapper jobDesMapper;
    private final ResumeService resumeService;
    private final ResumeRepository resumeRepository;

    public JobDesServiceImpl(JobDesRepository jobDesRepository, JobDesMapper jobDesMapper, ResumeService resumeService, ResumeRepository resumeRepository) {
        this.jobDesRepository = jobDesRepository;
        this.jobDesMapper = jobDesMapper;
        this.resumeService = resumeService;
        this.resumeRepository = resumeRepository;
    }

    // Transactional annotation is added as there are two write operations
    @Transactional
    @Override
    public JobDesResponseDTO saveJobDescription(JobDesRequestDTO jobDesRequestDTO, long resumeId) {
        
        // Checks if the resume exists for currently logged-in user or not
        Resume resume = resumeService.getResumeByIdForCurrUser(resumeId);

        // Convert the job description DTO to entity before saving it
        JobDescription jobDescription = jobDesMapper.jdRequestToJDObj(jobDesRequestDTO, resume);

        // Saves the job description
        JobDescription savedJD = jobDesRepository.save(jobDescription);

        // Add the new job description to the resume entity's job description list, and set this resume to the job desciption's entity
        resume.addJD(savedJD);

        // Even though the resume saved to the DB will cascade saves the job description as well
        // But JD needed to be returned
        resume = resumeRepository.save(resume);
        return jobDesMapper.jdObjToJdResponse(savedJD, resume.getId());
        
    }

    @Override
    public JobDesResponseDTO getJdDTOIfLinkedWithResume(long jdId, long resumeId) throws EntityNotFoundException {
        
        JobDescription jobDescription;
        try {
            Resume resume = resumeService.getResumeByIdForCurrUser(resumeId);
            jobDescription = getJDIfLinkedWithResume(jdId, resume);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        }

        return jobDesMapper.jdObjToJdResponse(jobDescription, jobDescription.getResume().getId());
        
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
