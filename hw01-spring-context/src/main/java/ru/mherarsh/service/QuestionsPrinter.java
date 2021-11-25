package ru.mherarsh.service;

import ru.mherarsh.enums.QuestionPrintMode;

public interface QuestionsPrinter {
    void printQuestions(QuestionPrintMode printMode);
}