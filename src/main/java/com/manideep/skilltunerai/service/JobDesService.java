package com.manideep.skilltunerai.service;

import com.manideep.skilltunerai.dto.JobDesRequestDTO;

public interface JobDesService {

    // Save the job description to the DB
    void saveJobDescription(JobDesRequestDTO jobDesRequestDTO);

}
