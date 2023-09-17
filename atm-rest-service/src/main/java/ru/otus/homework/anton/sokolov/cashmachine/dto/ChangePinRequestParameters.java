package ru.otus.homework.anton.sokolov.cashmachine.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePinRequestParameters {
    private CardCredentials credentials;
    private String newPin;
}
