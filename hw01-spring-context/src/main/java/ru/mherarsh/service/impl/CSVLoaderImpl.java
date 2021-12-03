package ru.mherarsh.service.impl;

import ru.mherarsh.service.CSVLoader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CSVLoaderImpl implements CSVLoader {
    private final String separator;

    public CSVLoaderImpl(String separator) {
        this.separator = separator;
    }

    @Override
    public List<List<String>> loadFileFromResource(String path) {
        try (var fileStream = getFileFromResourceAsStream(path)) {
            var fileLines = readStreamAsList(fileStream);

            return splitLines(fileLines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> readStreamAsList(InputStream inputStream) {
        try {
            var linesArray = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8).split(System.lineSeparator());

            return List.of(linesArray);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<List<String>> splitLines(List<String> fileLines) {
        var splittedLines = new ArrayList<List<String>>();

        for (var line : fileLines) {
            splittedLines.add(splitLinetBySeparator(line.trim()));
        }

        return splittedLines;
    }

    private List<String> splitLinetBySeparator(String line) {
        return List.of(line.split(separator));
    }

    private InputStream getFileFromResourceAsStream(String path) {
        var inputStream = getClass().getClassLoader().getResourceAsStream(path);

        if (inputStream == null) {
            throw new IllegalArgumentException("file in resource not found: " + path);
        }

        return inputStream;
    }
}
