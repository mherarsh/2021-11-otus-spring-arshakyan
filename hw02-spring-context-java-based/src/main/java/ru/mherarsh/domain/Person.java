package ru.mherarsh.domain;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder=true)
@Getter
public class Person {
    private String name;
    private TestResults testResults;

    @Override
    public String toString() {
        return name;
    }
}
