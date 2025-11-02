package com.manideep.skilltunerai.dto;

public class JobDesRequestDTO {

    private String jobTitle;
    private String companyName;
    private String description;
    private long resumeId;
    
    public JobDesRequestDTO() {
    }
    public JobDesRequestDTO(String jobTitle, String companyName, String description, long resumeId) {
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.description = description;
        this.resumeId = resumeId;
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

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    public long getResumeId() {
        return resumeId;
    }
    public void setResumeId(long resumeId) {
        this.resumeId = resumeId;
    }

}
