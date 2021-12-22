package ru.mherarsh.domain;

import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
public class Answer {
    private final int id;
    private final String answerDescription;

    public int getId() {
        return id;
    }

    public String getAnswerDescription() {
        return answerDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Answer answer = (Answer) o;
        return Objects.equals(answerDescription, answer.answerDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerDescription);
    }
}
