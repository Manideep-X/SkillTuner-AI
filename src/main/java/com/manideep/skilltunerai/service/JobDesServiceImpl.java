package com.manideep.skilltunerai.service;

import java.util.List;

import com.manideep.skilltunerai.dto.JobDesRequestDTO;
import com.manideep.skilltunerai.dto.JobDesResponseDTO;
import com.manideep.skilltunerai.entity.JobDescription;
import com.manideep.skilltunerai.entity.Resume;
import com.manideep.skilltunerai.mapper.JobDesMapper;
import com.manideep.skilltunerai.repository.JobDesRepository;
import com.manideep.skilltunerai.repository.ResumeRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;

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

    @Override
    public void saveJobDescription(JobDesRequestDTO jobDesRequestDTO) throws SecurityException, PersistenceException {
        
        // Checks if the resume exists for currently logged-in user or not
        Resume resume = resumeService.getResumeById(jobDesRequestDTO.getResumeId());
        if (!resumeService.doesResumeByIdExistsForCurrUser(jobDesRequestDTO.getResumeId())) {
            throw new SecurityException("No such resume exists to save this job description!");
        }

        // Convert the job description DTO to entity before saving it
        JobDescription jobDescription = jobDesMapper.jdRequestToJDObj(jobDesRequestDTO, resume);

        // Add hte new job description to the resume entity's job description list, and set this resume to the job desciption's entity
        resume.addJD(jobDescription);

        try {
            resumeRepository.save(resume);
        } catch (Exception e) {
            throw new PersistenceException("Error occured while persisting JD to DB");
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
        Resume resume = resumeService.getResumeById(resumeId);
        if (jobDescription.getResume().getId() != resume.getId()) {
            throw new SecurityException("Can't delete the anlysis history that doesn't exists!");
        }

        // Then, need to check if the resume exists for the current user
        if (!resumeService.doesResumeByIdExistsForCurrUser(resumeId)) {
            throw new SecurityException("Can't delete the anlysis history that doesn't exists!");
        }

        // Delete remove the JD from the JD list of the resume and set JD's resume to null
        resume.removeJD(jobDescription);

        // Saving the resume will delete the JD from it's resume, and cascade delete the orphan JD
        resumeRepository.save(resume);
        
    }

}
