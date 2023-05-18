package otus.study.cashmachine.bank.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import otus.study.cashmachine.bank.dao.AccountDao;
import otus.study.cashmachine.bank.data.Account;
import otus.study.cashmachine.bank.service.impl.AccountServiceImpl;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    @Mock
    AccountDao accountDao;

    AccountServiceImpl accountServiceImpl;

    @Test
    void createAccountMock() {
        accountServiceImpl = new AccountServiceImpl(accountDao);
        Account testAccount = new Account(1L, BigDecimal.TEN);
        when(accountDao.saveAccount(any())).thenReturn(testAccount);
        assertEquals(testAccount, accountServiceImpl.createAccount(BigDecimal.TEN));
// @TODO test account creation with mock and ArgumentMatcher
    }

    @Test
    void createAccountCaptor() {
        accountServiceImpl = new AccountServiceImpl(accountDao);
        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        when(accountDao.saveAccount(accountCaptor.capture())).thenCallRealMethod();
        Account resultAccount = accountServiceImpl.createAccount(BigDecimal.ONE);
        assertEquals(0, accountCaptor.getValue().getAmount().compareTo(resultAccount.getAmount()));
//  @TODO test account creation with ArgumentCaptor
    }

    @Test
    void addSum() {
    }

    @Test
    void getSum() {
    }

    @Test
    void getAccount() {
    }

    @Test
    void checkBalance() {
    }
}
