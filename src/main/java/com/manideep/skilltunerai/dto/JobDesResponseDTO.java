package com.manideep.skilltunerai.dto;

public class JobDesResponseDTO {

    private long id;
    private String jobTitle;
    private String companyName;
    private String description;
    private long resumeId;
    
    public JobDesResponseDTO() {
    }
    public JobDesResponseDTO(long id, String jobTitle, String companyName, String description, long resumeId) {
        this.id = id;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.description = description;
        this.resumeId = resumeId;
    }
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
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
