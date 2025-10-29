package com.manideep.skilltunerai.dto;

import org.springframework.web.multipart.MultipartFile;

public class ResumeRequestDAO {

    private String resumeTitle;
    private MultipartFile resumeFile;
    private boolean isResumeActive;
    
    public ResumeRequestDAO() {
    }
    public ResumeRequestDAO(String resumeTitle, MultipartFile resumeFile, boolean isResumeActive) {
        this.resumeTitle = resumeTitle;
        this.resumeFile = resumeFile;
        this.isResumeActive = isResumeActive;
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

    public boolean isResumeActive() {
        return isResumeActive;
    }
    public void setResumeActive(boolean isResumeActive) {
        this.isResumeActive = isResumeActive;
    }

}
