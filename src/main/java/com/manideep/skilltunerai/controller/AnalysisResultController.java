package com.manideep.skilltunerai.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manideep.skilltunerai.dto.AnalysisResultResponseDTO;
import com.manideep.skilltunerai.service.AnalysisResultService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("resume/{resumeId}/job-description/{jdId}/analysed-result")
public class AnalysisResultController {

    private final AnalysisResultService analysisResultService;

    public AnalysisResultController(AnalysisResultService analysisResultService) {
        this.analysisResultService = analysisResultService;
    }

    @PostMapping
    public ResponseEntity<AnalysisResultResponseDTO> generateResultResponse(@PathVariable long resumeId, @PathVariable long jdId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping
    public ResponseEntity<AnalysisResultResponseDTO> getResultResponse(@PathVariable long resumeId, @PathVariable long jdId) {
        return ResponseEntity.ok(analysisResultService.getAnalysedDTOByJDAndResume(jdId, resumeId));
    }

}
