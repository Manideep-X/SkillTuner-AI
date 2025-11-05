package com.manideep.skilltunerai.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class GeminiModelManagerService {

    private final Logger logger = LoggerFactory.getLogger(GeminiModelManagerService.class);

    private static final List<String> geminiModels = List.of(
        "gemini-2.5-flash",
        "gemini-2.5-flash-preview",
        "gemini-2.5-flash-lite",
        "gemini-2.5-flash-lite-preview"
    );
    private int currentModelIndex = 0;

    public synchronized String getCurrGeminiModel() {
        return geminiModels.get(currentModelIndex);
    }

    public synchronized String switchToNextGeminiModel() {
        currentModelIndex = (currentModelIndex + 1) % geminiModels.size();
        return geminiModels.get(currentModelIndex);
    }

    public synchronized boolean areAllModelsTraversed(int noOfAttempts) {
        return noOfAttempts >= geminiModels.size();
    }

    // Everyday at midnight the Gemini model index will reset to 0
    @Scheduled(cron = "0 0 0 * * *", zone = "${app.scheduling.timezone}")
    public synchronized void resetModelIndex() {
        int tempModelIndex = currentModelIndex;
        currentModelIndex = 0;
        logger.info("[GeminiModelManagerService] Model changed from : {} to {}",
                    getGeminiModelByIndex(tempModelIndex),
                    getCurrGeminiModel());
    }

    private synchronized String getGeminiModelByIndex(int index) {
        return geminiModels.get(index);
    }

}
