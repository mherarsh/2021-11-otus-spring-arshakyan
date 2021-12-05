package ru.mherarsh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.mherarsh.service.TestingService;

@ComponentScan("ru.mherarsh")
@Configuration
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(App.class);
        context.getBean(TestingService.class).run();
    }
}
