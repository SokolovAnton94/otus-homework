package ru.otus.homework.anton.sokolov.atm;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static ru.otus.homework.anton.sokolov.atm.Banknotes.*;

public class CashMachineImpl implements CashMachine {

    Map<Banknotes, Long> money;

    public CashMachineImpl() {
       this(5);
    }

    public CashMachineImpl(long banknotesCount) {
        money = new TreeMap<>();
        for (Banknotes value : values()) {
            money.put(value, banknotesCount);
        }
    }

    @Override
    public Map<Banknotes, Long> getMoney(long amount) {
        checkEnoughMoney(amount);

        Map<Banknotes, Long> result = new HashMap<>();

        for (Map.Entry<Banknotes, Long> entry : money.entrySet()) {
            amount = getBanknotes(amount, result, entry.getKey(), entry.getValue());
        }

        checkEnoughBanknotes(amount);

        for (Map.Entry<Banknotes, Long> entry : result.entrySet()) {
            Banknotes banknoteDenomination = entry.getKey();
            money.replace(banknoteDenomination, money.get(banknoteDenomination) - entry.getValue());
        }
        return result;
    }

    @Override
    public long putMoney(Map<Banknotes, Long> banknotes) {
        long result = 0;
        for (Map.Entry<Banknotes, Long> entry : banknotes.entrySet()) {
            Banknotes banknoteDenomination = entry.getKey();
            Long banknotesCount = entry.getValue();
            result += banknoteDenomination.getValue() * banknotesCount;

            money.replace(banknoteDenomination, money.get(banknoteDenomination) + banknotesCount);
        }
        return result;
    }

    @Override
    public long getBalance() {
        long result = 0;
        for (Map.Entry<Banknotes, Long> entry : money.entrySet()) {
            result += entry.getKey().getValue() * entry.getValue();
        }
        return result;
    }

    private void checkEnoughMoney(long amount) {
        if (!(amount <= getBalance())) {
            throw new IllegalArgumentException("Not enough money");
        }
    }

    private Long getBanknotes(long amount, Map<Banknotes, Long> result, Banknotes banknote, Long banknoteCount) {
        long requiredBanknoteCount = amount / banknote.getValue();
        long resultBanknoteCount;
        if (requiredBanknoteCount <= banknoteCount) {
            resultBanknoteCount = requiredBanknoteCount;
        } else {
            resultBanknoteCount = banknoteCount;
        }
        result.put(banknote, resultBanknoteCount);
        return amount - resultBanknoteCount * banknote.getValue();
    }

    private static void checkEnoughBanknotes(long amount) {
        if (amount > 0) {
            throw new IllegalStateException("Not enough banknotes");
        }
    }
}