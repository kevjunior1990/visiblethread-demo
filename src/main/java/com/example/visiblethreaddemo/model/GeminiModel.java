package com.example.visiblethreaddemo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeminiModel {

    private String prompt;

    private String response;
}
