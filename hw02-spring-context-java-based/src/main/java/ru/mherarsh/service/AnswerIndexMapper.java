package ru.mherarsh.service;

public interface AnswerIndexMapper {
    String indexToDescription(int index);
    int indexFromDescription(String description);
}
