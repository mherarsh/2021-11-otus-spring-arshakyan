package ru.mherarsh.exceptions;

public class IncorrectQuestionFileException extends RuntimeException{
    public IncorrectQuestionFileException(String errorMessage) {
        super(errorMessage);
    }
}
