package ru.mherarsh.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mherarsh.dao.QuestionRepository;
import ru.mherarsh.domain.TestResults;
import ru.mherarsh.service.*;

@Service
@AllArgsConstructor
public class TestingServiceImpl implements TestingService {
    private final QuestionRepository questionRepository;
    private final QuestionAskService questionAskService;
    private final UserInputService userInputService;
    private final TestScoreCalculationService testScoreCalculationService;

    @Override
    public void run() {
        var name = readName();
        var testResults = askQuestions();

        testScoreCalculationService.printResults(name, testResults);
    }

    private String readName(){
        return userInputService.getInput("Please input your name", x -> !x.isBlank());
    }

    private TestResults askQuestions() {
        var testResults = getBlankTestResults();
        var questions = questionRepository.getQuestions();

        for (var question : questions) {
            var isCorrect = questionAskService.askQuestion(question);
            testResults.pushResults(question, isCorrect);
        }

        return testResults;
    }

    private TestResults getBlankTestResults(){
        return new TestResults();
    }
}
