package ru.revolut.data;

import ru.revolut.dto.Account;
import ru.revolut.exception.AccountNotFoundException;
import ru.revolut.exception.OverrideExistingAccountException;

import java.util.HashMap;
import java.util.Map;

public class AccountCache {
    private final Map<Long, Account> accountMap = new HashMap<>();

    public Account getAccount(long number) {
        Account result = accountMap.get(number);

        if (result == null) {
            throw new AccountNotFoundException(number);
        }

        return result;
    }

    public void addAccount(Account account) {
        long number = account.getNumber();
        if (accountMap.containsKey(number)) {
            throw new OverrideExistingAccountException(number);
        }

        accountMap.put(number, account);
    }
}
