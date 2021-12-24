package ru.mherarsh.service.impl;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.mherarsh.service.LocaleConfigProvider;
import ru.mherarsh.service.MessageLocalisationService;

@Service
public class MessageLocalisationServiceImpl implements MessageLocalisationService {
    private final MessageSource messageSource;
    private final LocaleConfigProvider localeConfigProvider;

    public MessageLocalisationServiceImpl(MessageSource messageSource, LocaleConfigProvider localeConfigProvider) {
        this.messageSource = messageSource;
        this.localeConfigProvider = localeConfigProvider;
    }

    @Override
    public String getMessage(String code, Object... args) {
        return messageSource.getMessage(code, args, localeConfigProvider.getLocale());
    }
}
