package ru.mherarsh.service.impl;

import org.springframework.stereotype.Service;
import ru.mherarsh.domain.Person;
import ru.mherarsh.service.PersonService;
import ru.mherarsh.service.UserInputService;

@Service
public class PersonServiceImpl implements PersonService {
    private final UserInputService userInputService;

    public PersonServiceImpl(UserInputService userInputService) {
        this.userInputService = userInputService;
    }

    @Override
    public Person getNewPerson() {
        return Person.builder().name(readName()).build();
    }

    private String readName() {
        return userInputService.getInput("Please input your name", x -> !x.isBlank());
    }
}
