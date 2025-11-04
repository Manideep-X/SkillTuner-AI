package com.manideep.skilltunerai.util;

public class GeminiPromptCreationUtil {

    public static String createPrompt(
        String resumeContent, String jobTitle, String companyName, String jdContent
    ) {

        // Instructing AI to be an recruiter and adding the resume
        StringBuilder prompt = new StringBuilder();
        prompt
            .append("You are an expert recruitment AI.")
            .append("Here is a candidate's resume:\n")
            .append(resumeContent).append("\n\n")
            .append("Here is a job description:\n");
        
        // Adding job title is exists
        if (jobTitle != null && !jobTitle.isEmpty()) {
            prompt.append("Job title: ").append(jobTitle).append("\n");
        }
        
        // Adding company name is exists
        if (companyName != null && !companyName.isEmpty()) {
            prompt.append("Company name: ").append(companyName).append("\n");
        }
        
        // Adding the job description
        prompt.append(jdContent).append("\n\n");

        // Instructing AI for a well structured response in JSON format
        prompt
            .append("Instructions:\n")
                .append("1. A match score between 0 and 100.\n")
                .append("2. A list of strengths if any.\n")
                .append("3. A list of missing skills if any.\n")
                .append("4. A list of improvements that can be done to the resume if any.\n")
                .append("5. Provide final feedback (in less than or equal to 80 words) about whether the candidate should apply or not and why.\n\n")
            .append("Return the output strictly in the following JSON format only, with no markdown, no text outside JSON:\n")
            .append("{\n")
            .append("   \"match_score\": number,\n")
            .append("   \"strengths\": [\"...\"],\n")
            .append("   \"missing_skills\": [\"...\"],\n")
            .append("   \"improvements\": [\"...\"],\n")
            .append("   \"feedback\": \"...\"\n")
            .append("}");

        return prompt.toString();

    }

}
