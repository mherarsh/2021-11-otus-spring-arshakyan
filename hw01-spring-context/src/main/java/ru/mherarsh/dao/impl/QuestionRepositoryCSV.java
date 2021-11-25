package ru.mherarsh.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.mherarsh.dao.QuestionRepository;
import ru.mherarsh.domain.Answer;
import ru.mherarsh.domain.Question;
import ru.mherarsh.domain.impl.AnswerImpl;
import ru.mherarsh.domain.impl.QuestionImpl;
import ru.mherarsh.service.CSVLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class QuestionRepositoryCSV implements QuestionRepository {
    private static final Logger logger = LoggerFactory.getLogger(QuestionRepositoryCSV.class);

    private final String recourseName;
    private final CSVLoader csvLoader;
    private final List<Question> questions = new ArrayList<>();

    public QuestionRepositoryCSV(CSVLoader csvLoader, String recourseName) {
        this.recourseName = recourseName;
        this.csvLoader = csvLoader;

        loadQuestions();
    }

    @Override
    public List<Question> getQuestions() {
        return Collections.unmodifiableList(questions);
    }

    private void loadQuestions() {
        var questionsFromCsv = csvLoader.loadFileFromResource(recourseName);

        for (int i = 0; i < questionsFromCsv.size(); i++) {
            var questionLine = questionsFromCsv.get(i);
            var question = createFromQuestionLine(i, questionLine);

            question.ifPresent(this::addQuestion);
        }
    }

    private void addQuestion(Question question) {
        questions.add(question);
    }

    private Optional<Question> createFromQuestionLine(int questionId, List<String> questionLine) {
        if (!isValidQuestionLine(questionLine)) {
            return Optional.empty();
        }

        var question = new QuestionImpl(
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

    private ArrayList<Answer> getAnswerVariants(List<String> questionLine) {
        var answers = new ArrayList<Answer>();

        for (int i = 2, j = 1; i < questionLine.size(); i++, j++) {
            answers.add(createAnswer(j, questionLine.get(i)));
        }

        return answers;
    }

    private Answer createAnswer(int id, String answer) {
        return new AnswerImpl(id, answer);
    }
}