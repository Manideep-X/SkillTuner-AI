package com.manideep.skilltunerai.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ResumeRequestDTO {

    @NotBlank(message = "Resume title is required!")
    private String resumeTitle;

    @NotNull(message = "Resume file is required!")
    private MultipartFile resumeFile;
    
    public ResumeRequestDTO() {
    }
    public ResumeRequestDTO(String resumeTitle, MultipartFile resumeFile) {
        this.resumeTitle = resumeTitle;
        this.resumeFile = resumeFile;
    }

    public String getResumeTitle() {
        return resumeTitle;
    }
    public void setResumeTitle(String resumeTitle) {
        this.resumeTitle = resumeTitle;
    }

    public MultipartFile getResumeFile() {
        return resumeFile;
    }
    public void setResumeFile(MultipartFile resumeFile) {
        this.resumeFile = resumeFile;
    }

}
