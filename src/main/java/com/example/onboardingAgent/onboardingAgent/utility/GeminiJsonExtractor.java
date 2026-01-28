package com.example.onboardingAgent.onboardingAgent.utility;

public class GeminiJsonExtractor {

    public static String extractJsonArray(String response) {

        // Remove ```json and ```
        response = response
                .replace("```json", "")
                .replace("```", "")
                .trim();

        // Extract only JSON array
        int start = response.indexOf("[");
        int end = response.lastIndexOf("]");

        if (start == -1 || end == -1) {
            throw new RuntimeException("No JSON array found in Gemini response");
        }

        return response.substring(start, end + 1);
    }
}
