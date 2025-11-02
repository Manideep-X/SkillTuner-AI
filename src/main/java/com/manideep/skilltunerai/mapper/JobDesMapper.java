package com.manideep.skilltunerai.mapper;

import com.manideep.skilltunerai.dto.JobDesRequestDTO;
import com.manideep.skilltunerai.dto.JobDesResponseDTO;
import com.manideep.skilltunerai.entity.JobDescription;
import com.manideep.skilltunerai.entity.Resume;

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
        jobDesResponseDTO.setCompanyName(jobDescription.getCompanyName());
        jobDesResponseDTO.setDescription(jobDesResponseDTO.getDescription());
        jobDesResponseDTO.setResumeId(resumeId);

        return jobDesResponseDTO;

    }

}
