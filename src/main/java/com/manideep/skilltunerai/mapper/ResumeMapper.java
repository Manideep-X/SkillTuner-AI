package com.manideep.skilltunerai.mapper;

import org.springframework.stereotype.Component;

import com.manideep.skilltunerai.dto.ResumeRequestDAO;
import com.manideep.skilltunerai.entity.Resume;
import com.manideep.skilltunerai.entity.Users;

@Component
public class ResumeMapper {

    public Resume ResumeReqToResumeObj(ResumeRequestDAO resumeRequestDAO, String resumeUrl, String content, Users users) {

        Resume resume = new Resume();

        resume.setResumeTitle(resumeRequestDAO.getResumeTitle());
        resume.setResumeUrl(resumeUrl);
        resume.setContent(content);
        resume.setUser(users);
        resume.setJobDescriptions(null);

        return resume;

    }

}
