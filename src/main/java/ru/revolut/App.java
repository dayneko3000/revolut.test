package ru.revolut;

import ru.revolut.context.GuiceServerContext;
import ru.revolut.server.TransferServer;

public class App {

    public static void main(String[] args) throws Exception {
        TransferServer transferServer = new TransferServer(8888, new GuiceServerContext());

        try {
            transferServer.start();
            transferServer.join();
        } finally {
            transferServer.stop();
        }
    }

}
