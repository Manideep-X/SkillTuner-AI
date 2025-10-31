package com.manideep.skilltunerai.service;

import com.manideep.skilltunerai.dto.JobDesRequestDTO;
import com.manideep.skilltunerai.entity.JobDescription;
import com.manideep.skilltunerai.mapper.JobDesMapper;
import com.manideep.skilltunerai.repository.JobDesRepository;

public class JobDesServiceImpl implements JobDesService {

    private final JobDesRepository jobDesRepository;
    private final JobDesMapper jobDesMapper;

    public JobDesServiceImpl(JobDesRepository jobDesRepository, JobDesMapper jobDesMapper) {
        this.jobDesRepository = jobDesRepository;
        this.jobDesMapper = jobDesMapper;
    }

    @Override
    public void saveJobDescription(JobDesRequestDTO jobDesRequestDTO) {
        
        JobDescription jobDescription = jobDesMapper.jdRequestToJDObj(jobDesRequestDTO, null);
        
    }    

}
