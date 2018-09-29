package ru.revolut.endpoint;

import com.google.inject.Inject;
import ru.revolut.data.AccountCache;
import ru.revolut.dto.Account;
import ru.revolut.process.Transfer;

import javax.inject.Named;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Path("/")
public class TransferEndpoint {

    private final AccountCache accountCache;

    @Inject
    public TransferEndpoint(@Named("accountCache") AccountCache accountCache) {
        this.accountCache = accountCache;
    }

    @POST
    @Path("transfer/{from}/{to}")
    public Response transfer(@PathParam("from") long from, @PathParam("to") long to, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            Response.ok("Null transfer is prohibited").build();
        }

        try {
            final Account accountFrom = accountCache.getAccount(from);
            final Account accountTo = accountCache.getAccount(to);

            new Transfer(accountFrom, accountTo, amount).process();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
        return Response.ok("success").build();
    }
}
