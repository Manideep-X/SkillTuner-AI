package com.manideep.skilltunerai.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.errors.ApiException;
import com.google.genai.types.GenerateContentResponse;
import com.google.gson.JsonParseException;
import com.manideep.skilltunerai.dto.AnalysisResultResponseDTO;
import com.manideep.skilltunerai.entity.AnalysisResult;
import com.manideep.skilltunerai.entity.JobDescription;
import com.manideep.skilltunerai.entity.Resume;
import com.manideep.skilltunerai.exception.JacksonParsingException;
import com.manideep.skilltunerai.mapper.AnalysisMapper;
import com.manideep.skilltunerai.repository.JobDesRepository;
import com.manideep.skilltunerai.util.GeminiPromptCreationUtil;

@Service
public class AnalysisResultServiceImpl implements AnalysisResultService {

    private final JobDesRepository jobDesRepository;
    private final JobDesService jobDesService;
    private final ResumeService resumeService;
    private final AnalysisMapper analysisMapper;
    private final Client geminiClient;
    private final GeminiModelManagerService geminiModelManagerService;

    public AnalysisResultServiceImpl(JobDesService jobDesService, ResumeService resumeService, Client geminiClient, AnalysisMapper analysisMapper, JobDesRepository jobDesRepository, GeminiModelManagerService geminiModelManagerService) {
        this.jobDesRepository = jobDesRepository;
        this.jobDesService = jobDesService;
        this.resumeService = resumeService;
        this.analysisMapper = analysisMapper;
        this.geminiClient = geminiClient;
        this.geminiModelManagerService = geminiModelManagerService;
    }

    @Override
    public AnalysisResultResponseDTO generateAndSaveResponse(long resumeId, long jdId) {
        
        // Need to check if the resumes belong to the currently logged-in user
        Resume resume = resumeService.getResumeByIdForCurrUser(resumeId);

        // Need to check if the job description belongs to that resume
        JobDescription jobDescription = jobDesService.getJDIfLinkedWithResume(jdId, resume);

        // Gets prompt for generating results
        String prompt = GeminiPromptCreationUtil.createPrompt(
                resume.getContent(), 
                jobDescription.getJobTitle(), 
                jobDescription.getCompanyName(), 
                jobDescription.getDescription());
        
        // Getting response from Gemini, and handling API exception
        GenerateContentResponse response = generateContentResponse(prompt);

        // Gets the parsed JSON as DTO
        AnalysisResultResponseDTO responseDTO = parseTheResponse(response.text());

        // Map to analysed result from response DTO to entity
        AnalysisResult analysisResult = analysisMapper.analysisResponseToAnalysisObj(responseDTO);
        
        // This adds the analysed result to the job description and save it to the database, which cascade saves the analysis result to the database
        jobDescription.setAnalysisResult(analysisResult);
        jobDesRepository.save(jobDescription);

        // return the saved value
        return responseDTO;
        
    }

    @Override
    public AnalysisResultResponseDTO parseTheResponse(String responseText) {
        
        try {
            // Removes all markdown bold symbols
            responseText = responseText.replace("**", "");

            // Removes everything except the JSON file
            int openParenthesis = responseText.indexOf('{');
            int closeParenthesis = responseText.lastIndexOf('}');
            if (openParenthesis == -1 || closeParenthesis == -1 || openParenthesis >= closeParenthesis) {
                throw new JsonParseException("Didn't get a valid JSON structure from the AI response!");
            }
            responseText = responseText.substring(openParenthesis, closeParenthesis+1);

            // creates an object of jackson object mapper to map JSON to DTO
            ObjectMapper mapper = new ObjectMapper();
            AnalysisResultResponseDTO responseDTO = mapper.readValue(responseText, AnalysisResultResponseDTO.class);
            return responseDTO;

        } catch (Exception e) {
            throw new JacksonParsingException("Can't parse or map the JSON object!");
        }
        
    }

    @Override
    public AnalysisResultResponseDTO getAnalysedDTOByJDAndResume(long resumeId, long jdId) {
        
        // Need to check if the resumes belong to the currently logged-in user
        Resume resume = resumeService.getResumeByIdForCurrUser(resumeId);

        // Need to check if the job description belongs to that resume
        JobDescription jobDescription = jobDesService.getJDIfLinkedWithResume(jdId, resume);

        // Returns the mapped response DTO of the Analysed result entity object
        return analysisMapper.analysisObjToAnalysisResponse(jobDescription.getAnalysisResult());
        
    }

    private GenerateContentResponse generateContentResponse(String prompt) throws ApiException {

        int diffModelAttempts = 0;
        
        // Loop to switch between different models if the current model's tokens are exhausted
        while (true) {
        
            String model = geminiModelManagerService.getCurrGeminiModel();
            try {
                // Generate content from current gemini model
                return geminiClient.models.generateContent(
                    model, prompt, null);
            
            } catch (ApiException e) {
                
                // If the current model's token is exhausted, then it will switch to a different model
                // There are 4 different Gemini models used
                if ((e.code() == 429 || e.code() == 403) && !geminiModelManagerService.areAllModelsTraversed(diffModelAttempts)) {
                    
                    geminiModelManagerService.switchToNextGeminiModel();
                    diffModelAttempts++;
                    continue;
                }
                // If all of the models are exhausted then it throughs exception
                throw e;
            }
            
        }

    }

}
