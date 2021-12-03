package ru.mherarsh.service.impl;

import ru.mherarsh.domain.Question;
import ru.mherarsh.service.QuestionEncoder;

public class QuestionAndAnswersEncoder implements QuestionEncoder {
    @Override
    public String encode(Question question) {
        return String.format(
                "%s%s%s",
                getQuestionString(question),
                System.lineSeparator(),
                getAnswerVariantsString(question)
        );
    }

    private String getQuestionString(Question question) {
        return String.format("Q: %s", question.getQuestionDescription());
    }

    private String getAnswerVariantsString(Question question) {
        var variantsBuilder = new StringBuilder()
                .append("Variants:").append(System.lineSeparator());

        for (var i = 0; i < question.getAnswerVariants().size(); i++) {
            var answers = question.getAnswerVariants().get(i);

            variantsBuilder
                    .append(String.format("\t%s. ", answers.getId()))
                    .append(answers.getAnswerDescription());

            appendNewLineIfNotLast(question, variantsBuilder, i);
        }

        return variantsBuilder.toString();
    }

    private void appendNewLineIfNotLast(Question question, StringBuilder variantsBuilder, int i) {
        if (i != question.getAnswerVariants().size() - 1) {
            variantsBuilder.append(System.lineSeparator());
        }
    }
}
