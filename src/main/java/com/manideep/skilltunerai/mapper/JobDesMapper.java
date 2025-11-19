package com.manideep.skilltunerai.mapper;

import org.springframework.stereotype.Component;

import com.manideep.skilltunerai.dto.JobDesRequestDTO;
import com.manideep.skilltunerai.dto.JobDesResponseDTO;
import com.manideep.skilltunerai.entity.JobDescription;
import com.manideep.skilltunerai.entity.Resume;

@Component
public class JobDesMapper {

    public JobDescription jdRequestToJDObj(JobDesRequestDTO jobDesRequestDTO, Resume resume) {

        JobDescription jobDescription = new JobDescription();

        jobDescription.setJobTitle(jobDesRequestDTO.getJobTitle());
        jobDescription.setCompanyName(jobDesRequestDTO.getCompanyName());
        jobDescription.setDescription(jobDesRequestDTO.getDescription());
        jobDescription.setResume(resume);
        jobDescription.setAnalysisResult(null);

        return jobDescription;

    }

    public JobDesResponseDTO jdObjToJdResponse(JobDescription jobDescription, long resumeId) {

        JobDesResponseDTO jobDesResponseDTO = new JobDesResponseDTO();

        jobDesResponseDTO.setId(jobDescription.getId());
        jobDesResponseDTO.setJobTitle(jobDescription.getJobTitle());
        jobDesResponseDTO.setCompanyName(jobDescription.getCompanyName());
        jobDesResponseDTO.setDescription(jobDescription.getDescription());
        jobDesResponseDTO.setResumeId(resumeId);

        return jobDesResponseDTO;

    }

}
