package ru.otus.homework.anton.sokolov.cashmachine.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardCredentials {
    private String cardNumber;
    private String pin;
}
