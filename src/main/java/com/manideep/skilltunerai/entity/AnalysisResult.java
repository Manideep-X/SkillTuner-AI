package com.manideep.skilltunerai.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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

    @Column(columnDefinition = "TEXT")
    private String feedback;

    // Allows Hibernate to send/receive JSON between Java and PostgreSQL.
    @JdbcTypeCode(SqlTypes.JSON)
    // Tells Hibernate to generate the column in the DB as jsonb.
    @Column(columnDefinition = "jsonb")
    // Converts a Java object into a JSON String before saving.
    // Converts JSON String from DB back into a Java object when loading.
    // @Convert(converter = ListToJsonConverter.class)
    private List<String> strengths;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    // @Convert(converter = ListToJsonConverter.class)
    private List<String> improvements;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "missing_skills", columnDefinition = "jsonb")
    // @Convert(converter = ListToJsonConverter.class)
    private List<String> missingSkills;

    @Column(name = "creation_time", updatable = false)
    @CreationTimestamp
    private LocalDateTime creationTime;

    public AnalysisResult() {
    }
    public AnalysisResult(int matchScore, String feedback, List<String> strengths, List<String> improvements,
            List<String> missingSkills, LocalDateTime creationTime) {
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
