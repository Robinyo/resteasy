package io.qbuddy.api;

import javax.annotation.concurrent.ThreadSafe;

import org.eclipse.jetty.servlet.DefaultServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;

import io.qbuddy.api.jetty.JettyModule;
import io.qbuddy.api.logging.LoggingModule;
import io.qbuddy.api.resource.ResourceModule;
import io.qbuddy.api.resteasy.RestEasyModule;
import io.qbuddy.api.server.JettyServer;
import io.qbuddy.api.swagger.SwaggerModule;

@ThreadSafe
public class Main {

    static final String DEFAULT_PORT = "8080";
    static final String APPLICATION_PATH = "/api";
    static final String CONTEXT_ROOT = "/";

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {

        try {

            final Injector injector = createInjector();
            final JettyServer server = injector.getInstance(JettyServer.class);

            log.info("main()");

            String port = System.getenv("PORT");

            if (port == null || port.isEmpty()) {
                port = DEFAULT_PORT;
            }

            log.info("Port: " + port);

            server.setPort(Integer.valueOf(port));

            // Add the GuiceFilter (all requests will be routed through GuiceFilter).
            server.addFilter(new GuiceFilter(), APPLICATION_PATH + "/*");

            // Setup the DefaultServlet at "/".
            server.addServlet(new DefaultServlet(), CONTEXT_ROOT);

            server.run();

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static Injector createInjector() {

        return Guice.createInjector(new LoggingModule(), new JettyModule(), new RestEasyModule(APPLICATION_PATH),
                new ResourceModule(), new SwaggerModule(APPLICATION_PATH));
    }
}