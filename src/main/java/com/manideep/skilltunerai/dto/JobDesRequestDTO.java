package com.manideep.skilltunerai.dto;

import jakarta.validation.constraints.NotBlank;

public class JobDesRequestDTO {

    @NotBlank(message = "Job description title is required!")
    private String jobTitle;

    @NotBlank(message = "Company name is required!")
    private String companyName;
    
    @NotBlank(message = "The job description is required!")
    private String description;
    
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
