package ru.otus.homework.anton.sokolov.cashmachine.bank.service;

import ru.otus.homework.anton.sokolov.cashmachine.bank.data.Account;

import java.math.BigDecimal;

public interface AccountService {
    Account createAccount(BigDecimal amount);

    Account getAccount(Long id);

    BigDecimal getMoney(Long id, BigDecimal amount);

    BigDecimal putMoney(Long id, BigDecimal amount);

    BigDecimal checkBalance(Long id);

}