package com.manideep.skilltunerai.mapper;

import org.springframework.stereotype.Component;

import com.manideep.skilltunerai.dto.ResumeRequestDTO;
import com.manideep.skilltunerai.entity.Resume;
import com.manideep.skilltunerai.entity.Users;

@Component
public class ResumeMapper {

    public Resume resumeReqToResumeObj(ResumeRequestDTO resumeRequestDTO, String resumeUrl, String content, Users users) {

        Resume resume = new Resume();

        resume.setResumeTitle(resumeRequestDTO.getResumeTitle());
        resume.setResumeUrl(resumeUrl);
        resume.setContent(content);
        resume.setUser(users);
        resume.setJobDescriptions(null);

        return resume;

    }

}
