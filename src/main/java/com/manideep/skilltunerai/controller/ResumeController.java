package com.manideep.skilltunerai.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manideep.skilltunerai.dto.ResumeJDListResDTO;
import com.manideep.skilltunerai.dto.ResumeRequestDTO;
import com.manideep.skilltunerai.dto.ResumeResponseDTO;
import com.manideep.skilltunerai.service.ResumeService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/resumes")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Void> createNewResume(@Valid @ModelAttribute ResumeRequestDTO resumeRequestDTO) {
        resumeService.saveResumeAndUpload(resumeRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{resume-id}")
    public ResponseEntity<ResumeResponseDTO> getResumeDetails(@PathVariable("resume-id") long id) {
        return ResponseEntity.ok(resumeService.getResumeResponseDTO(id));
    }
    
    @GetMapping
    public ResponseEntity<List<ResumeResponseDTO>> getAllResumesOfCurrUser() {
        return ResponseEntity.ok(resumeService.getResumeDTOsOfCurrUser());
    }

    @GetMapping("/job-descriptions")
    public ResponseEntity<List<ResumeJDListResDTO>> getAllJdListGroupByResume() {
        return ResponseEntity.ok(resumeService.getResumesOfCurrUserIfJDNotEmpty());
    }
    
    @DeleteMapping("/{resume-id}")
    public ResponseEntity<Void> deleteResumeById(@PathVariable("resume-id") long id) {
        resumeService.deleteAResume(id);
        // HTTP response code: 204
        return ResponseEntity.noContent().build();
    }

}
