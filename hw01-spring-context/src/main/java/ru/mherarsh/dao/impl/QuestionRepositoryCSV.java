package ru.mherarsh.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mherarsh.dao.QuestionRepository;
import ru.mherarsh.domain.Answer;
import ru.mherarsh.domain.Question;
import ru.mherarsh.exceptions.NoQuestionsInFileException;
import ru.mherarsh.service.CSVLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class QuestionRepositoryCSV implements QuestionRepository {
    private static final Logger logger = LoggerFactory.getLogger(QuestionRepositoryCSV.class);

    private final String recourseName;
    private final CSVLoader csvLoader;

    public QuestionRepositoryCSV(CSVLoader csvLoader, String recourseName) {
        this.recourseName = recourseName;
        this.csvLoader = csvLoader;
    }

    @Override
    public List<Question> getQuestions() {
        return getQuestionsFromCsv();
    }

    private List<Question> getQuestionsFromCsv() {
        var questionsFromCsv = csvLoader.loadFileFromResource(recourseName);
        var questions = new ArrayList<Question>();

        for (int i = 0; i < questionsFromCsv.size(); i++) {
            var questionLine = questionsFromCsv.get(i);
            var question = createFromQuestionLine(i, questionLine);

            question.ifPresent(questions::add);
        }

        validateQuestions(questions);

        return questions;
    }

    private void validateQuestions(List<Question> questions) {
        if (questions.isEmpty()) {
            throw new NoQuestionsInFileException("no questions found in file: " + recourseName);
        }
    }

    private Optional<Question> createFromQuestionLine(int questionId, List<String> questionLine) {
        if (!isValidQuestionLine(questionLine)) {
            return Optional.empty();
        }

        var question = new Question(
                questionId,
                questionLine.get(0),
                createAnswer(0, questionLine.get(1)),
                getAnswerVariants(questionLine)
        );

        return Optional.of(question);
    }

    private boolean isValidQuestionLine(List<String> questionLine) {
        if (questionLine == null || questionLine.size() < 3) {
            logger.warn("incorrect question line: {}", questionLine);
            return false;
        }

        return true;
    }

    private List<Answer> getAnswerVariants(List<String> questionLine) {
        var answers = new ArrayList<Answer>();

        for (int i = 2, j = 1; i < questionLine.size(); i++, j++) {
            answers.add(createAnswer(j, questionLine.get(i)));
        }

        return answers;
    }

    private Answer createAnswer(int id, String answer) {
        return new Answer(id, answer);
    }
}
