package com.example.visiblethreaddemo.controller;

import com.example.visiblethreaddemo.entity.Document;
import com.example.visiblethreaddemo.model.DocumentWord;
import com.example.visiblethreaddemo.model.DocumentWords;
import com.example.visiblethreaddemo.service.DocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;

class DocumentControllerTest {

    @Mock
    private DocumentService documentService;

    @InjectMocks
    private DocumentController documentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDocuments_ShouldReturnDocumentList() {
        // Arrange
        List<Document> mockDocuments = List.of(new Document().withId(1L).withName("TestDoc"));
        when(documentService.getDocuments()).thenReturn(mockDocuments);

        // Act
        ResponseEntity<List<Document>> response = documentController.getDocuments();

        // Assert
        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("TestDoc", response.getBody().get(0).getName());

        verify(documentService, times(1)).getDocuments();
    }

    @Test
    void testUploadDocument_ShouldReturnUploadedDocument() {
        // Arrange
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Hello World".getBytes()
        );
        String email = "user@example.com";
        Document mockDocument = new Document().withId(1L).withName("test.txt");

        when(documentService.saveDocument(file, email)).thenReturn(mockDocument);

        // Act
        ResponseEntity<Document> response = documentController.uploadDocument(file, email);

        // Assert
        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("test.txt", response.getBody().getName());

        verify(documentService, times(1)).saveDocument(file, email);
    }

    @Test
    void testCountWord_ShouldReturnWordCounts() {
        // Arrange
        String fileName = "test.txt";
        DocumentWords mockDocumentWords = DocumentWords.builder()
                .words(List.of(
                        DocumentWord.builder().word("Hello").count(3L).build(),
                        DocumentWord.builder().word("World").count(2L).build()
                ))
                .build();

        when(documentService.getDocumentWordCount(fileName)).thenReturn(mockDocumentWords);

        // Act
        ResponseEntity<DocumentWords> response = documentController.countWord(fileName);

        // Assert
        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getWords().size());
        assertEquals("Hello", response.getBody().getWords().get(0).getWord());
        assertEquals(3L, response.getBody().getWords().get(0).getCount());

        verify(documentService, times(1)).getDocumentWordCount(fileName);
    }

    @Test
    void testCountWord_ShouldReturnEmptyList_WhenNoWords() {
        // Arrange
        String fileName = "empty.txt";
        DocumentWords mockDocumentWords = DocumentWords.builder().words(List.of()).build();
        when(documentService.getDocumentWordCount(fileName)).thenReturn(mockDocumentWords);

        // Act
        ResponseEntity<DocumentWords> response = documentController.countWord(fileName);

        // Assert
        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getWords().isEmpty());

        verify(documentService, times(1)).getDocumentWordCount(fileName);
    }
}
