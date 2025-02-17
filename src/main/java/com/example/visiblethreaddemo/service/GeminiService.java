package com.example.visiblethreaddemo.service;

import com.example.visiblethreaddemo.model.GeminiModel;
import org.springframework.web.multipart.MultipartFile;

public interface GeminiService {

    GeminiModel getLongestWordSynonym(MultipartFile file);
}
