package com.manideep.skilltunerai.dto;

public class ResumeResponseDTO {

    private long id;
    private String resumeTitle;
    private String resumeUrl;
    private String resumeExtension;
    private long fileSizeInBytes;
    
    public ResumeResponseDTO() {
    }
    public ResumeResponseDTO(long id, String resumeTitle, String resumeUrl, String resumeExtension, long fileSizeInBytes) {
        this.id = id;
        this.resumeTitle = resumeTitle;
        this.resumeUrl = resumeUrl;
        this.resumeExtension = resumeExtension;
        this.fileSizeInBytes = fileSizeInBytes;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
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

    public long getFileSizeInBytes() {
        return fileSizeInBytes;
    }
    public void setFileSizeInBytes(long fileSizeInBytes) {
        this.fileSizeInBytes = fileSizeInBytes;
    }
    
    public String getResumeExtension() {
        return resumeExtension;
    }
    public void setResumeExtension(String resumeExtension) {
        this.resumeExtension = resumeExtension;
    }

}
