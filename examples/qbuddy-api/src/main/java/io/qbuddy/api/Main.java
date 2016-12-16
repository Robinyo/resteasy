package io.qbuddy.api;

import java.util.EnumSet;

import javax.annotation.concurrent.ThreadSafe;
import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Slf4jLog;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;

import io.qbuddy.api.guice.ApplicationModule;
import io.qbuddy.api.guice.RestEasyModule;

@ThreadSafe
public class Main {

    static final String APPLICATION_PATH = "/api";
    static final String API_PATH_SPEC = "/api/*";
    static final String SWAGGER_UI_PATH_SPEC = "/*";

    private final GuiceFilter filter;

    @Inject
    public Main(GuiceFilter filter) {
        this.filter = filter;
    }

    public static void main(String[] args) throws Exception {

        try {
            Log.setLog(new Slf4jLog());

            final Injector injector = Guice.createInjector(new RestEasyModule(), new ApplicationModule());

            injector.getInstance(Main.class).run();

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void run() throws Exception {

        final int port = 8080;
        final Server server = new Server(port);

        // setup the Application context
        final ServletContextHandler context = new ServletContextHandler();

        // add the GuiceFilter
        FilterHolder filterHolder = new FilterHolder(filter);
        context.addFilter(filterHolder, API_PATH_SPEC, EnumSet.allOf(DispatcherType.class));

        // setup the JAX-RS (RESTEasy) resources
        final ServletHolder apiServlet = new ServletHolder(new HttpServletDispatcher());
        apiServlet.setInitOrder(1);
        apiServlet.setInitParameter("resteasy.servlet.mapping.prefix", APPLICATION_PATH);
        apiServlet.setInitParameter("javax.ws.rs.Application", "io.qbuddy.api.QBuddyApplication");
        context.addServlet(apiServlet, API_PATH_SPEC);

        // setup the static (Swagger UI) resources
        String resourceBasePath = Main.class.getResource("/swagger-ui").toExternalForm();
        context.setResourceBase(resourceBasePath);
        context.setWelcomeFiles(new String[] { "index.html" });

        // the Default Servlet will serve our static resources
        final ServletHolder swaggerUiServlet = new ServletHolder(new DefaultServlet());
        swaggerUiServlet.setInitOrder(2);
        context.addServlet(swaggerUiServlet, SWAGGER_UI_PATH_SPEC);

        server.setHandler(context);
        server.start();
        server.join();
    }
}