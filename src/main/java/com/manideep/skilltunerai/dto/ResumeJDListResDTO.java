package com.manideep.skilltunerai.dto;

import java.time.LocalDateTime;

public class ResumeJDListResDTO {

    private long resumeId;
    private long jdId;
    private String resumeTitle;
    private String resumeExtension;
    private String jobTitle;
    private String companyName;
    private LocalDateTime creationTime;
    
    public ResumeJDListResDTO() {
    }
    public ResumeJDListResDTO(long resumeId, long jdId, String resumeTitle, String resumeExtension, String jobTitle, String companyName, LocalDateTime creationTime) {
        this.resumeId = resumeId;
        this.jdId = jdId;
        this.resumeTitle = resumeTitle;
        this.resumeExtension = resumeExtension;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.creationTime = creationTime;
    }

    public long getResumeId() {
        return resumeId;
    }
    public void setResumeId(long resumeId) {
        this.resumeId = resumeId;
    }
    
    public long getJdId() {
        return jdId;
    }
    public void setJdId(long jdId) {
        this.jdId = jdId;
    }
    
    public String getResumeTitle() {
        return resumeTitle;
    }
    public void setResumeTitle(String resumeTitle) {
        this.resumeTitle = resumeTitle;
    }
    
    public String getResumeExtension() {
        return resumeExtension;
    }
    public void setResumeExtension(String resumeExtension) {
        this.resumeExtension = resumeExtension;
    }
    
    public String getJobTitle() {
        return jobTitle;
    }
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public LocalDateTime getCreationTime() {
        return creationTime;
    }
    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

}
