package ru.mherarsh.service;

import ru.mherarsh.domain.Person;
import ru.mherarsh.domain.Question;
import ru.mherarsh.domain.TestResults;

import java.util.List;

public interface QuestionAskService {
    TestResults askQuestions(List<Question> questions, Person person);
    boolean askQuestion(Question question);
}
