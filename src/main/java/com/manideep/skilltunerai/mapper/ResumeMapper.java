package com.manideep.skilltunerai.mapper;

import org.springframework.stereotype.Component;

import com.manideep.skilltunerai.dto.ResumeRequestDTO;
import com.manideep.skilltunerai.dto.ResumeResponseDTO;
import com.manideep.skilltunerai.entity.Resume;
import com.manideep.skilltunerai.entity.Users;

@Component
public class ResumeMapper {

    public Resume resumeReqToResumeObj(
        ResumeRequestDTO resumeRequestDTO, String resumeUrl, String fileExtension, String content, Users users
    ) {

        Resume resume = new Resume();

        resume.setResumeTitle(resumeRequestDTO.getResumeTitle());
        resume.setResumeUrl(resumeUrl);
        resume.setResumeExtension(fileExtension);
        resume.setContent(content);
        resume.setUser(users);
        resume.setJobDescriptions(null);

        return resume;

    }

    public ResumeResponseDTO resumeObjToResumeRes(Resume resume) {

        ResumeResponseDTO resumeResponseDTO = new ResumeResponseDTO();

        resumeResponseDTO.setId(resume.getId());
        resumeResponseDTO.setResumeTitle(resume.getResumeTitle());
        resumeResponseDTO.setResumeExtension(resume.getResumeExtension());
        resumeResponseDTO.setResumeUrl(resume.getResumeUrl());

        return resumeResponseDTO;

    }

}
