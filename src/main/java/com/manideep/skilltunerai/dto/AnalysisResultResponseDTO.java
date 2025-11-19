package com.manideep.skilltunerai.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AnalysisResultResponseDTO {

    private int matchScore;
    private String feedback;
    private List<String> strengths;
    private List<String> improvements;
    private List<String> missingSkills;
    private LocalDateTime creationTime;
    
    public AnalysisResultResponseDTO() {
    }
    public AnalysisResultResponseDTO(int matchScore, String feedback, List<String> strengths, List<String> improvements,
            List<String> missingSkills, LocalDateTime creationTime) {
        this.matchScore = matchScore;
        this.feedback = feedback;
        this.strengths = strengths;
        this.improvements = improvements;
        this.missingSkills = missingSkills;
        this.creationTime = creationTime;
    }
    
    public int getMatchScore() {
        return matchScore;
    }
    public void setMatchScore(int matchScore) {
        this.matchScore = matchScore;
    }
    
    public String getFeedback() {
        return feedback;
    }
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
    
    public List<String> getStrengths() {
        return strengths;
    }
    public void setStrengths(List<String> strengths) {
        this.strengths = strengths;
    }
    
    public List<String> getImprovements() {
        return improvements;
    }
    public void setImprovements(List<String> improvements) {
        this.improvements = improvements;
    }
    
    public List<String> getMissingSkills() {
        return missingSkills;
    }
    public void setMissingSkills(List<String> missingSkills) {
        this.missingSkills = missingSkills;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }
    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

}
