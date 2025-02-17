package com.example.visiblethreaddemo.connector.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "spring.google.gemini.api")
@Getter
@Setter
public class GeminiConfig {

    private String url;

    private String key;
}

