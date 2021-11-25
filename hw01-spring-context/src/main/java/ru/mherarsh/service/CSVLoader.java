package ru.mherarsh.service;

import java.util.List;

public interface CSVLoader {
    void setSeparator(String separator);
    String getSeparator();
    List<List<String>> loadFileFromResource(String path);
}