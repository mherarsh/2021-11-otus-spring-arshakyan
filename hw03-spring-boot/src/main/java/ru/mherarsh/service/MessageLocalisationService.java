package ru.mherarsh.service;

public interface MessageLocalisationService {
    String getMessage(String code, Object... args);

    String getMessage(String code, Object arg);

    String getMessage(String code);
}
