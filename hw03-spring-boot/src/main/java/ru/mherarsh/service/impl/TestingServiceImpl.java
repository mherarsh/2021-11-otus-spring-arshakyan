package ru.mherarsh.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mherarsh.dao.QuestionRepository;
import ru.mherarsh.service.PersonService;
import ru.mherarsh.service.QuestionAskService;
import ru.mherarsh.service.TestScoreCalculationService;
import ru.mherarsh.service.TestingService;

@Service
@AllArgsConstructor
public class TestingServiceImpl implements TestingService {
    private final QuestionRepository questionRepository;
    private final QuestionAskService questionAskService;
    private final TestScoreCalculationService testScoreCalculationService;
    private final PersonService personService;

    @Override
    public void run() {
        var person = personService.getNewPerson();
        var questions = questionRepository.getQuestions();
        var testResults = questionAskService.askQuestions(questions, person);

        testScoreCalculationService.printResults(testResults);
    }
}
