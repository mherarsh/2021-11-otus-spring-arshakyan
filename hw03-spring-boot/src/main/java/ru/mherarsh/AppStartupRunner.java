package ru.mherarsh;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.mherarsh.service.TestingService;

@Component
public class AppStartupRunner implements ApplicationRunner {
    private final TestingService testingService;

    public AppStartupRunner(TestingService testingService) {
        this.testingService = testingService;
    }

    @Override
    public void run(ApplicationArguments args) {
        testingService.startTest();
    }
}
