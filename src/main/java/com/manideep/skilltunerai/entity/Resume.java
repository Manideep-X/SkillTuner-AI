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
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "resume_table", 
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "resumeTitle"})
)
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "resume_title", nullable = false)
    private String resumeTitle;

    @Column(name = "resume_url", nullable = false)
    private String resumeUrl;

    @Column(name = "cloudinary_public_id", nullable = false)
    private String cloudinaryPublicId;

    @Column(name = "resume_extension", nullable = false)
    private String resumeExtension;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobDescription> jobDescriptions = new ArrayList<>();

    public Resume() {
    }
    public Resume(String resumeTitle, String resumeUrl, String cloudinaryPublicId, String resumeExtension, String content) {
        this.resumeTitle = resumeTitle;
        this.resumeUrl = resumeUrl;
        this.cloudinaryPublicId = cloudinaryPublicId;
        this.resumeExtension = resumeExtension;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getResumeTitle() {
        return resumeTitle;
    }
    public void setResumeTitle(String resumeTitle) {
        this.resumeTitle = resumeTitle;
    }
    
    public String getResumeUrl() {
        return resumeUrl;
    }
    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }
    
    public String getCloudinaryPublicId() {
        return cloudinaryPublicId;
    }
    public void setCloudinaryPublicId(String cloudinaryPublicId) {
        this.cloudinaryPublicId = cloudinaryPublicId;
    }
    
    public String getResumeExtension() {
        return resumeExtension;
    }
    public void setResumeExtension(String resumeExtension) {
        this.resumeExtension = resumeExtension;
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
    public JobDescription addJD(JobDescription job) {
        jobDescriptions.add(job);
        job.setResume(this);
        return job;
    }
    public void removeJD(JobDescription job) {
        jobDescriptions.remove(job);
        job.setResume(null);
    }

}
