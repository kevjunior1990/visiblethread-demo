package com.example.visiblethreaddemo.service;

import com.example.visiblethreaddemo.entity.Document;
import com.example.visiblethreaddemo.exception.DocumentNameNotFoundException;
import com.example.visiblethreaddemo.exception.DocumentServiceException;
import com.example.visiblethreaddemo.exception.DocumentUidNotFoundException;
import com.example.visiblethreaddemo.exception.UserNotFoundException;
import com.example.visiblethreaddemo.model.DocumentWord;
import com.example.visiblethreaddemo.model.DocumentWords;
import com.example.visiblethreaddemo.repository.DocumentRepository;
import com.example.visiblethreaddemo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.visiblethreaddemo.enums.ExcludedWords.isExcludedWord;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Service
@Transactional
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<Document> getDocuments() {

        return documentRepository.findAll();
    }

    @Override
    public Document getDocument(Long id) {

        return documentRepository.findById(id).orElseThrow(() -> new DocumentUidNotFoundException(id));
    }

    @Override
    public Document saveDocument(MultipartFile file, String email) {

        var user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

        try {

            var content = new String(file.getBytes());

            var fileName = file.getOriginalFilename();

            var wordList = splitContent(content);

            var document = Document.builder()
                    .name(fileName)
                    .wordCount((long) wordList.length)
                    .content(content)
                    .user(user)
                    .build();

            return documentRepository.save(document);

        } catch (Exception e) {

            throw new DocumentServiceException(e.getMessage());
        }
    }

    @Override
    public DocumentWords getDocumentWordCount(String name) {

        var document = documentRepository.findByName(name)
                .orElseThrow(() -> new DocumentNameNotFoundException(name));

        try {

            var words = Stream.of(
                            splitContent(
                                    document.getContent()
                            )
                    )
                    .filter(word -> !isExcludedWord(word))
                    .collect(groupingBy(str -> str, counting()))
                    .entrySet().stream()
                    .map(entry -> DocumentWord.builder().word(entry.getKey()).count(entry.getValue()).build())
                    .sorted((w1, w2) -> Long.compare(w2.getCount(), w1.getCount()))
                    .collect(Collectors.toList());

            return DocumentWords.builder()
                    .words(words)
                    .build();

        } catch (Exception e) {

            throw new DocumentServiceException(e.getMessage());
        }
    }

    private String[] splitContent(String content) {

        return content.replaceAll("[^a-zA-Z0-9\\s]", "").trim().split("\\s+");
    }
}
