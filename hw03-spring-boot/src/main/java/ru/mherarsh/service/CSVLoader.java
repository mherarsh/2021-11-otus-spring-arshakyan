package ru.mherarsh.service;

import java.util.List;

public interface CSVLoader {
    List<List<String>> loadFileFromResource(CSVSource csvSource);
}
