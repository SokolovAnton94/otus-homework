package otus.study.cashmachine.machine.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import otus.study.cashmachine.TestUtil;
import otus.study.cashmachine.bank.dao.CardsDao;
import otus.study.cashmachine.bank.data.Card;
import otus.study.cashmachine.bank.service.AccountService;
import otus.study.cashmachine.bank.service.impl.CardServiceImpl;
import otus.study.cashmachine.machine.data.CashMachine;
import otus.study.cashmachine.machine.data.MoneyBox;
import otus.study.cashmachine.machine.service.impl.CashMachineServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CashMachineServiceTest {

    @Spy
    @InjectMocks
    private CardServiceImpl cardService;

    @Mock
    private CardsDao cardsDao;

    @Mock
    private AccountService accountService;

    @Mock
    private MoneyBoxService moneyBoxService;

    private CashMachineServiceImpl cashMachineService;

    private final CashMachine cashMachine = new CashMachine(new MoneyBox());

    private final String cardNum = "1234";
    private final String pin = "0000";
    private final String newPin = "9876";
    private final Card testCard = new Card(0, cardNum, 1L, TestUtil.getHash(pin));

    @BeforeEach
    void init() {
        cashMachineService = new CashMachineServiceImpl(cardService, accountService, moneyBoxService);
    }


    @Test
    void getMoney() {
        BigDecimal testAmount = BigDecimal.valueOf(1000);
        doReturn(testAmount).when(cardService).getMoney(eq(cardNum), eq(pin), eq(testAmount));
        cashMachineService.getMoney(cashMachine, cardNum, pin, testAmount);
        verify(moneyBoxService, only()).getMoney(any(), eq(testAmount.intValue()));
// @TODO create get money test using spy as mock
    }

    @Test
    void putMoney() {
    }

    @Test
    void checkBalance() {

    }

    @Test
    void changePin() {
        when(cardsDao.getCardByNumber(any())).thenReturn(testCard);
        cashMachineService.changePin(cardNum, pin, newPin);
        ArgumentCaptor<Card> cardCaptor = ArgumentCaptor.forClass(Card.class);
        verify(cardsDao).saveCard(cardCaptor.capture());
        assertEquals(cardCaptor.getValue().getPinCode(), TestUtil.getHash(newPin));
// @TODO create change pin test using spy as implementation and ArgumentCaptor and thenReturn
    }

    @Test
    void changePinWithAnswer() {
        when(cardsDao.getCardByNumber(any())).thenReturn(testCard);
        List<Card> cards = new ArrayList<>();
        when(cardsDao.saveCard(any(Card.class))).thenAnswer(invocation -> {
            Card card = invocation.getArgument(0);
            cards.add(card);
            return card;
        });
        cashMachineService.changePin(cardNum, pin, newPin);
        assertEquals(cards.get(0).getPinCode(), TestUtil.getHash(newPin));
// @TODO create change pin test using spy as implementation and mock an thenAnswer
    }
}