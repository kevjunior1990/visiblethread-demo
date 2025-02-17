package com.example.visiblethreaddemo.repository;

import com.example.visiblethreaddemo.entity.Document;
import com.example.visiblethreaddemo.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
public class DocumentRepositoryTest {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository; // Inject UserRepository

    private User testUser;
    private Document document1, document2;

    @BeforeEach
    public void setUp() {
        // Create and save a User before assigning to Documents
        testUser = User.builder()
                .email("test@example.com")
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .modificationDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        testUser = userRepository.save(testUser);

        document1 = Document.builder()
                .name("TEST_NAME_01")
                .content("Sample text 1")
                .wordCount(5L)
                .user(testUser) // Assign user
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .modificationDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        document2 = Document.builder()
                .name("TEST_NAME_02")
                .content("Sample text 2")
                .wordCount(7L)
                .user(testUser) // Assign user
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .modificationDate(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        documentRepository.save(document1);
        documentRepository.save(document2);
    }

    @AfterEach
    public void tearDown() {
        documentRepository.deleteAll();
        userRepository.deleteAll(); // Cleanup users after each test
    }

    @Test
    public void testSaveAndRetrieveDocument() {
        Document savedDocument = documentRepository.save(Document.builder()
                .name("NEW_TEST")
                .content("New text")
                .wordCount(10L)
                .user(testUser) // Assign user
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .modificationDate(Timestamp.valueOf(LocalDateTime.now()))
                .build());

        Optional<Document> retrievedDocument = documentRepository.findById(savedDocument.getId());
        assertTrue(retrievedDocument.isPresent());
        assertEquals("NEW_TEST", retrievedDocument.get().getName());
    }

    @Test
    public void testFindById() {
        Optional<Document> foundDocument = documentRepository.findById(document1.getId());
        assertTrue(foundDocument.isPresent());
        assertEquals("TEST_NAME_01", foundDocument.get().getName());
    }

    @Test
    public void testFindAll() {
        List<Document> documents = documentRepository.findAll();
        assertEquals(2, documents.size());
    }

    @Test
    public void testDeleteDocument() {
        documentRepository.delete(document1);
        Optional<Document> deletedDocument = documentRepository.findById(document1.getId());
        assertFalse(deletedDocument.isPresent());
    }
}
