package com.example.visiblethreaddemo.controller;

import com.example.visiblethreaddemo.entity.Document;
import com.example.visiblethreaddemo.model.DocumentWords;
import com.example.visiblethreaddemo.service.DocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/document/", produces = "application/json")
@Slf4j
public class DocumentController {

    @Autowired
    private DocumentService service;

    @GetMapping
    public ResponseEntity<List<Document>> getDocuments() {
        return ResponseEntity.ok(service.getDocuments());
    }

    @PostMapping
    public ResponseEntity<Document> uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("email") String email
    ) {

        return ResponseEntity.ok(service.saveDocument(file, email));
    }

    @GetMapping("word/count")
    public ResponseEntity<DocumentWords> countWord(@RequestParam("fileName") String fileName) {

        return ResponseEntity.ok(service.getDocumentWordCount(fileName));
    }
}
