package ru.otus.homework.anton.sokolov;

import ru.otus.homework.anton.sokolov.atm.Banknotes;
import ru.otus.homework.anton.sokolov.atm.CashMachine;
import ru.otus.homework.anton.sokolov.atm.CashMachineImpl;

import java.util.Arrays;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(Banknotes.values()));
        CashMachine cashMachine = new CashMachineImpl(2);
        System.out.println(cashMachine.getBalance());
        System.out.println("-------------------------------");

        Map<Banknotes, Long> money = cashMachine.getMoney(17500);
        System.out.println(money);
        System.out.println(cashMachine.getBalance());
        System.out.println("-------------------------------");

        long l = cashMachine.putMoney(money);
        System.out.println(l);
        System.out.println(cashMachine.getBalance());
    }
}