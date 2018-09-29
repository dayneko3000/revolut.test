package ru.revolut.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(long number) {
        super("Account with number " + number + " not found");
    }
}
