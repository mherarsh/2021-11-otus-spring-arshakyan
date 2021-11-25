package ru.mherarsh;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.mherarsh.enums.QuestionPrintMode;
import ru.mherarsh.service.QuestionsPrinter;

public class App {
    private static ClassPathXmlApplicationContext context;

    public static void main(String[] args) {
        try {
            context = new ClassPathXmlApplicationContext("spring-context.xml");

            var questionsPrinter= context.getBean(QuestionsPrinter.class);
            questionsPrinter.printQuestions(QuestionPrintMode.QUESTIONS_AND_VARIANTS);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            context.close();
        }
    }
}