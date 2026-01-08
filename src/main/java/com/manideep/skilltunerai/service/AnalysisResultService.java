package com.manideep.skilltunerai.service;

import java.util.Optional;

import com.manideep.skilltunerai.dto.AnalysisResultResponseDTO;

public interface AnalysisResultService {

    // Generates gemini response using resume and job description
    AnalysisResultResponseDTO generateAndSaveResponse(long resumeId, long jdId);

    // Parses the returned gemini response into analysis result entity's object
    AnalysisResultResponseDTO parseTheResponse(String responseText);

    // Returns response DTO for the analysed result
    Optional<AnalysisResultResponseDTO> getAnalysedDTOByJDAndResume(long resumeId, long jdId);

}
