package ru.mherarsh.dao;

import ru.mherarsh.domain.Question;

import java.util.List;

public interface QuestionRepository {
    List<Question> getQuestions();
}