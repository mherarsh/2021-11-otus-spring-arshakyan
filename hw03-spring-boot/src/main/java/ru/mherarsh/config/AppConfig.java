package ru.mherarsh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mherarsh.service.MessageLocalisationService;
import ru.mherarsh.service.PrintAdapter;
import ru.mherarsh.service.ReaderAdapter;
import ru.mherarsh.service.TestScoreCalculationService;
import ru.mherarsh.service.impl.PrintStreamAdapter;
import ru.mherarsh.service.impl.ReaderStreamAdapter;
import ru.mherarsh.service.impl.TestScoreCalculationServiceImpl;

@Configuration
public class AppConfig {
    private final AppProperties appProperties;

    public AppConfig(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Bean
    PrintAdapter printAdapter() {
        return new PrintStreamAdapter(System.out);
    }

    @Bean
    ReaderAdapter readerAdapter() {
        return new ReaderStreamAdapter(System.in);
    }

    @Bean
    TestScoreCalculationService testResultCalculationService(PrintAdapter printAdapter, MessageLocalisationService localisationService) {
        return new TestScoreCalculationServiceImpl(printAdapter, appProperties.getTest().getMinimumScore(), localisationService);
    }
}
