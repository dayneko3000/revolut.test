package ru.revolut.data;

import org.testng.annotations.Test;
import ru.revolut.dto.Account;
import ru.revolut.exception.AccountNotFoundException;
import ru.revolut.exception.OverrideExistingAccountException;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;

public class AccountCacheTest {

    @Test(expectedExceptions = AccountNotFoundException.class)
    public void testGetNonexistentAccount() {
        AccountCache accountCache = new AccountCache();

        Account account = new Account(124L, BigDecimal.TEN);
        accountCache.addAccount(account);

        assertEquals(account, accountCache.getAccount(123L));
    }

    @Test
    public void testGetExistsAccount() {
        AccountCache accountCache = new AccountCache();

        long accountNumber = 123L;
        Account account = new Account(accountNumber, BigDecimal.TEN);
        accountCache.addAccount(account);

        assertEquals(account, accountCache.getAccount(accountNumber));
    }

    @Test(expectedExceptions = OverrideExistingAccountException.class)
    public void testAddAccountSecondTime() {
        AccountCache accountCache = new AccountCache();

        Account account = new Account(123L, BigDecimal.TEN);
        accountCache.addAccount(account);
        accountCache.addAccount(account);
    }

}