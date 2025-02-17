package com.example.visiblethreaddemo.service;

import com.example.visiblethreaddemo.connector.GeminiConnector;
import com.example.visiblethreaddemo.enums.GeminiPrompt;
import com.example.visiblethreaddemo.exception.GeminiServiceException;
import com.example.visiblethreaddemo.model.GeminiModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;


import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GeminiServiceImplTest {

    @Mock
    private GeminiConnector geminiConnector;

    @InjectMocks
    private GeminiServiceImpl geminiService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetLongestWordSynonym_ShouldReturnGeminiModel() throws Exception {
        // Arrange
        String fileContent = "This is a test document with the word extraordinary.";
        MultipartFile file = mock(MultipartFile.class);
        when(file.getBytes()).thenReturn(fileContent.getBytes(StandardCharsets.UTF_8));

        String prompt = GeminiPrompt.LONGEST_WORD_SYNONYM.getPrompt() + fileContent;
        String mockResponse = "extraordinary -> remarkable";
        when(geminiConnector.queryChat(prompt)).thenReturn(mockResponse);

        // Act
        GeminiModel result = geminiService.getLongestWordSynonym(file);

        // Assert
        assertNotNull(result);
        assertEquals(prompt, result.getPrompt());
        assertEquals(mockResponse, result.getResponse());

        verify(geminiConnector, times(1)).queryChat(prompt);
    }

    @Test
    void testGetLongestWordSynonym_ShouldThrowGeminiServiceException_WhenErrorOccurs() throws Exception {
        // Arrange
        MultipartFile file = mock(MultipartFile.class);
        when(file.getBytes()).thenThrow(new RuntimeException("File processing error"));

        // Act & Assert
        Exception exception = assertThrows(GeminiServiceException.class, () -> {
            geminiService.getLongestWordSynonym(file);
        });

        assertEquals("Gemini Service failed with error message: File processing error", exception.getMessage());

        verify(geminiConnector, never()).queryChat(anyString());
    }
}
