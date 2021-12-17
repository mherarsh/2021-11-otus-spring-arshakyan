package ru.mherarsh.service.impl;

import ru.mherarsh.domain.TestResults;
import ru.mherarsh.service.MessageLocalisationService;
import ru.mherarsh.service.PrintAdapter;
import ru.mherarsh.service.TestScoreCalculationService;

import java.util.Map;

public class TestScoreCalculationServiceImpl implements TestScoreCalculationService {
    private final PrintAdapter printAdapter;
    private final int testMinimumScore;
    private final MessageLocalisationService localisationService;

    public TestScoreCalculationServiceImpl(PrintAdapter printAdapter, int testMinimumScore, MessageLocalisationService localisationService) {
        this.printAdapter = printAdapter;
        this.testMinimumScore = testMinimumScore;
        this.localisationService = localisationService;
    }

    @Override
    public void printResults(TestResults testResults) {
        printAdapter.println(getResultMessage(testResults));
    }

    private String getResultMessage(TestResults testResults) {
        return String.format("%s%s", System.lineSeparator(),
                isTestPassed(testResults)
                        ? localisationService.getMessage("strings.test-passed", testResults.getPerson())
                        : localisationService.getMessage("strings.test-failed", testResults.getPerson())
        );
    }

    private boolean isTestPassed(TestResults results) {
        return calculateScore(results) >= testMinimumScore;
    }

    private long calculateScore(TestResults results) {
        return results.getResults().entrySet().stream().filter(Map.Entry::getValue).count();
    }
}
