package ru.revolut.dto;

import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.*;

public class AccountTest {

    @Test
    public void testWithdrawSuccess() {
        Account account = new Account(123L, BigDecimal.valueOf(150));

        assertEquals(account.getAmount(), BigDecimal.valueOf(150));

        boolean withdrawResult = account.withdraw(BigDecimal.valueOf(100));

        assertTrue(withdrawResult);
        assertEquals(account.getAmount(), BigDecimal.valueOf(50));
    }

    @Test
    public void testWithdrawFail() {
        Account account = new Account(123L, BigDecimal.valueOf(150));

        assertEquals(account.getAmount(), BigDecimal.valueOf(150));

        boolean withdrawResult = account.withdraw(BigDecimal.valueOf(200));

        assertFalse(withdrawResult);
        assertEquals(account.getAmount(), BigDecimal.valueOf(150));
    }

    @Test
    public void testDeposit() {
        Account account = new Account(123L, BigDecimal.valueOf(15));

        assertEquals(account.getAmount(), BigDecimal.valueOf(15));

        account.deposit(BigDecimal.valueOf(200));

        assertEquals(account.getAmount(), BigDecimal.valueOf(215));
    }

}