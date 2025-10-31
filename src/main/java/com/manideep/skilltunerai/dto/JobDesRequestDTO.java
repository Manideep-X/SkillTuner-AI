package com.manideep.skilltunerai.dto;

public class JobDesRequestDTO {

    String jobTitle;
    String companyName;
    String description;
    
    public JobDesRequestDTO() {
    }
    public JobDesRequestDTO(String jobTitle, String companyName, String description) {
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.description = description;
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

}
