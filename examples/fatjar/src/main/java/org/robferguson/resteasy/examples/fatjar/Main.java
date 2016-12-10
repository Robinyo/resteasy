package org.robferguson.resteasy.examples.fatjar;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

public class Main {

    static final String APPLICATION_PATH = "/api";
    static final String API_PATH_SPEC = "/api/*";

    public Main() {
    }

    public static void main(String[] args) throws Exception {
        try {
            new Main().run();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void run() throws Exception {

        final int port = 8080;
        final Server server = new Server(port);

        // setup Application context
        ServletContextHandler context = new ServletContextHandler();

        // setup JAX-RS (RESTEasy) resources
        ServletHolder apiServlet = new ServletHolder(new HttpServletDispatcher());
        apiServlet.setInitParameter("resteasy.servlet.mapping.prefix", APPLICATION_PATH);
        apiServlet.setInitParameter("javax.ws.rs.Application",
                "org.robferguson.resteasy.examples.fatjar.FatJarApplication");

        context.addServlet(apiServlet, API_PATH_SPEC);

        server.setHandler(context);
        server.start();
        server.join();
    }
}