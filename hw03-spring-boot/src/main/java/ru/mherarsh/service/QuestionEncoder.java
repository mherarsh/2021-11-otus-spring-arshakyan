package ru.mherarsh.service;

import ru.mherarsh.domain.Question;

public interface QuestionEncoder {
    String encode(Question question);
}
