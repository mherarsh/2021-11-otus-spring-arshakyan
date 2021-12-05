package ru.mherarsh.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Builder
public class Question {
    private final int id;
    private final String questionDescription;
    private final Answer answer;
    private final List<Answer> answerVariants;

    public int getId() {
        return id;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public Answer getAnswer() {
        return answer;
    }

    public boolean isAnswer(int answerIndex) {
        return this.answer.equals(answerVariants.get(answerIndex));
    }

    public boolean isValidAnswerIndex(int answerIndex) {
        return answerIndex >= 0 && answerIndex < answerVariants.size();
    }

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

        Question question = (Question) o;

        return questionDescription.equals(question.questionDescription)
                && answer.equals(question.answer)
                && answerVariants.equals(question.answerVariants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionDescription, answer, answerVariants);
    }
}
