package ru.otus.homework.anton.sokolov.cashmachine.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetMoneyRequestParameters {
    private CardCredentials credentials;
    private long amount;
}
