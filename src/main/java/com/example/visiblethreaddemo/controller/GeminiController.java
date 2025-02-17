package com.example.visiblethreaddemo.controller;

import com.example.visiblethreaddemo.model.GeminiModel;
import com.example.visiblethreaddemo.service.GeminiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/api/v1/gemini/", produces = "application/json")
@Slf4j
public class GeminiController {

    @Autowired
    private GeminiService geminiService;

    @GetMapping
    public ResponseEntity<GeminiModel> queryGemini(@RequestParam("file") MultipartFile file) {

        return ResponseEntity.ok(geminiService.getLongestWordSynonym(file));
    }
}
