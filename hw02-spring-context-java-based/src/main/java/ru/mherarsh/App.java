package ru.mherarsh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.mherarsh.service.QuestionsPrinter;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        try (var context = new ClassPathXmlApplicationContext("spring-context.xml")) {
            var questionsPrinter = context.getBean(QuestionsPrinter.class);

            questionsPrinter.printQuestions();

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
