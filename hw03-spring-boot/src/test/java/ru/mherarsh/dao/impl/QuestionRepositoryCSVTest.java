package ru.mherarsh.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import ru.mherarsh.dao.QuestionRepository;
import ru.mherarsh.domain.Answer;
import ru.mherarsh.domain.Question;
import ru.mherarsh.exceptions.IncorrectQuestionFileException;
import ru.mherarsh.service.CSVLoader;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
class QuestionRepositoryCSVTest {
    @Configuration
    static class TestConfig {
    }

    @MockBean
    private CSVLoader csvLoader;

    private List<Question> originalQuestion;

    public QuestionRepositoryCSVTest() {
        initOriginalQuestions();
    }

    @Test
    @DisplayName("getQuestionsTest: get questions")
    void getQuestionsTest() {
        assertThatNoException().isThrownBy(() -> {
            var questionRepository = getQuestionRepository(getTestStringQuestionsList());
            var questions = questionRepository.getQuestions();

            assertThat(originalQuestion).isEqualTo(questions);
        });
    }

    @Test
    @DisplayName("getQuestionsTest: incorrect question line in file")
    void getQuestionsIncorrectLineTest() {
        assertThatExceptionOfType(IncorrectQuestionFileException.class).isThrownBy(() ->
                getQuestionRepository(getIncorrectTestStringQuestionsList()).getQuestions()
        );
    }

    @Test
    @DisplayName("getQuestionsTest: no questions in file")
    void getQuestionsEmptyTest() {
        assertThatExceptionOfType(IncorrectQuestionFileException.class).isThrownBy(() ->
                getQuestionRepository(List.of()).getQuestions()
        );
    }

    private QuestionRepository getQuestionRepository(List<List<String>> testStringQuestionsList) {
        Mockito.doReturn(testStringQuestionsList)
                .when(csvLoader)
                .loadFileFromResource(anyString());

        return new QuestionRepositoryCSV(csvLoader, anyString());
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

    private void initOriginalQuestions() {
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

        originalQuestion = List.of(question);
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
