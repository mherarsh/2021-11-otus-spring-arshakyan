package ru.mherarsh.service.impl;

import org.springframework.stereotype.Service;
import ru.mherarsh.domain.Person;
import ru.mherarsh.service.MessageLocalisationService;
import ru.mherarsh.service.PersonService;
import ru.mherarsh.service.UserInputService;

@Service
public class PersonServiceImpl implements PersonService {
    private final UserInputService userInputService;
    private final MessageLocalisationService localisationService;

    public PersonServiceImpl(UserInputService userInputService, MessageLocalisationService localisationService) {
        this.userInputService = userInputService;
        this.localisationService = localisationService;
    }

    @Override
    public Person getNewPerson() {
        return Person.fromName(readName());
    }

    private String readName() {
        return userInputService.getInput(
                localisationService.getMessage("strings.input-name"),
                x -> !x.isBlank()
        );
    }
}
