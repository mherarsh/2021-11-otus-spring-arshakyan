package ru.mherarsh.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Getter
@Setter
@Component
@Validated
@ConfigurationProperties("app")
public class AppProperties {
    private static final String DEFAULT_LANG_CONFIG_NAME = "default";

    private String lang;

    @NotEmpty
    private Map<String, LocaleConfig> langs;

    @NotNull
    private Test test;

    public LocaleConfig getLocaleConfig() {
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
    public static class LocaleConfig {
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
