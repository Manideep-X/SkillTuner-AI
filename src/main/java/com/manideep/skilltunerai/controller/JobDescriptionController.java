package com.manideep.skilltunerai.controller;

import org.springframework.web.bind.annotation.RestController;

import com.manideep.skilltunerai.dto.JobDesRequestDTO;
import com.manideep.skilltunerai.dto.JobDesResponseDTO;
import com.manideep.skilltunerai.service.JobDesService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("resumes/{resumeId}/job-descriptions")
public class JobDescriptionController {

    private final JobDesService jobDesService;

    public JobDescriptionController(JobDesService jobDesService) {
        this.jobDesService = jobDesService;
    }

    @PostMapping
    public ResponseEntity<JobDesResponseDTO> createJobDescription(@Valid @RequestBody JobDesRequestDTO jobDesRequestDTO, @PathVariable long resumeId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobDesService.saveJobDescription(jobDesRequestDTO, resumeId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDesResponseDTO> getJobDescriptionById(@PathVariable("id") long jdId, @PathVariable long resumeId) {
        return ResponseEntity.ok(jobDesService.getJdDTOIfLinkedWithResume(jdId, resumeId));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobDescriptionById(@PathVariable("id") long jdId, @PathVariable long resumeId) {
        jobDesService.deleteJDByItsId(jdId, resumeId);
        return ResponseEntity.noContent().build();
    }

}
