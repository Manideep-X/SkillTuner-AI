package com.manideep.skilltunerai.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.genai.Client;

@Configuration
public class GeminiClientConfig {

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Bean
    Client geminiClient() {
        return Client.builder().apiKey(geminiApiKey).build();
    }

}
