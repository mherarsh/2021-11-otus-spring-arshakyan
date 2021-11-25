package ru.mherarsh.dao.impl;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import ru.mherarsh.dao.QuestionRepository;
import ru.mherarsh.domain.Question;
import ru.mherarsh.domain.impl.AnswerImpl;
import ru.mherarsh.domain.impl.QuestionImpl;
import ru.mherarsh.service.CSVLoader;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;

class QuestionRepositoryCSVTest {
    private static final int INCORRECT_QUESTIONS_COUNT = 1;
    private static PrintStream defaultSysOut;
    private static ByteArrayOutputStream testBuffer;

    private CSVLoader csvLoader;
    private QuestionRepository questionRepository;
    private List<Question> originalQuestion;

    @BeforeAll
    static void overrideSysOut() {
        defaultSysOut = System.out;
        testBuffer = new ByteArrayOutputStream();

        System.setOut(
                new PrintStream(
                        new BufferedOutputStream(
                                testBuffer
                        )
                )
        );
    }

    @AfterAll
    static void resetSysOut() {
        System.setOut(defaultSysOut);
    }

    @BeforeEach
    void init() {
        initOriginalQuestions();
        initCsvLoader();
        iniQuestionRepository();
    }

    @AfterEach
    void resetBuffer() {
        testBuffer.reset();
    }

    @Test
    @DisplayName("getQuestionsTest: get questions")
    void getQuestionsTest() {
        var questions = questionRepository.getQuestions();

        assertThat(originalQuestion).isEqualTo(questions);
    }

    @Test
    @DisplayName("getQuestionsTest: skip incorrect question line")
    void getQuestionsSkipIncorrectTest() {
        var questions = questionRepository.getQuestions();

        assertThat(getTestQuestionsList().size() - INCORRECT_QUESTIONS_COUNT)
                .isEqualTo(questions.size());
    }

    @Test
    @DisplayName("getQuestionsTest: incorrect question line skipping warning")
    void getQuestionsSkipIncorrectWarnTest() {
        questionRepository.getQuestions();

        assertThat(getTestSysOut())
                .containsIgnoringCase("incorrect question line:");
    }

    private void initOriginalQuestions() {
        var questionLine = getTestQuestionLine();
        var question = QuestionImpl.builder()
                .id(0)
                .question(questionLine.get(0))
                .answer(new AnswerImpl(0, questionLine.get(1)))
                .answerVariants(List.of(
                        new AnswerImpl(1, questionLine.get(2)),
                        new AnswerImpl(2, questionLine.get(3)),
                        new AnswerImpl(3, questionLine.get(4)),
                        new AnswerImpl(4, questionLine.get(5))
                ))
                .build();

        originalQuestion = List.of(question);
    }

    private void initCsvLoader() {
        csvLoader = mock(CSVLoader.class);

        Mockito.doReturn(getTestQuestionsList())
                .when(csvLoader)
                .loadFileFromResource(anyString());
    }

    private void iniQuestionRepository() {
        questionRepository = new QuestionRepositoryCSV(csvLoader, anyString());
    }

    private List<List<String>> getTestQuestionsList() {
        return List.of(
                getTestQuestionLine(),
                List.of("incorrect question line")
        );
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

    private String getTestSysOut() {
        System.out.flush();
        return testBuffer.toString();
    }
}