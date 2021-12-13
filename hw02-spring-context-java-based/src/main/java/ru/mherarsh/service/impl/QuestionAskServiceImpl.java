package ru.mherarsh.service.impl;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mherarsh.domain.Answer;
import ru.mherarsh.domain.Person;
import ru.mherarsh.domain.Question;
import ru.mherarsh.domain.TestResults;
import ru.mherarsh.service.*;

import java.util.List;

@AllArgsConstructor
@Service
public class QuestionAskServiceImpl implements QuestionAskService {
    private static final Logger logger = LoggerFactory.getLogger(QuestionAskServiceImpl.class);

    private final QuestionsPrinter questionsPrinter;
    private final UserInputService userInputService;
    private final AnswerIndexMapper answerIndexMapper;

    @Override
    public Person askQuestions(List<Question> questions, Person person) {
        var testResults = TestResults.builder().build();

        for (var question : questions) {
            var isCorrect = askQuestion(question);
            testResults.pushResults(question, isCorrect);
        }

        return person.toBuilder().testResults(testResults).build();
    }

    @Override
    public boolean askQuestion(Question question) {
        questionsPrinter.printQuestion(question);
        var selectedAnswer = userInputService.getInput("choose answer", answer -> validateAnswer(answer, question));

        return isRightAnswer(question, selectedAnswer);
    }

    private boolean isRightAnswer(Question question, String selectedAnswer) {
        var answer = getAnswerVariant(question, selectedAnswer);

        return question.isRightAnswer(answer);
    }

    private boolean validateAnswer(String answer, Question question) {
        if (answer.isBlank()) {
            return false;
        }

        try {
            var answerIndex = getAnswerIndex(answer);

            return question.isValidAnswerIndex(answerIndex);
        } catch (Exception e) {
            logger.debug("incorrect answer variant : {}", answer);
            return false;
        }
    }

    private Answer getAnswerVariant(Question question, String selectedAnswer) {
        var answerIndexInQuestion = getAnswerIndex(selectedAnswer);

        return question.getAnswerVariants().get(answerIndexInQuestion);
    }

    private int getAnswerIndex(String selectedAnswer) {
        return answerIndexMapper.indexFromDescription(selectedAnswer);
    }
}
