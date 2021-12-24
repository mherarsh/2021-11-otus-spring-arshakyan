package ru.mherarsh.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mherarsh.domain.Question;
import ru.mherarsh.service.PrintAdapter;
import ru.mherarsh.service.QuestionEncoder;
import ru.mherarsh.service.QuestionsPrinter;

@Service
@AllArgsConstructor
public class QuestionsPrinterImpl implements QuestionsPrinter {
    private final QuestionEncoder questionEncoder;
    private final PrintAdapter printAdapter;

    @Override
    public void printQuestion(Question question) {
        var encodedQuestion = questionEncoder.encode(question);
        printAdapter.println(encodedQuestion);
    }
}
