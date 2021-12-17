package ru.mherarsh.service.impl;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.mherarsh.config.AppProperties;
import ru.mherarsh.service.MessageLocalisationService;

import java.util.Locale;

@Service
public class MessageLocalisationServiceImpl implements MessageLocalisationService {
    private final MessageSource messageSource;
    private final AppProperties appProperties;

    public MessageLocalisationServiceImpl(MessageSource messageSource, AppProperties appProperties) {
        this.messageSource = messageSource;
        this.appProperties = appProperties;
    }

    @Override
    public String getMessage(String code, Object[] args) {
        var locale = Locale.forLanguageTag(appProperties.getLocaleConfig().getLocale());

        return messageSource.getMessage(code, args, locale);
    }

    @Override
    public String getMessage(String code, Object arg) {
        return getMessage(code, new Object[]{arg});
    }

    @Override
    public String getMessage(String code) {
        return getMessage(code, new Object[]{});
    }
}
