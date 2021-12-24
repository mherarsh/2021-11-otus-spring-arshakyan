package ru.mherarsh;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.mherarsh.service.TestLangProvider;
import ru.mherarsh.service.TestingService;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@ShellComponent
public class AppShell {
    private final TestingService testingService;
    private final TestLangProvider testLangProvider;

    public AppShell(TestingService testingService, TestLangProvider testLangProvider) {
        this.testingService = testingService;
        this.testLangProvider = testLangProvider;
    }

    @ShellMethod(value = "Get available test languages.", key = "get-langs")
    List<String> getAvailableLangs() {
        return testLangProvider.getTestAvailableLangs();
    }

    @ShellMethod(value = "Set test language.", key = "set-lang")
    void setLang(@NotEmpty String lang) {
        testLangProvider.setTestLang(lang);
    }

    @ShellMethod(value = "Start person testing.", key = "start-test")
    void startTest() {
        testingService.startTest();
    }
}
