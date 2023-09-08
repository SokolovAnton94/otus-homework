package ru.otus.homework.anton.sokolov.cashmachine.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.homework.anton.sokolov.cashmachine.dto.*;
import ru.otus.homework.anton.sokolov.cashmachine.machine.data.CashMachine;
import ru.otus.homework.anton.sokolov.cashmachine.machine.service.CashMachineService;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AtmController {

    private final CashMachineService cashMachineService;
    private final CashMachine cashMachine;

    @GetMapping("/balance/data")
    public String getBalanceData(Model model) {
        model.addAttribute("credentials", new CardCredentials());
        return "balance/data";
    }

    @GetMapping("/balance/view")
    public String getBalance(CardCredentials credentials, Model model) {
        try {
            BigDecimal balance = cashMachineService.checkBalance(cashMachine, credentials.getCardNumber(), credentials.getPin());
            model.addAttribute("balance", balance);
            return "balance/result";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "balance/error";
        } catch (Exception e) {
            model.addAttribute("error", e);
            return "error";
        }
    }

    @GetMapping("/money/get/data")
    public String getMoneyData(Model model) {
        model.addAttribute("parameters", new GetMoneyRequestParameters());
        return "money/get/data";
    }

    @GetMapping("/get/money")
    public String getMoney(GetMoneyRequestParameters parameters, Model model) {
        try {
            CardCredentials credentials = parameters.getCredentials();
            List<Integer> listOfMoney = cashMachineService.getMoney(cashMachine, credentials.getCardNumber(), credentials.getPin(), BigDecimal.valueOf(parameters.getAmount()));
            model.addAttribute("listOfMoney", listOfMoney);
            return "money/get/result";
        } catch (RuntimeException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IllegalArgumentException || cause instanceof IllegalStateException) {
                model.addAttribute("errorMessage", cause.getMessage());
                return "money/get/error";
            }
            model.addAttribute("error", e);
            return "error";
        } catch (Exception e) {
            model.addAttribute("error", e);
            return "error";
        }
    }

    @GetMapping("/money/put/data")
    public String putMoneyData(Model model) {
        model.addAttribute("parameters", new PutMoneyRequestParameters());
        return "money/put/data";
    }

    @PostMapping("/put/money")
    public String putMoney(PutMoneyRequestParameters parameters, Model model) {
        try {
            Banknotes banknotes = parameters.getBanknotes();
            CardCredentials credentials = parameters.getCredentials();
            List<Integer> putBanknotes = List.of(banknotes.getNote5000(), banknotes.getNote1000(), banknotes.getNote500(), banknotes.getNote100());

            BigDecimal allAccountMoney = cashMachineService.putMoney(cashMachine, credentials.getCardNumber(), credentials.getPin(), putBanknotes);

            model.addAttribute("allAccountMoney", allAccountMoney);
            return "money/put/result";
        } catch (IllegalArgumentException | IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "money/put/error";
        } catch (Exception e) {
            model.addAttribute("error", e);
            return "error";
        }
    }

    @GetMapping("pin/data")
    public String changePin(Model model) {
        model.addAttribute("parameters", new ChangePinRequestParameters());
        return "/pin/data";
    }

    @PostMapping("/pin/change")
    public String changePin(ChangePinRequestParameters parameters, Model model) {
        try {
            CardCredentials credentials = parameters.getCredentials();
            boolean result = cashMachineService.changePin(credentials.getCardNumber(), credentials.getPin(), parameters.getNewPin());

            if (result) {
                model.addAttribute("result", "The pin was changed successfully.");
            } else {
                model.addAttribute("result", "Something went wrong, try again later.");
            }

            return "pin/result";
        } catch (IllegalArgumentException | IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "pin/error";
        } catch (Exception e) {
            model.addAttribute("error", e);
            return "error";
        }
    }
}