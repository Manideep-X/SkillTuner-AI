package com.manideep.skilltunerai.service;

import java.io.IOException;

import com.manideep.skilltunerai.dto.ResumeRequestDTO;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;

public interface ResumeService {

    // Form submittion for new resume and set active resume if available
    void saveResumeAndUpload(ResumeRequestDTO resumeRequestDTO) throws IllegalArgumentException, IOException, PersistenceException;

    // Method to delete a resume by it's ID
    void deleteAResume(long id) throws EntityNotFoundException, SecurityException;

}
