package ru.mherarsh.service.impl;

import ru.mherarsh.dao.QuestionRepository;
import ru.mherarsh.domain.Question;
import ru.mherarsh.enums.QuestionPrintMode;
import ru.mherarsh.service.QuestionsPrinter;

public class QuestionsPrinterTerminal implements QuestionsPrinter {
    private final QuestionRepository questionRepository;

    public QuestionsPrinterTerminal(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public void printQuestions(QuestionPrintMode printMode) {
        QuestionFormatter formatter = this::questionAndVariantsFormatter;

        if (printMode == QuestionPrintMode.QUESTIONS_AND_ANSWERS) {
            formatter = this::questionAndAnswersFormatter;
        }

        printer(formatter);
    }

    private void printer(QuestionFormatter formatter) {
        var questions = questionRepository.getQuestions();
        for (var question : questions) {
            System.out.println(formatter.format(question));
        }
    }

    private String questionAndAnswersFormatter(Question question) {
        return getQuestionString(question) + "\n" +
                getAnswerString(question) + "\n" +
                getAnswerVariantsString(question);
    }

    private String questionAndVariantsFormatter(Question question) {
        return getQuestionString(question) + "\n" +
                getAnswerVariantsString(question);
    }

    private String getQuestionString(Question question) {
        return String.format("Q: %s", question.getQuestion());
    }

    private String getAnswerString(Question question) {
        return String.format("A: %s", question.getAnswer());
    }

    private String getAnswerVariantsString(Question question) {
        var variantsBuilder = new StringBuilder();
        variantsBuilder.append("Variants:\n");

        for (var variant : question.getAnswerVariants()) {
            variantsBuilder
                    .append(String.format("\t%s. ", variant.getId()))
                    .append(variant)
                    .append("\n");
        }

        return variantsBuilder.toString();
    }

    private interface QuestionFormatter {
        String format(Question question);
    }
}