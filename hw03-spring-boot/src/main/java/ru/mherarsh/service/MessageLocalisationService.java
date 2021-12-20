package ru.mherarsh.service;

public interface MessageLocalisationService {
    String getMessage(String code, Object... args);
}
