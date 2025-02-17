package com.example.visiblethreaddemo.service;

import com.example.visiblethreaddemo.entity.Document;
import com.example.visiblethreaddemo.entity.User;
import com.example.visiblethreaddemo.exception.DocumentNameNotFoundException;
import com.example.visiblethreaddemo.exception.DocumentUidNotFoundException;
import com.example.visiblethreaddemo.exception.UserNotFoundException;
import com.example.visiblethreaddemo.model.DocumentWords;
import com.example.visiblethreaddemo.repository.DocumentRepository;
import com.example.visiblethreaddemo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DocumentServiceImplTest {

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private DocumentServiceImpl documentService;

    private User mockUser;
    private Document mockDocument;

    @BeforeEach
    void setUp() {
        mockUser = User.builder()
                .id(1L)
                .email("test@example.com")
                .build();

        mockDocument = Document.builder()
                .id(1L)
                .name("testDocument.txt")
                .content("Hello world Hello")
                .wordCount(3L)
                .user(mockUser)
                .build();
    }

    @Test
    void getDocuments_ShouldReturnListOfDocuments() {
        when(documentRepository.findAll()).thenReturn(List.of(mockDocument));

        List<Document> documents = documentService.getDocuments();

        assertNotNull(documents);
        assertEquals(1, documents.size());
        verify(documentRepository, times(1)).findAll();
    }

    @Test
    void getDocument_ShouldReturnDocument_WhenIdExists() {
        when(documentRepository.findById(1L)).thenReturn(Optional.of(mockDocument));

        Document document = documentService.getDocument(1L);

        assertNotNull(document);
        assertEquals("testDocument.txt", document.getName());
        verify(documentRepository, times(1)).findById(1L);
    }

    @Test
    void getDocument_ShouldThrowException_WhenIdNotExists() {
        when(documentRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(DocumentUidNotFoundException.class, () -> documentService.getDocument(2L));
        verify(documentRepository, times(1)).findById(2L);
    }

    @Test
    void saveDocument_ShouldSaveAndReturnDocument() throws Exception {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));
        when(file.getBytes()).thenReturn("Hello world Hello".getBytes());
        when(file.getOriginalFilename()).thenReturn("testDocument.txt");
        when(documentRepository.save(any(Document.class))).thenReturn(mockDocument);

        Document savedDocument = documentService.saveDocument(file, "test@example.com");

        assertNotNull(savedDocument);
        assertEquals("testDocument.txt", savedDocument.getName());
        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(documentRepository, times(1)).save(any(Document.class));
    }

    @Test
    void saveDocument_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findByEmail("invalid@example.com")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> documentService.saveDocument(file, "invalid@example.com"));
        verify(userRepository, times(1)).findByEmail("invalid@example.com");
        verifyNoInteractions(documentRepository);
    }

    @Test
    void getDocumentWordCount_ShouldReturnWordCount_WhenDocumentExists() {
        when(documentRepository.findByName("testDocument.txt")).thenReturn(Optional.of(mockDocument));

        DocumentWords documentWords = documentService.getDocumentWordCount("testDocument.txt");

        assertNotNull(documentWords);
        assertEquals(2, documentWords.getWords().size()); // "Hello" and "world"
        verify(documentRepository, times(1)).findByName("testDocument.txt");
    }

    @Test
    void getDocumentWordCount_ShouldThrowException_WhenDocumentNotFound() {
        when(documentRepository.findByName("notFound.txt")).thenReturn(Optional.empty());

        assertThrows(DocumentNameNotFoundException.class, () -> documentService.getDocumentWordCount("notFound.txt"));
        verify(documentRepository, times(1)).findByName("notFound.txt");
    }
}
