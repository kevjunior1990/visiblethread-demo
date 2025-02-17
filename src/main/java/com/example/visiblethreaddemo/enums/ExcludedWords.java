package com.example.visiblethreaddemo.enums;

public enum ExcludedWords {
    THE,
    ME,
    YOU,
    I,
    OF,
    AND,
    A,
    WE;

    public static boolean isExcludedWord(String word) {

        for (ExcludedWords excludedWord : ExcludedWords.values()) {
            if (excludedWord.name().equals(word.toUpperCase())) {
                return true;
            }
        }

        return false;
    }
}
