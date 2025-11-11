package com.manideep.skilltunerai.controller;

import org.springframework.web.bind.annotation.RestController;

import com.manideep.skilltunerai.dto.JobDesRequestDTO;
import com.manideep.skilltunerai.dto.JobDesResponseDTO;
import com.manideep.skilltunerai.service.JobDesService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("resume/{resumeId}/job-description")
public class JobDescriptionController {

    private final JobDesService jobDesService;

    public JobDescriptionController(JobDesService jobDesService) {
        this.jobDesService = jobDesService;
    }

    @PostMapping
    public ResponseEntity<JobDesResponseDTO> saveJobDescriptionForm(@RequestBody JobDesRequestDTO jobDesRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(jobDesService.saveJobDescription(jobDesRequestDTO));
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<JobDesResponseDTO> getJDById(@PathVariable("id") long jdId, @PathVariable long resumeId) {
        return ResponseEntity.ok(jobDesService.getJdDTOIfLinkedWithResume(jdId, resumeId));
    }
    
    @GetMapping
    public ResponseEntity<List<String>> getJDTitles(@PathVariable long resumeId) {
        return ResponseEntity.ok(jobDesService.getsJobTitlesByResumeId(resumeId));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJDById(@PathVariable("id") long jdId, @PathVariable long resumeId) {
        jobDesService.deleteJDByItsId(jdId, resumeId);
        return ResponseEntity.ok(null);
    }

}
