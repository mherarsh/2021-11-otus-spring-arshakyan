package ru.mherarsh.service.impl;

import ru.mherarsh.service.PrintAdapter;

import java.io.PrintStream;

public class PrintStreamAdapter implements PrintAdapter {
    private final PrintStream printStream;

    public PrintStreamAdapter(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    public void print(String message) {
        printStream.println(message);
        printStream.flush();
    }
}
