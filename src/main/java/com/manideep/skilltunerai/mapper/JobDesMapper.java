package com.manideep.skilltunerai.mapper;

import com.manideep.skilltunerai.dto.JobDesRequestDTO;
import com.manideep.skilltunerai.entity.JobDescription;
import com.manideep.skilltunerai.entity.Resume;

public class JobDesMapper {

    public JobDescription jdRequestToJDObj(JobDesRequestDTO jobDesRequestDTO, Resume resume) {

        JobDescription jobDescription = new JobDescription();

        jobDescription.setCompanyName(jobDesRequestDTO.getCompanyName());
        jobDescription.setDescription(jobDesRequestDTO.getDescription());
        jobDescription.setJobTitle(jobDesRequestDTO.getJobTitle());
        jobDescription.setResume(resume);
        jobDescription.setAnalysisResult(null);

        return jobDescription;

    }

}
