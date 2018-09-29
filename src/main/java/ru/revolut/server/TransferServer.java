package ru.revolut.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import ru.revolut.context.ServerContext;

public class TransferServer {

    private Server jettyInstance;

    public TransferServer(int port, ServerContext context) {
        ServletContextHandler handler = new ServletContextHandler();
        handler.setContextPath("");
        handler.addServlet(new ServletHolder(new ServletContainer(resourceConfig(context))), "/*");

        jettyInstance = new Server(port);
        jettyInstance.setHandler(handler);
    }

    private ResourceConfig resourceConfig(ServerContext context) {
        ResourceConfig resourceConfig = new ResourceConfig();
        context.components().forEach(resourceConfig::register);

        return resourceConfig;
    }

    public void start() throws Exception {
        jettyInstance.start();
    }

    public void join() throws InterruptedException {
        jettyInstance.join();
    }

    public void stop() throws Exception {
        jettyInstance.stop();
        jettyInstance.destroy();
    }
}
