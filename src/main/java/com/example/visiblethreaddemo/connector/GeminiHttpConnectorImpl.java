package com.example.visiblethreaddemo.connector;

import com.example.visiblethreaddemo.exception.GeminiConnectorException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.util.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Component("geminiHttpService")
public class GeminiHttpConnectorImpl implements GeminiConnector {

    @Value("${spring.google.gemini.api.key}")
    private String apiKey;

    @Value("${spring.google.gemini.api.url}")
    private String apiUrl;

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private ObjectMapper mapper;


    @Override
    public String queryChat(String prompt) {

        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-002:generateContent?key=AIzaSyAPqgXAZhEQdEA89AxJLuhVnU6gtW8C2Nc";

        Map<String, Object> requestBody = Map.of(
                "contents", Collections.singletonList(
                        Map.of("parts", Collections.singletonList(
                                Map.of("text", prompt)
                        ))
                )
        );

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // Make POST request
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        return extractText(response.getBody());
    }

    private String extractText(String jsonResponse) {
        try {
            JsonNode rootNode = mapper.readTree(jsonResponse);
            return rootNode.path("candidates")
                    .path(0)
                    .path("content")
                    .path("parts")
                    .path(0)
                    .path("text")
                    .asText();
        } catch (Exception e) {

            throw new GeminiConnectorException(e.getMessage());
        }
    }
}
