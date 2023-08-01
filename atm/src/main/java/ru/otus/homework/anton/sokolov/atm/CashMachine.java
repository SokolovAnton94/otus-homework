package ru.otus.homework.anton.sokolov.atm;

import java.util.Map;

public interface CashMachine {

    Map<Banknotes, Long> getMoney(long amount);

    long putMoney(Map<Banknotes, Long> banknotes);

    long getBalance();
}
