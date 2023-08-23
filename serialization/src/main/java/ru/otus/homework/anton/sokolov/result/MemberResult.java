package ru.otus.homework.anton.sokolov.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberResult {
    private String last;

    @Override
    public String toString() {
        return "last='" + last + '\'';
    }
}
