package ru.mherarsh.service;

import ru.mherarsh.domain.Question;

public interface QuestionAskService {
    boolean askQuestion(Question question);
}
