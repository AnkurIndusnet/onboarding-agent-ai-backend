package com.example.onboardingAgent.onboardingAgent.utility;

public class GeminiJsonExtractor {

    /**
     * Extracts a JSON ARRAY from Gemini response
     * Use this ONLY when response is expected to be []
     */
    public static String extractJsonArray(String response) {

        response = clean(response);

        int start = response.indexOf("[");
        int end = response.lastIndexOf("]");

        if (start == -1 || end == -1) {
            throw new RuntimeException("No JSON array found in Gemini response");
        }

        return response.substring(start, end + 1);
    }

    /**
     * Extracts a JSON OBJECT from Gemini response
     * Use this when response is expected to be {}
     */
    public static String extractJsonObject(String response) {

        response = clean(response);

        int start = response.indexOf("{");
        int end = response.lastIndexOf("}");

        if (start == -1 || end == -1) {
            throw new RuntimeException("No JSON object found in Gemini response");
        }

        return response.substring(start, end + 1);
    }

    /**
     * Common cleanup for Gemini responses
     */
    private static String clean(String response) {
        return response
                .replace("```json", "")
                .replace("```", "")
                .trim();
    }
}
