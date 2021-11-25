package ru.mherarsh.domain;

import java.util.List;

public interface Question {
    int getId();
    String getQuestion();
    Answer getAnswer();
    boolean isAnswer(Answer answer);
    List<Answer> getAnswerVariants();
}