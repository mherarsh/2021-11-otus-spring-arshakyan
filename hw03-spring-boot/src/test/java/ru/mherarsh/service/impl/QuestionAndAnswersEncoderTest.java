package ru.mherarsh.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.mherarsh.domain.Answer;
import ru.mherarsh.domain.Question;
import ru.mherarsh.service.QuestionEncoder;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class QuestionAndAnswersEncoderTest {
    private final QuestionEncoder questionEncoder = new QuestionAndAnswersEncoder(new AnswerIndexMapperNumbers());

    @Test
    @DisplayName("question to string encoder")
    void encode() {
        assertThat(getOriginalQuestionString())
                .isEqualTo(questionEncoder.encode(getTestQuestion()));
    }

    private String getOriginalQuestionString() {
        return "Q: question" + System.lineSeparator() +
                "Variants:" + System.lineSeparator() +
                "\t1. variant1" + System.lineSeparator() +
                "\t2. variant2" + System.lineSeparator() +
                "\t3. variant3" + System.lineSeparator() +
                "\t4. variant4";
    }

    private Question getTestQuestion() {
        var questionLine = getTestQuestionLine();

        return Question.builder()
                .id(0)
                .questionDescription(questionLine.get(0))
                .answer(new Answer(0, questionLine.get(1)))
                .answerVariants(List.of(
                        new Answer(1, questionLine.get(2)),
                        new Answer(2, questionLine.get(3)),
                        new Answer(3, questionLine.get(4)),
                        new Answer(4, questionLine.get(5))
                ))
                .build();
    }

    private List<String> getTestQuestionLine() {
        return List.of(
                "question",
                "answer",
                "variant1",
                "variant2",
                "variant3",
                "variant4"
        );
    }
}