package ru.mherarsh.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.mherarsh.dao.QuestionRepository;
import ru.mherarsh.dao.impl.QuestionRepositoryCSV;
import ru.mherarsh.service.*;
import ru.mherarsh.service.impl.*;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {
    @Value("${csv.separator}")
    private String csvSeparator;

    @Value("${csv.path}")
    private String csvPath;

    @Value("${test.minimum.score}")
    private int testMinimumScore;

    @Bean
    CSVLoader csvLoader() {
        return new CSVLoaderImpl(csvSeparator);
    }

    @Bean
    QuestionRepository questionRepository(CSVLoader csvLoader) {
        return new QuestionRepositoryCSV(csvLoader, csvPath);
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
    TestScoreCalculationService testResultCalculationService(PrintAdapter printAdapter){
        return new TestScoreCalculationServiceImpl(printAdapter, testMinimumScore);
    }
}
