package ru.mherarsh.service.impl;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import ru.mherarsh.dao.QuestionRepository;
import ru.mherarsh.domain.impl.AnswerImpl;
import ru.mherarsh.domain.impl.QuestionImpl;
import ru.mherarsh.enums.QuestionPrintMode;
import ru.mherarsh.service.QuestionsPrinter;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class QuestionsPrinterTerminalTest {
    private static PrintStream defaultSysOut;
    private static ByteArrayOutputStream testBuffer;

    QuestionRepository questionRepository;
    QuestionsPrinter questionsPrinter;

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
        initQuestionRepository();
        initQuestionPrinter();
    }

    @AfterEach
    void resetBuffer() {
        testBuffer.reset();
    }

    @Test
    @DisplayName("printQuestionsTerminal: print questions, answers and answer variants")
    void printQuestionsQuestionsAndAnswersTest() {
        var originalOut = "" +
                "Q: question\n" +
                "A: answer\n" +
                "Variants:\n" +
                "\t1. variant1\n" +
                "\t2. variant2\n" +
                "\t3. variant3\n" +
                "\t4. variant4\n" +
                "\r\n";

        questionsPrinter.printQuestions(QuestionPrintMode.QUESTIONS_AND_ANSWERS);

        assertThat(originalOut)
                .isEqualTo(getTestSysOut());
    }

    @Test
    @DisplayName("printQuestionsTerminal: print questions and answer variants")
    void printQuestionsQuestionsAndVariantsTest() {
        var originalOut = "" +
                "Q: question\n" +
                "Variants:\n" +
                "\t1. variant1\n" +
                "\t2. variant2\n" +
                "\t3. variant3\n" +
                "\t4. variant4\n" +
                "\r\n";

        questionsPrinter.printQuestions(QuestionPrintMode.QUESTIONS_AND_VARIANTS);

        assertThat(originalOut)
                .isEqualTo(getTestSysOut());
    }

    private void initQuestionRepository() {
        questionRepository = Mockito.mock(QuestionRepository.class);

        Mockito.doReturn(List.of(getTestQuestion()))
                .when(questionRepository)
                .getQuestions();
    }

    private void initQuestionPrinter() {
        questionsPrinter = new QuestionsPrinterTerminal(questionRepository);
    }

    private QuestionImpl getTestQuestion() {
        var questionLine = getTestQuestionLine();

        return QuestionImpl.builder()
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