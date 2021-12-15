package ru.mherarsh.service;

import ru.mherarsh.domain.Question;

public interface QuestionsPrinter {
    void printQuestions();
    void printQuestion(Question question);
}
