package ru.revolut.context;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import ru.revolut.data.AccountCache;
import ru.revolut.endpoint.AccountsEndpoint;
import ru.revolut.endpoint.TransferEndpoint;

public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AccountCache.class).annotatedWith(Names.named("accountCache")).to(AccountCache.class).asEagerSingleton();
        bind(TransferEndpoint.class).annotatedWith(Names.named("TransferEndpoint")).to(TransferEndpoint.class);
        bind(AccountsEndpoint.class).annotatedWith(Names.named("AccountsEndpoint")).to(AccountsEndpoint.class);
    }
}