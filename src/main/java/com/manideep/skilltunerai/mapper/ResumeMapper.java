package com.manideep.skilltunerai.mapper;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.manideep.skilltunerai.dto.ResumeJDListResDTO;
import com.manideep.skilltunerai.dto.ResumeRequestDTO;
import com.manideep.skilltunerai.dto.ResumeResponseDTO;
import com.manideep.skilltunerai.entity.Resume;
import com.manideep.skilltunerai.entity.Users;

@Component
public class ResumeMapper {

    public Resume resumeReqToResumeObj(
        ResumeRequestDTO resumeRequestDTO, String resumeUrl, String cloudinaryPublicId, String fileExtension, String content, Users users
    ) {

        Resume resume = new Resume();

        resume.setResumeTitle(resumeRequestDTO.getResumeTitle());
        resume.setResumeUrl(resumeUrl);
        resume.setCloudinaryPublicId(cloudinaryPublicId);
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

    public List<ResumeJDListResDTO> resumeObjToListRes(List<Resume> resumes) {

        return resumes.stream()
            // flatMap() flattens the inner list into single continuous stream instead of a list of lists.
            .flatMap(resume -> resume.getJobDescriptions().stream()
                .map(jobDescription -> new ResumeJDListResDTO(
                    resume.getId(),
                    jobDescription.getId(),
                    resume.getResumeTitle(),
                    resume.getResumeExtension(),
                    jobDescription.getJobTitle(),
                    jobDescription.getCompanyName(),
                    jobDescription.getCreationTime(),
                    // jobDescription.getAnalysisResult() != null ? jobDescription.getAnalysisResult().getCreationTime() : null
                    Optional.ofNullable(jobDescription.getAnalysisResult())
                            .map(analysisResult -> analysisResult.getCreationTime())
                            .orElse(null)
                )))
            .toList();

    }

}
