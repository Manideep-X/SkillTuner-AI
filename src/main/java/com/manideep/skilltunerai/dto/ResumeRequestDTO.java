package com.manideep.skilltunerai.dto;

import org.springframework.web.multipart.MultipartFile;

public class ResumeRequestDTO {

    private String resumeTitle;
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
