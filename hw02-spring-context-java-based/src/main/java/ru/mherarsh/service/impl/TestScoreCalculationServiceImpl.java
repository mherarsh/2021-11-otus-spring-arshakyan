package ru.mherarsh.service.impl;

import ru.mherarsh.domain.TestResults;
import ru.mherarsh.service.PrintAdapter;
import ru.mherarsh.service.TestScoreCalculationService;

import java.util.Map;

public class TestScoreCalculationServiceImpl implements TestScoreCalculationService {
    private final PrintAdapter printAdapter;
    private final int testMinimumScore;

    public TestScoreCalculationServiceImpl(PrintAdapter printAdapter, int testMinimumScore) {
        this.printAdapter = printAdapter;
        this.testMinimumScore = testMinimumScore;
    }

    @Override
    public void printResults(TestResults testResults) {
        printAdapter.println(getResultMessage(testResults));
    }

    private String getResultMessage(TestResults testResults) {
        return String.format("%s%s, test is %s", System.lineSeparator(),
                testResults.getPerson(),
                isTestPassed(testResults) ? "passed" : "failed");
    }

    private boolean isTestPassed(TestResults results) {
        return calculateScore(results) >= testMinimumScore;
    }

    private long calculateScore(TestResults results) {
        return results.getResults().entrySet().stream().filter(Map.Entry::getValue).count();
    }
}
