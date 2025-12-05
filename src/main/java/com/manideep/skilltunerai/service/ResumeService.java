package com.manideep.skilltunerai.service;

import java.util.List;

import com.manideep.skilltunerai.dto.ResumeJDListResDTO;
import com.manideep.skilltunerai.dto.ResumeRequestDTO;
import com.manideep.skilltunerai.dto.ResumeResponseDTO;
import com.manideep.skilltunerai.entity.Resume;

import jakarta.persistence.EntityNotFoundException;

public interface ResumeService {

    // Form submittion for new resume and set active resume if available
    void saveResumeAndUpload(ResumeRequestDTO resumeRequestDTO);

    // Method to delete a resume by it's ID
    void deleteAResume(long id) throws EntityNotFoundException;

    // Method to get a resume by it's ID
    Resume getResumeByIdForCurrUser(long id) throws EntityNotFoundException;

    // Method to get a list of resume of the currently logged-in user
    List<Resume> getResumesOfCurrUser();

    // Method to get a list of resume DTOs of the currently logged-in user
    List<ResumeResponseDTO> getResumeDTOsOfCurrUser();

    // Method to get a resume response DTO by it's ID
    ResumeResponseDTO getResumeResponseDTO(long id);

    // Method to get a list of resume DTOs of the currently logged-in user with atleast one job description
    List<ResumeJDListResDTO> getResumesOfCurrUserIfJDNotEmpty();

}
