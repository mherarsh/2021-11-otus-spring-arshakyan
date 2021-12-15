package ru.mherarsh.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.mherarsh.domain.Answer;
import ru.mherarsh.domain.Question;
import ru.mherarsh.service.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class QuestionAskServiceImplTest {
    private final UserInputService userInputService;
    private final AnswerIndexMapper answerIndexMapper;
    private final QuestionAskService questionAskService;

    QuestionAskServiceImplTest() {
        var questionsPrinter = Mockito.mock(QuestionsPrinter.class);
        answerIndexMapper = Mockito.mock(AnswerIndexMapper.class);
        userInputService = Mockito.mock(UserInputService.class);

        this.questionAskService = new QuestionAskServiceImpl(
                questionsPrinter,
                userInputService,
                answerIndexMapper
        );
    }

    @Test
    @DisplayName("askQuestionTest: correct answer")
    void askQuestionTest() {
        Mockito.doReturn(1)
                .when(answerIndexMapper)
                .indexFromDescription("2");

        Mockito.doReturn("2")
                .when(userInputService)
                .getInput(anyString(), any());

        var question = getTestQuestion();

        var isCorrect = questionAskService.askQuestion(question);

        assertThat(isCorrect).isTrue();
    }

    @Test
    @DisplayName("askQuestionTest: incorrect answer")
    void askQuestionIncorrectTest() {
        Mockito.doReturn(2)
                .when(answerIndexMapper)
                .indexFromDescription("3");

        Mockito.doReturn("3")
                .when(userInputService)
                .getInput(anyString(), any());

        var question = getTestQuestion();

        var isCorrect = questionAskService.askQuestion(question);

        assertThat(isCorrect).isFalse();
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
                "answer",
                "variant3",
                "variant4"
        );
    }
}