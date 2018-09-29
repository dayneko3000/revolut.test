package ru.revolut.exception;

import java.math.BigDecimal;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(long number, BigDecimal amount) {
        super("Account with number " + number + " has less then " + amount + " money amount");
    }
}
