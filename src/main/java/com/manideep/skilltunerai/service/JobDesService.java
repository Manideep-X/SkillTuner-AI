package com.manideep.skilltunerai.service;

import java.util.List;

import com.manideep.skilltunerai.dto.JobDesRequestDTO;
import com.manideep.skilltunerai.dto.JobDesResponseDTO;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;

public interface JobDesService {

    // Save the job description to the DB
    void saveJobDescription(JobDesRequestDTO jobDesRequestDTO) throws SecurityException, PersistenceException;

    // Returns the DTO that can be send to the frontend
    JobDesResponseDTO getsJDByItsId(long id) throws EntityNotFoundException;

    // Returns job titles entered for one resume
    List<String> getsJobTitlesByResumeId(long resumeId);

    // Deletes the job description and it's result by JD's ID of the current user
    void deleteJDByItsId(long jdId, long resumeId) throws EntityNotFoundException;

}
