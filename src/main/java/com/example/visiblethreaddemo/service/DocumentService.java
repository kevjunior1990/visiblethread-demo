package com.example.visiblethreaddemo.service;

import com.example.visiblethreaddemo.entity.Document;
import com.example.visiblethreaddemo.entity.Team;
import com.example.visiblethreaddemo.entity.User;
import com.example.visiblethreaddemo.model.DocumentWords;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {

    List<Document> getDocuments();

    Document getDocument(Long id);

    Document saveDocument(MultipartFile file, String email);

    DocumentWords getDocumentWordCount(String name);
}
