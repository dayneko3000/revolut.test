package ru.revolut.process;

import ru.revolut.dto.Account;
import ru.revolut.exception.InsufficientFundsException;

import java.math.BigDecimal;

public class Transfer {

    private final Account from;
    private final Account to;
    private final BigDecimal amount;

    public Transfer(Account from, Account to, BigDecimal amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public void process() {
        if (from.getNumber() > to.getNumber()) {
            synchronized (from) {
                synchronized (to) {
                    transfer();
                }
            }
        } else {
            synchronized (to) {
                synchronized (from) {
                    transfer();
                }
            }
        }
    }

    private void transfer() {
        if (!from.withdraw(amount)) {
            throw new InsufficientFundsException(from.getNumber(), amount);
        }
        to.deposit(amount);
    }
}
