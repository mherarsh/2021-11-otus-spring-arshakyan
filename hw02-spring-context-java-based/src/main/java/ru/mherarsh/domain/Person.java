package ru.mherarsh.domain;

public class Person {
    private String name;

    private Person() {
    }

    private Person(String name) {
        this.name = name;
    }

    public static Person fromName(String name) {
        return new Person(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
