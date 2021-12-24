package ru.mherarsh.service;

public interface UserInputService {
    String getInput(String description, ReaderValueValidator validator);
}
