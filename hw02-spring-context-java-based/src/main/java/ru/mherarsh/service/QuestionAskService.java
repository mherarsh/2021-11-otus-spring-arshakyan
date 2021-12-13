package ru.mherarsh.service;

import ru.mherarsh.domain.Person;
import ru.mherarsh.domain.Question;

import java.util.List;

public interface QuestionAskService {
    Person askQuestions(List<Question> questions, Person person);
    boolean askQuestion(Question question);
}
