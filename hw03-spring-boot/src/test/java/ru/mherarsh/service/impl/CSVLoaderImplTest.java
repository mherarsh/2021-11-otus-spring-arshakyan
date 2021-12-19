package ru.mherarsh.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.mherarsh.service.CSVLoader;
import ru.mherarsh.service.CSVSource;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@SpringBootTest(classes = {CSVLoaderImpl.class})
class CSVLoaderImplTest {
    @MockBean
    private CSVSource csvSource;

    @Autowired
    private CSVLoader csvLoader;

    @BeforeEach
    void setUp() {
        doReturn(";")
                .when(csvSource)
                .getCsvSeparator();
    }

    @Test
    @DisplayName("loadFileFromResourceTest: load non-existent resource file")
    void loadFileFromResourceNonExistentTest() {
        doReturn("no-file.csv")
                .when(csvSource)
                .getCsvPath();

        assertThatIllegalArgumentException()
                .isThrownBy(() -> csvLoader.loadFileFromResource(csvSource));
    }

    @Test
    @DisplayName("loadFileFromResourceTest: load existent resource file")
    void loadFileFromResourceExistingTest() {
        doReturn("csv-loader-data-test.csv")
                .when(csvSource)
                .getCsvPath();

        assertThatNoException()
                .isThrownBy(() -> csvLoader.loadFileFromResource(csvSource));
    }

    @Test
    @DisplayName("loadFileFromResourceTest: dataset test")
    void loadFileFromResourceDataSetTest() {
        doReturn("csv-loader-data-test.csv")
                .when(csvSource)
                .getCsvPath();

        assertThatNoException().isThrownBy(() -> {
            var originalData = getOriginalData();
            var loadedData = csvLoader.loadFileFromResource(csvSource);

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