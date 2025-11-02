package com.manideep.skilltunerai.service;

import java.io.IOException;
import java.util.List;

import com.manideep.skilltunerai.dto.ResumeRequestDTO;
import com.manideep.skilltunerai.dto.ResumeResponseDTO;
import com.manideep.skilltunerai.entity.Resume;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;

public interface ResumeService {

    // Form submittion for new resume and set active resume if available
    void saveResumeAndUpload(ResumeRequestDTO resumeRequestDTO) throws IllegalArgumentException, IOException, PersistenceException;

    // Method to delete a resume by it's ID
    void deleteAResume(long id) throws SecurityException;

    // Method to get a resume by it's ID
    Resume getResumeById(long id) throws EntityNotFoundException;

    // Method to get a list of resume of the currently logged-in user
    List<Resume> getResumesOfCurrUser() throws EntityNotFoundException;

    // Method to get a resume response DTO by it's ID
    ResumeResponseDTO getResumeResponseDTO(long id);

    // Method that return true if the resume exists for the current user
    boolean doesResumeByIdExistsForCurrUser(long resumeId) throws EntityNotFoundException;

}
