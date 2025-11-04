package com.manideep.skilltunerai.mapper;

import org.springframework.stereotype.Component;

import com.manideep.skilltunerai.dto.AnalysisResultResponseDTO;
import com.manideep.skilltunerai.entity.AnalysisResult;

@Component
public class AnalysisMapper {

    public AnalysisResultResponseDTO analysisObjToAnalysisResponse(AnalysisResult analysisResult) {

        AnalysisResultResponseDTO analysisResultDTO = new AnalysisResultResponseDTO();

        analysisResultDTO.setMatchScore(analysisResult.getMatchScore());
        analysisResultDTO.setFeedback(analysisResult.getFeedback());
        analysisResultDTO.setStrengths(analysisResult.getStrengths());
        analysisResultDTO.setImprovements(analysisResult.getImprovements());
        analysisResultDTO.setMissingSkills(analysisResult.getMissingSkills());
        analysisResultDTO.setCreationTime(analysisResult.getCreationTime());

        return analysisResultDTO;

    }

    public AnalysisResult analysisResponseToAnalysisObj(AnalysisResultResponseDTO responseDTO) {

        AnalysisResult analysisResult = new AnalysisResult();

        analysisResult.setMatchScore(responseDTO.getMatchScore());
        analysisResult.setFeedback(responseDTO.getFeedback());
        analysisResult.setStrengths(responseDTO.getStrengths());
        analysisResult.setImprovements(responseDTO.getImprovements());
        analysisResult.setMissingSkills(responseDTO.getMissingSkills());

        return analysisResult;

    }

}
