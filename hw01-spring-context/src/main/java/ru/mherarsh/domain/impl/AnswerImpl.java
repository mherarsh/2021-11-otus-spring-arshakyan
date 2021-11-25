package ru.mherarsh.domain.impl;

import lombok.AllArgsConstructor;
import ru.mherarsh.domain.Answer;

import java.util.Objects;

@AllArgsConstructor
public class AnswerImpl implements Answer {
    private final int id;
    private final String answer;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AnswerImpl answerIn = (AnswerImpl) o;
        return Objects.equals(answer, answerIn.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answer);
    }
}