package ru.mherarsh.service.impl;

import org.springframework.stereotype.Service;
import ru.mherarsh.service.AnswerIndexMapper;

@Service
public class AnswerIndexMapperNumbers implements AnswerIndexMapper {
    @Override
    public String indexToDescription(int index) {
        return "" + (index + 1);
    }

    @Override
    public int indexFromDescription(String description) {
        return Integer.parseInt(description) - 1;
    }
}
