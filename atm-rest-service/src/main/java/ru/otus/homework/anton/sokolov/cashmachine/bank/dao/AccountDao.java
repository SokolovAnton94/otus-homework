package ru.otus.homework.anton.sokolov.cashmachine.bank.dao;

import org.springframework.stereotype.Repository;
import ru.otus.homework.anton.sokolov.cashmachine.bank.data.Account;
import ru.otus.homework.anton.sokolov.cashmachine.bank.db.Accounts;

@Repository
public class AccountDao {
    public Account getAccount(Long accountId) {
        if (!Accounts.accounts.containsKey(accountId)) {
            throw new IllegalArgumentException("Account not found");
        }
        return Accounts.accounts.get(accountId);
    }

    public Account saveAccount(Account account) {
        if (account.getId() <= 0) {
            account.setId(Accounts.getNextId());
        }
        Accounts.accounts.put(account.getId(), account);
        return account;
    }
}
