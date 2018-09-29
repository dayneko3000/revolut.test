package ru.revolut.endpoint;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.revolut.data.AccountCache;
import ru.revolut.dto.Account;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;

public class TransferEndpointTest {

    private static final long FIRST_ACCOUNT_ID = 123L;
    private static final long SECOND_ACCOUNT_ID = 124L;
    private AccountCache accountCache;

    @BeforeMethod
    public void setUp() {
        accountCache = new AccountCache();

        accountCache.addAccount(new Account(FIRST_ACCOUNT_ID, BigDecimal.TEN));
        accountCache.addAccount(new Account(SECOND_ACCOUNT_ID, BigDecimal.TEN));
    }

    @Test
    public void testTransfer() throws Exception {
        new TransferEndpoint(accountCache).transfer(FIRST_ACCOUNT_ID, SECOND_ACCOUNT_ID, BigDecimal.ONE);

        assertEquals(accountCache.getAccount(FIRST_ACCOUNT_ID).getAmount(), BigDecimal.valueOf(9));
        assertEquals(accountCache.getAccount(SECOND_ACCOUNT_ID).getAmount(), BigDecimal.valueOf(11));
    }
}