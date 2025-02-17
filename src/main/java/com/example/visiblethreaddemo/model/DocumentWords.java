package com.example.visiblethreaddemo.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DocumentWords {

    private List<DocumentWord> words;
}
