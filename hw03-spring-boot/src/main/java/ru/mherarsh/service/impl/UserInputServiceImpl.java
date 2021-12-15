package ru.mherarsh.service.impl;

import org.springframework.stereotype.Service;
import ru.mherarsh.service.PrintAdapter;
import ru.mherarsh.service.ReaderAdapter;
import ru.mherarsh.service.ReaderValueValidator;
import ru.mherarsh.service.UserInputService;

@Service
public class UserInputServiceImpl implements UserInputService {
    private final PrintAdapter printAdapter;
    private final ReaderAdapter readerAdapter;

    public UserInputServiceImpl(PrintAdapter printAdapter, ReaderAdapter readerAdapter) {
        this.printAdapter = printAdapter;
        this.readerAdapter = readerAdapter;
    }

    @Override
    public String getInput(String description, ReaderValueValidator validator) {
        var userInput = "";

        do {
            printAdapter.print(description + ": ");
            userInput = readerAdapter.readLine();
        } while (!validator.isValid(userInput));

        return userInput;
    }
}
