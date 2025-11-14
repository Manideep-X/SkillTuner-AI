package com.manideep.skilltunerai.service;

import com.manideep.skilltunerai.dto.JobDesRequestDTO;
import com.manideep.skilltunerai.dto.JobDesResponseDTO;
import com.manideep.skilltunerai.entity.JobDescription;
import com.manideep.skilltunerai.entity.Resume;

import jakarta.persistence.EntityNotFoundException;

public interface JobDesService {

    // Save the job description to the DB
    JobDesResponseDTO saveJobDescription(JobDesRequestDTO jobDesRequestDTO, long resumeId);

    // Returns the DTO that can be send to the frontend
    JobDesResponseDTO getJdDTOIfLinkedWithResume(long jdId, long resumeId) throws EntityNotFoundException;

    // Returns job description if the given resume is linked with it.
    JobDescription getJDIfLinkedWithResume(long jdId, Resume resume) throws EntityNotFoundException;

    // Deletes the job description and it's result by JD's ID of the current user
    void deleteJDByItsId(long jdId, long resumeId) throws EntityNotFoundException;

}
