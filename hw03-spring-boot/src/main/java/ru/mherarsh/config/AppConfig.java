package ru.mherarsh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mherarsh.dao.QuestionRepository;
import ru.mherarsh.dao.impl.QuestionRepositoryCSV;
import ru.mherarsh.service.*;
import ru.mherarsh.service.impl.CSVLoaderImpl;
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
    CSVLoader csvLoader() {
        return new CSVLoaderImpl(appProperties.getLocaleConfig().getCsvSeparator());
    }

    @Bean
    QuestionRepository questionRepository(CSVLoader csvLoader) {
        return new QuestionRepositoryCSV(csvLoader, appProperties.getLocaleConfig().getCsvPath());
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
