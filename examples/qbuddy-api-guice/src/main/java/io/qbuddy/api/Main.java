package io.qbuddy.api;

import java.util.EventListener;

import javax.annotation.concurrent.ThreadSafe;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Slf4jLog;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;

import io.qbuddy.api.guice.EventListenerScanner;
import io.qbuddy.api.guice.HandlerScanner;
import io.qbuddy.api.guice.JettyModule;
import io.qbuddy.api.guice.RestEasyModule;
import io.qbuddy.api.guice.Scanner.Visitor;
import io.qbuddy.api.resource.ResourceModule;
import io.qbuddy.api.swagger.SwaggerModule;

@ThreadSafe
public class Main {

    static final String APPLICATION_PATH = "/api";
    static final String API_PATH_SPEC = "/api/*";
    // static final String SWAGGER_UI_PATH_SPEC = "/*";
    static final String SWAGGER_UI_PATH_SPEC = "/";

    private final GuiceFilter filter;
    private final EventListenerScanner eventListenerScanner;
    private final HandlerScanner handlerScanner;

    @Inject
    public Main(GuiceFilter filter, EventListenerScanner eventListenerScanner, HandlerScanner handlerScanner) {
        this.filter = filter;
        this.eventListenerScanner = eventListenerScanner;
        this.handlerScanner = handlerScanner;
    }

    public static void main(String[] args) throws Exception {

        try {
            Log.setLog(new Slf4jLog());

            final Injector injector = Guice.createInjector(new RestEasyModule(APPLICATION_PATH), new ResourceModule(),
                    new JettyModule(), new SwaggerModule());

            injector.getInstance(Main.class).run();

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void run() throws Exception {

        final int port = 8080;
        final Server server = new Server(port);

        // setup the Application context
        final ServletContextHandler context = new ServletContextHandler(server, SWAGGER_UI_PATH_SPEC);

        // add the GuiceFilter (all requests will be routed through the GuiceFilter)
        FilterHolder filterHolder = new FilterHolder(filter);
        context.addFilter(filterHolder, API_PATH_SPEC, null);

        // setup the DefaultServlet for embedded Jetty (and to serve our static resources)
        final ServletHolder defaultServlet = new ServletHolder(new DefaultServlet());
        context.addServlet(defaultServlet, SWAGGER_UI_PATH_SPEC);

        // set the path to our static (Swagger UI) resources
        String resourceBasePath = Main.class.getResource("/swagger-ui").toExternalForm();
        context.setResourceBase(resourceBasePath);
        context.setWelcomeFiles(new String[] { "index.html" });

        // This will add any registered ServletContextListeners or other misc servlet listeners
        // that have been bound. For example, the GuiceResteasyBootstrapServletContextListener
        // which gets bound by the RestEasyModule.
        eventListenerScanner.accept(new Visitor<EventListener>() {

            @Override
            public void visit(EventListener listener) {
                context.addEventListener(listener);
            }
        });

        final HandlerCollection handlers = new HandlerCollection();

        // the Application context is currently the server handler, add it to the list
        handlers.addHandler(server.getHandler());

        // This will add any registered Jetty Handlers that have been bound
        handlerScanner.accept(new Visitor<Handler>() {

            @Override
            public void visit(Handler handler) {
                handlers.addHandler(handler);
            }
        });

        server.setHandler(handlers);
        server.start();
        server.join();
    }
}

// https://github.com/gwizard/gwizard/tree/master/gwizard-web/src/main/java/org/gwizard/web
// https://github.com/gwizard/gwizard/blob/master/gwizard-web/src/main/java/org/gwizard/web/WebServer.java