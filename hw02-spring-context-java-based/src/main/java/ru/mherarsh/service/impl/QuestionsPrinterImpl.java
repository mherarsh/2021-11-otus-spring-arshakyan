package ru.mherarsh.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mherarsh.dao.QuestionRepository;
import ru.mherarsh.domain.Question;
import ru.mherarsh.service.PrintAdapter;
import ru.mherarsh.service.QuestionEncoder;
import ru.mherarsh.service.QuestionsPrinter;

@Service
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

            printAdapter.println(encodedQuestion);
        }
    }

    @Override
    public void printQuestion(Question question) {
        var encodedQuestion = questionEncoder.encode(question);
        printAdapter.println(encodedQuestion);
    }
}
