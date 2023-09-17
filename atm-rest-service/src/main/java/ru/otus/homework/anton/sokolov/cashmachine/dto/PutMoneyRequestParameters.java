package ru.otus.homework.anton.sokolov.cashmachine.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PutMoneyRequestParameters {
    private CardCredentials credentials;
    private Banknotes banknotes;
}
