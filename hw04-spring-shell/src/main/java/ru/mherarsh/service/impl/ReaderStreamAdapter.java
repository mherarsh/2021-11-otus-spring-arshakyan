package ru.mherarsh.service.impl;

import org.springframework.beans.factory.DisposableBean;
import ru.mherarsh.service.ReaderAdapter;

import java.io.InputStream;
import java.util.Scanner;

public class ReaderStreamAdapter implements ReaderAdapter, DisposableBean {
    private final Scanner scanner;

    public ReaderStreamAdapter(InputStream inputStream) {
        scanner = new Scanner(inputStream);
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    @Override
    public void destroy() {
        scanner.close();
    }
}
