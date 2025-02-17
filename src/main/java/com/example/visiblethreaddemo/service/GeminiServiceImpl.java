package com.example.visiblethreaddemo.service;

import com.example.visiblethreaddemo.connector.GeminiConnector;
import com.example.visiblethreaddemo.enums.GeminiPrompt;
import com.example.visiblethreaddemo.exception.GeminiServiceException;
import com.example.visiblethreaddemo.model.GeminiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GeminiServiceImpl implements GeminiService {

    @Autowired
    @Qualifier("geminiHttpService")
    private GeminiConnector geminiConnector;

    @Override
    public GeminiModel getLongestWordSynonym(MultipartFile file) {

        try {

            var content = new String(file.getBytes());

            String prompt = GeminiPrompt.LONGEST_WORD_SYNONYM.getPrompt() + content;

            return GeminiModel.builder()
                    .prompt(prompt)
                    .response(geminiConnector.queryChat(prompt))
                    .build();

        } catch (Exception e) {

            throw new GeminiServiceException(e.getMessage());
        }
    }
}
