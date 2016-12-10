package org.robferguson.resteasy.examples.fatjar;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Slf4jLog;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

public class Main {

    static final String APPLICATION_PATH = "/api";
    static final String API_PATH_SPEC = "/api/*";
    static final String SWAGGER_UI_PATH_SPEC = "/*";

    public Main() {
    }

    public static void main(String[] args) throws Exception {
        try {
            Log.setLog(new Slf4jLog());

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
        apiServlet.setInitOrder(1);
        apiServlet.setInitParameter("resteasy.servlet.mapping.prefix", APPLICATION_PATH);
        apiServlet.setInitParameter("javax.ws.rs.Application",
                "org.robferguson.resteasy.examples.fatjar.FatJarApplication");

        // setup static (Swagger UI) resources
        String resourceBasePath = Main.class.getResource("/swagger-ui").toExternalForm();
        context.setResourceBase(resourceBasePath);
        context.setWelcomeFiles(new String[] { "index.html" });

        ServletHolder swaggerUiServlet = new ServletHolder(new DefaultServlet());
        swaggerUiServlet.setInitOrder(2);

        context.addServlet(apiServlet, API_PATH_SPEC);
        context.addServlet(swaggerUiServlet, SWAGGER_UI_PATH_SPEC);

        server.setHandler(context);
        server.start();
        server.join();
    }
}