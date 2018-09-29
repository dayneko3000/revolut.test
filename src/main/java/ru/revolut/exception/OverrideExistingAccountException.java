package ru.revolut.exception;

public class OverrideExistingAccountException extends RuntimeException {
    public OverrideExistingAccountException(long number) {
        super("Account with number " + number + " already exists");
    }
}
