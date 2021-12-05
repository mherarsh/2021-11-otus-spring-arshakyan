package ru.mherarsh.service;

import ru.mherarsh.domain.TestResults;

public interface TestScoreCalculationService {
    void printResults(String name, TestResults results);
}
