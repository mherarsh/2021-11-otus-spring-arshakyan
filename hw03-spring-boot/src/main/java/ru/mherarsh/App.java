package ru.mherarsh;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.mherarsh.service.TestingService;

@ComponentScan("ru.mherarsh")
@Configuration
public class App {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(App.class);
        context.getBean(TestingService.class).run();
    }
}
