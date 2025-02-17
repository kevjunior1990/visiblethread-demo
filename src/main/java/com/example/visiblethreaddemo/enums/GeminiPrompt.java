package com.example.visiblethreaddemo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum GeminiPrompt {

    LONGEST_WORD_SYNONYM("Suggest synonyms for the longest word in the following document: ");

    private final String prompt;
}
