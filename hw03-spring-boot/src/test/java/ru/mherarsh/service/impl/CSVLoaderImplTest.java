package ru.mherarsh.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mherarsh.service.CSVLoader;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class CSVLoaderImplTest {
    @Configuration
    static class BeanConfig {
        private static final String CSV_SEPARATOR = ";";

        @Bean
        CSVLoader csvLoader() {
            return new CSVLoaderImpl(CSV_SEPARATOR);
        }
    }

    @Autowired
    private CSVLoader csvLoader;

    @Test
    @DisplayName("loadFileFromResourceTest: load non-existent resource file")
    void loadFileFromResourceNonExistentTest() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> csvLoader.loadFileFromResource("no-file.csv"));
    }

    @Test
    @DisplayName("loadFileFromResourceTest: load existent resource file")
    void loadFileFromResourceExistingTest() {
        assertThatNoException()
                .isThrownBy(() -> csvLoader.loadFileFromResource("csv-loader-data-test.csv"));
    }

    @Test
    @DisplayName("loadFileFromResourceTest: dataset test")
    void loadFileFromResourceDataSetTest() {
        assertThatNoException().isThrownBy(() -> {
            var originalData = getOriginalData();
            var loadedData = csvLoader.loadFileFromResource("csv-loader-data-test.csv");

            assertThat(originalData).isEqualTo(loadedData);
        });
    }

    private static List<List<String>> getOriginalData() {
        return List.of(
                List.of("a1", "a2", "a3", "a4", "a5"),
                List.of("b1", "b2", "b3"),
                List.of("c1", "c2", "c3"),
                List.of("1", "2", "3", "4", "5", "6", "7", "8")
        );
    }
}