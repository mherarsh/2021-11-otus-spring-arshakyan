package ru.mherarsh.dao.impl;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import ru.mherarsh.dao.QuestionRepository;
import ru.mherarsh.domain.Answer;
import ru.mherarsh.domain.Question;
import ru.mherarsh.exceptions.NoQuestionsInFileException;
import ru.mherarsh.service.CSVLoader;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;

class QuestionRepositoryCSVTest {
    private static final int INCORRECT_QUESTIONS_COUNT = 1;
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
    @DisplayName("getQuestionsTest: no questions in file")
    void getQuestionsEmptyTest() {
        assertThatExceptionOfType(NoQuestionsInFileException.class).isThrownBy(() ->
                getQuestionRepository(List.of()).getQuestions()
        );
    }

    @Test
    @DisplayName("getQuestionsTest: skip incorrect question line")
    void getQuestionsSkipIncorrectTest() {
        var questionRepository = getQuestionRepository(getTestStringQuestionsList());
        var questions = questionRepository.getQuestions();

        assertThat(getTestStringQuestionsList().size() - INCORRECT_QUESTIONS_COUNT)
                .isEqualTo(questions.size());
    }

    private QuestionRepository getQuestionRepository(List<List<String>> testStringQuestionsList) {
        return new QuestionRepositoryCSV(
                getCsvLoaderFromQuestions(testStringQuestionsList),
                anyString()
        );
    }

    private CSVLoader getCsvLoaderFromQuestions(List<List<String>> testStringQuestionsList) {
        var csvLoader = mock(CSVLoader.class);

        Mockito.doReturn(testStringQuestionsList)
                .when(csvLoader)
                .loadFileFromResource(anyString());

        return csvLoader;
    }

    private List<List<String>> getTestStringQuestionsList() {
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