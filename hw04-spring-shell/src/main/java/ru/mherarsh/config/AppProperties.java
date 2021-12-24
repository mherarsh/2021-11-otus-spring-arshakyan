package ru.mherarsh.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import ru.mherarsh.service.CSVSource;
import ru.mherarsh.service.CSVSourceProvider;
import ru.mherarsh.service.LocaleConfigProvider;
import ru.mherarsh.service.TestLangProvider;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@Component
@Validated
@ConfigurationProperties("app")
public class AppProperties implements LocaleConfigProvider, CSVSourceProvider, TestLangProvider {
    private static final String DEFAULT_LANG_CONFIG_NAME = "default";

    private String lang;

    @NotEmpty
    private Map<String, AppLangConfig> langs;

    @NotNull
    private Test test;

    @Override
    public Locale getLocale() {
        return Locale.forLanguageTag(getAppLangConfig().getLocale());
    }

    @Override
    public CSVSource getCsvSource() {
        return getAppLangConfig();
    }

    @Override
    public List<String> getTestAvailableLangs() {
        return langs.keySet().stream()
                .filter(x -> !x.equals(DEFAULT_LANG_CONFIG_NAME))
                .collect(Collectors.toList());
    }

    @Override
    public void setTestLang(String lang) {
        if (!getTestAvailableLangs().contains(lang)) {
            throw new IllegalArgumentException("unavailable test language: " + lang);
        }

        this.lang = lang;
    }

    private AppLangConfig getAppLangConfig() {
        var langConfigName = getAppLangConfigName();

        if (!langs.containsKey(langConfigName)) {
            throw new IllegalArgumentException("resources for language '" + langConfigName + "' not found");
        }

        return langs.get(langConfigName);
    }

    private String getAppLangConfigName() {
        if (lang == null || lang.isBlank() || lang.equals(DEFAULT_LANG_CONFIG_NAME)) {
            return DEFAULT_LANG_CONFIG_NAME;
        }

        return lang;
    }

    @Getter
    @Setter
    public static class AppLangConfig implements CSVSource {
        private String csvSeparator;
        private String csvPath;
        private String locale;
    }

    @Getter
    @Setter
    public static class Test {
        private int minimumScore;
    }
}
