package ru.revolut.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class Account implements Serializable {
    private final long number;
    private volatile BigDecimal amount;

    public Account(long number, BigDecimal amount) {
        this.number = number;
        this.amount = amount;
    }

    public long getNumber() {
        return number;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public boolean withdraw(BigDecimal withdrawAmount) {
        if (amount.compareTo(withdrawAmount) >= 0) {
            amount = amount.subtract(withdrawAmount);
            return true;
        }

        return false;
    }

    public void deposit(BigDecimal sum) {
        amount = amount.add(sum);
    }
}
