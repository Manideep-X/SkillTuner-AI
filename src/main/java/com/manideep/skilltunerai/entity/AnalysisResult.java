package com.manideep.skilltunerai.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "analysis_result_table")
public class AnalysisResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "match_score")
    private int matchScore;

    private String feedback;

    @Column(columnDefinition = "jsonb")
    private List<String> strengths;

    @Column(columnDefinition = "jsonb")
    private List<String> improvements;

    @Column(name = "missing_skills", columnDefinition = "jsonb")
    private List<String> missingSkills;

    @Column(name = "creation_time", updatable = false)
    @CreationTimestamp
    private LocalDateTime creationTime;

    public AnalysisResult(long id, int matchScore, String feedback, List<String> strengths, List<String> improvements,
            List<String> missingSkills, LocalDateTime creationTime) {
        this.id = id;
        this.matchScore = matchScore;
        this.feedback = feedback;
        this.strengths = strengths;
        this.improvements = improvements;
        this.missingSkills = missingSkills;
        this.creationTime = creationTime;
    }

    public long getId() {
        return id;
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

}
