package ru.mherarsh.service.impl;

import lombok.AllArgsConstructor;
import ru.mherarsh.dao.QuestionRepository;
import ru.mherarsh.service.PrintAdapter;
import ru.mherarsh.service.QuestionEncoder;
import ru.mherarsh.service.QuestionsPrinter;

@AllArgsConstructor
public class QuestionsPrinterImpl implements QuestionsPrinter {
    private final QuestionRepository questionRepository;
    private final QuestionEncoder questionEncoder;
    private final PrintAdapter printAdapter;

    @Override
    public void printQuestions() {
        var questions = questionRepository.getQuestions();

        for (var question : questions) {
            var encodedQuestion = questionEncoder.encode(question);

            printAdapter.print(encodedQuestion);
        }
    }
}
