package ru.mherarsh.service.impl;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.mherarsh.domain.Question;
import ru.mherarsh.service.*;

@AllArgsConstructor
@Service
public class QuestionAskServiceImpl implements QuestionAskService {
    private static final Logger logger = LoggerFactory.getLogger(QuestionAskServiceImpl.class);

    private final QuestionsPrinter questionsPrinter;
    private final UserInputService userInputService;
    private final AnswerIndexMapper answerIndexMapper;

    @Override
    public boolean askQuestion(Question question) {
        questionsPrinter.printQuestion(question);
        var answerVariant = userInputService.getInput("choose answer", answer -> validateAnswer(answer, question));

        return isAnswer(question, answerVariant);
    }

    private boolean isAnswer(Question question, String answerVariant) {
        var answerIndexInQuestion = getAnswerIndex(answerVariant);

        return question.isAnswer(answerIndexInQuestion);
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

    private int getAnswerIndex(String answer) {
        return answerIndexMapper.indexFromDescription(answer);
    }
}
