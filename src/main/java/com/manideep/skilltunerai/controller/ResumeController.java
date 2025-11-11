package com.manideep.skilltunerai.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manideep.skilltunerai.dto.ResumeJDListResDTO;
import com.manideep.skilltunerai.dto.ResumeRequestDTO;
import com.manideep.skilltunerai.dto.ResumeResponseDTO;
import com.manideep.skilltunerai.service.ResumeService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/resume")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping
    public ResponseEntity<Void> saveNewResume(@RequestBody ResumeRequestDTO resumeRequestDTO) {
        resumeService.saveResumeAndUpload(resumeRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
    

    @GetMapping("/{resume-id}")
    public ResponseEntity<ResumeResponseDTO> getResumeDetails(@PathVariable("resume-id") long id) {
        return ResponseEntity.ok(resumeService.getResumeResponseDTO(id));
    }
    
    @GetMapping
    public ResponseEntity<List<ResumeResponseDTO>> getAllResumesOfCurrUser() {
        return ResponseEntity.ok(resumeService.getResumeDTOsOfCurrUser());
    }

    @GetMapping("/jd-list")
    public ResponseEntity<List<ResumeJDListResDTO>> getAllJdListGroupByResume() {
        return ResponseEntity.ok(resumeService.getResumesOfCurrUserIfJDNotEmpty());
    }
    
    @DeleteMapping("/{resume-id}")
    public ResponseEntity<Void> deleteResumeById(@PathVariable("resume-id") long id) {
        resumeService.deleteAResume(id);
        return ResponseEntity.ok(null);
    }

}
