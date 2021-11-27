package ru.mherarsh.exceptions;

public class NoQuestionsInFileException extends RuntimeException{
    public NoQuestionsInFileException(String errorMessage) {
        super(errorMessage);
    }
}
