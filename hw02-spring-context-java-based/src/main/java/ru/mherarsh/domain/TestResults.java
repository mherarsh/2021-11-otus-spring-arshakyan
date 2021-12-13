package ru.mherarsh.domain;

import lombok.Builder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Builder
public class TestResults {
    private final HashMap<Question, Boolean> results = new HashMap<>();

    public void pushResults(Question question, boolean isCorrect){
        results.put(question, isCorrect);
    }

    public Map<Question, Boolean> getResults(){
        return Collections.unmodifiableMap(results);
    }
}
