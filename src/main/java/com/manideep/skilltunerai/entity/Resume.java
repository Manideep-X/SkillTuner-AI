package com.manideep.skilltunerai.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "resume_table")
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "resume_url", nullable = false)
    private String resumeUrl;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobDescription> jobDescriptions = new ArrayList<>();

    public Resume() {
    }
    public Resume(String resumeUrl, String content) {
        this.resumeUrl = resumeUrl;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }
    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }
    
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    
    public Users getUser() {
        return user;
    }
    public void setUser(Users user) {
        this.user = user;
    }

    public List<JobDescription> getJobDescriptions() {
        return jobDescriptions;
    }
    public void setJobDescriptions(List<JobDescription> jobDescriptions) {
        this.jobDescriptions = jobDescriptions;
    }

    // Helper methods
    public void addJD(JobDescription job) {
        jobDescriptions.add(job);
        job.setResume(this);
    }
    public void removeJD(JobDescription job) {
        jobDescriptions.remove(job);
        job.setResume(null);
    }

}
