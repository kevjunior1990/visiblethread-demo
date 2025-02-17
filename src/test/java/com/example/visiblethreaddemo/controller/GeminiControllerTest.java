package com.example.visiblethreaddemo.controller;

import com.example.visiblethreaddemo.model.GeminiModel;
import com.example.visiblethreaddemo.service.GeminiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

class GeminiControllerTest {

    @Mock
    private GeminiService geminiService;

    @InjectMocks
    private GeminiController geminiController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testQueryGemini_ShouldReturnResponse() {
        // Arrange
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.txt", "text/plain", "Hello world from Gemini".getBytes()
        );

        GeminiModel mockResponse = GeminiModel.builder()
                .prompt("Hello world from Gemini")
                .response("This is a response from Gemini AI.")
                .build();

        when(geminiService.getLongestWordSynonym(file)).thenReturn(mockResponse);

        // Act
        ResponseEntity<GeminiModel> response = geminiController.queryGemini(file);

        // Assert
        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Hello world from Gemini", response.getBody().getPrompt());
        assertEquals("This is a response from Gemini AI.", response.getBody().getResponse());

        verify(geminiService, times(1)).getLongestWordSynonym(file);
    }
}
