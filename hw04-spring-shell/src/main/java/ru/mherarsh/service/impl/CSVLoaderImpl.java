package ru.mherarsh.service.impl;

import org.springframework.stereotype.Service;
import ru.mherarsh.service.CSVLoader;
import ru.mherarsh.service.CSVSource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVLoaderImpl implements CSVLoader {
    private static final String WINDOWS_LINE_SEPARATOR = "\r\n";
    private static final String LINUX_LINE_SEPARATOR = "\n";

    @Override
    public List<List<String>> loadFileFromResource(CSVSource csvSource) {
        try (var fileStream = getFileFromResourceAsStream(csvSource.getCsvPath())) {
            var fileLines = readStreamAsList(fileStream);

            return splitLines(fileLines, csvSource.getCsvSeparator());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> readStreamAsList(InputStream inputStream) {
        try {
            var inputString = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            var linesArray = inputString.split(getLineSeparator(inputString));

            return List.of(linesArray);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getLineSeparator(String text) {
        if (text != null && text.contains(WINDOWS_LINE_SEPARATOR)) {
            return WINDOWS_LINE_SEPARATOR;
        }

        return LINUX_LINE_SEPARATOR;
    }

    private List<List<String>> splitLines(List<String> fileLines, String separator) {
        var splittedLines = new ArrayList<List<String>>();

        for (var line : fileLines) {
            splittedLines.add(splitLinetBySeparator(line.trim(), separator));
        }

        return splittedLines;
    }

    private List<String> splitLinetBySeparator(String line, String separator) {
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
