package ru.otus.homework.anton.sokolov;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class HelloOtus {
    private static final String MESSAGE = "In %s: %s Otus!";
    public static void main(String... args) {
        printHello();
    }

    public static void printHello() {
        Map<String, String> hello = ImmutableMap.of(
                "English", "Hello",
                "Arabian", "Marhaba",
                "Belarusian", "Pryvitanne",
                "Bulgarian", "Zdravejte",
                "Hungarian", "Jo napot",
                "Vietnamese", "Chao",
                "Hawaiian", "Aloha",
                "Dutch", "Hallo",
                "Greek", "Geia sou",
                "Georgian", "Gamardzhoba");
        hello.forEach((key, value) -> System.out.println(String.format(MESSAGE, key, value)));
    }
}
