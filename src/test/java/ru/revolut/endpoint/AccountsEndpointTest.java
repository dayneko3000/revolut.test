package ru.revolut.endpoint;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.revolut.data.AccountCache;
import ru.revolut.dto.Account;

import javax.ws.rs.core.Response;
import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;

public class AccountsEndpointTest {
    private static final long ACCOUNT_ID = 123L;
    private static final BigDecimal ACCOUNT_AMOUNT = BigDecimal.TEN;
    private AccountCache accountCache;

    @BeforeMethod
    public void setUp() throws Exception {
        accountCache = new AccountCache();
    }

    @Test
    public void testCreateAccount() throws Exception {
        new AccountsEndpoint(accountCache).createAccount(ACCOUNT_ID, ACCOUNT_AMOUNT);

        assertEquals(accountCache.getAccount(ACCOUNT_ID).getNumber(), ACCOUNT_ID);
    }

    @Test
    public void testGetAccountBalance() throws Exception {
        accountCache.addAccount(new Account(ACCOUNT_ID, ACCOUNT_AMOUNT));

        Response accountBalance = new AccountsEndpoint(accountCache).getAccountBalance(ACCOUNT_ID);

        assertEquals(BigDecimal.valueOf(Long.parseLong(String.valueOf(accountBalance.getEntity()))),
                ACCOUNT_AMOUNT);
    }
}