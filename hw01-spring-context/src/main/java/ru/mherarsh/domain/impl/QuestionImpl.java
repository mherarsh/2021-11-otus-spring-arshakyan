package ru.mherarsh.domain.impl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import ru.mherarsh.domain.Answer;
import ru.mherarsh.domain.Question;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Builder
public class QuestionImpl implements Question {
    private final int id;
    private final String question;
    private final Answer answer;
    private final List<Answer> answerVariants;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public Answer getAnswer() {
        return answer;
    }

    @Override
    public boolean isAnswer(Answer answer) {
        return this.answer.equals(answer);
    }

    @Override
    public List<Answer> getAnswerVariants() {
        return Collections.unmodifiableList(answerVariants);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        QuestionImpl questionIn = (QuestionImpl) o;

        return question.equals(questionIn.question)
                && answer.equals(questionIn.answer)
                && answerVariants.equals(questionIn.answerVariants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, answer, answerVariants);
    }
}