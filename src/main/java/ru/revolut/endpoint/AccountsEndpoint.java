package ru.revolut.endpoint;

import com.google.inject.Inject;
import ru.revolut.data.AccountCache;
import ru.revolut.dto.Account;
import ru.revolut.exception.AccountNotFoundException;
import ru.revolut.exception.OverrideExistingAccountException;

import javax.inject.Named;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Path("/")
public class AccountsEndpoint {

    private final AccountCache accountCache;

    @Inject
    public AccountsEndpoint(@Named("accountCache") AccountCache accountCache) {
        this.accountCache = accountCache;
    }

    @POST
    @Path("createAccount/{number}")
    public Response createAccount(@PathParam("number") long number, BigDecimal amount) {
        try {
            accountCache.addAccount(new Account(number, amount));
        } catch (OverrideExistingAccountException e) {
            return Response.ok(e.getMessage()).build();
        }
        return Response.ok("success").build();
    }

    @POST
    @Path("balance")
    public Response getAccountBalance(long accountNumber) {
        try {
            return Response.ok(accountCache.getAccount(accountNumber).getAmount()).build();
        } catch (AccountNotFoundException e) {
            return Response.ok(e.getMessage()).build();
        }
    }
}
