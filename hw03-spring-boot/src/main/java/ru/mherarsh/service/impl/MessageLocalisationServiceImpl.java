package ru.mherarsh.service.impl;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.mherarsh.service.LocaleConfig;
import ru.mherarsh.service.MessageLocalisationService;

@Service
public class MessageLocalisationServiceImpl implements MessageLocalisationService {
    private final MessageSource messageSource;
    private final LocaleConfig localeConfig;

    public MessageLocalisationServiceImpl(MessageSource messageSource, LocaleConfig localeConfig) {
        this.messageSource = messageSource;
        this.localeConfig = localeConfig;
    }

    @Override
    public String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, localeConfig.getLocale());
    }

    @Override
    public String getMessage(String code, Object arg) {
        var args = new Object[]{arg};
        return getMessage(code, args);
    }

    @Override
    public String getMessage(String code) {
        var args = new Object[]{};
        return getMessage(code, args);
    }
}
