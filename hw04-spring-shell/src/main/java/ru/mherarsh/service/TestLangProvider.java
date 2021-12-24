package ru.mherarsh.service;

import java.util.List;

public interface TestLangProvider {
    List<String> getTestAvailableLangs();
    void setTestLang(String lang);
}
