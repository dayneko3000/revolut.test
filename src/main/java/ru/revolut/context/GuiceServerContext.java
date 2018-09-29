package ru.revolut.context;

import com.google.inject.Guice;
import com.google.inject.Injector;
import ru.revolut.endpoint.AccountsEndpoint;
import ru.revolut.endpoint.TransferEndpoint;

import java.util.Arrays;
import java.util.List;

public class GuiceServerContext implements ServerContext {
    private final Injector injector;

    public GuiceServerContext() {
        this.injector = Guice.createInjector(new BasicModule());
    }

    public List<Object> components() {
        return Arrays.asList(injector.getInstance(TransferEndpoint.class), injector.getInstance(AccountsEndpoint.class));
    }
}
