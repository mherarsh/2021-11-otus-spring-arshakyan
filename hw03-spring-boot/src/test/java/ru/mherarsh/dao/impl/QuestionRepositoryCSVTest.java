package ru.mherarsh.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import ru.mherarsh.dao.QuestionRepository;
import ru.mherarsh.domain.Answer;
import ru.mherarsh.domain.Question;
import ru.mherarsh.exceptions.IncorrectQuestionFileException;
import ru.mherarsh.service.CSVLoader;
import ru.mherarsh.service.LocaleConfig;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@SpringBootTest(classes = {QuestionRepositoryCSV.class})
class QuestionRepositoryCSVTest {
    @Configuration
    static class TestConfig {
    }

    @MockBean
    private LocaleConfig localeConfig;

    @MockBean
    private CSVLoader csvLoader;

    @Autowired
    QuestionRepository questionRepository;

    @Test
    @DisplayName("getQuestionsTest: get questions")
    void getQuestionsTest() {
        doReturn(getTestStringQuestionsList())
                .when(csvLoader)
                .loadFileFromResource(any());

        assertThatNoException().isThrownBy(() -> {
            var questions = questionRepository.getQuestions();

            assertThat(getOriginalQuestions()).isEqualTo(questions);
        });
    }

    @Test
    @DisplayName("getQuestionsTest: incorrect question line in file")
    void getQuestionsIncorrectLineTest() {
        doReturn(getIncorrectTestStringQuestionsList())
                .when(csvLoader)
                .loadFileFromResource(any());

        assertThatExceptionOfType(IncorrectQuestionFileException.class).isThrownBy(() ->
                questionRepository.getQuestions()
        );
    }

    @Test
    @DisplayName("getQuestionsTest: no questions in file")
    void getQuestionsEmptyTest() {
        doReturn(List.of())
                .when(csvLoader)
                .loadFileFromResource(any());

        assertThatExceptionOfType(IncorrectQuestionFileException.class).isThrownBy(() ->
                questionRepository.getQuestions()
        );
    }

    private QuestionRepository getQuestionRepository111(List<List<String>> testStringQuestionsList) {
        doReturn(testStringQuestionsList)
                .when(csvLoader)
                .loadFileFromResource(any());

        return new QuestionRepositoryCSV(csvLoader, any());
    }

    private List<List<String>> getTestStringQuestionsList() {
        return List.of(
                getTestQuestionLine()
        );
    }

    private List<List<String>> getIncorrectTestStringQuestionsList() {
        return List.of(
                getTestQuestionLine(),
                List.of("incorrect question line")
        );
    }

    private List<Question> getOriginalQuestions() {
        var questionLine = getTestQuestionLine();
        var question = Question.builder()
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

        return List.of(question);
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
