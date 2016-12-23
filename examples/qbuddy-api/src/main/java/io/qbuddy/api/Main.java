package io.qbuddy.api;

import javax.annotation.concurrent.ThreadSafe;
import javax.inject.Inject;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Slf4jLog;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;

import io.qbuddy.api.guice.EventListenerScanner;
import io.qbuddy.api.guice.HandlerScanner;
import io.qbuddy.api.jetty.JettyModule;
import io.qbuddy.api.resource.ResourceModule;
import io.qbuddy.api.resteasy.RestEasyModule;
import io.qbuddy.api.swagger.SwaggerModule;

@ThreadSafe
public class Main {

    static final String APPLICATION_PATH = "/api";
    static final String CONTEXT_ROOT = "/";

    private final GuiceFilter filter;
    private final EventListenerScanner eventListenerScanner;
    private final HandlerScanner handlerScanner;

    // Guice can work with both javax and guice annotations.
    @Inject
    public Main(GuiceFilter filter, EventListenerScanner eventListenerScanner, HandlerScanner handlerScanner) {
        this.filter = filter;
        this.eventListenerScanner = eventListenerScanner;
        this.handlerScanner = handlerScanner;
    }

    public static void main(String[] args) throws Exception {

        try {
            Log.setLog(new Slf4jLog());

            final Injector injector = Guice.createInjector(new JettyModule(), new RestEasyModule(APPLICATION_PATH),
                    new ResourceModule(), new SwaggerModule(APPLICATION_PATH));

            injector.getInstance(Main.class).run();

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void run() throws Exception {

        final int port = 8080;
        final Server server = new Server(port);

        // Setup the basic Application "context" at "/".
        // This is also known as the handler tree (in Jetty speak).
        final ServletContextHandler context = new ServletContextHandler(server, CONTEXT_ROOT);

        // Add the GuiceFilter (all requests will be routed through GuiceFilter).
        FilterHolder filterHolder = new FilterHolder(filter);
        context.addFilter(filterHolder, APPLICATION_PATH + "/*", null);

        // Setup the DefaultServlet at "/".
        final ServletHolder defaultServlet = new ServletHolder(new DefaultServlet());
        context.addServlet(defaultServlet, CONTEXT_ROOT);

        // Set the path to our static (Swagger UI) resources
        String resourceBasePath = Main.class.getResource("/swagger-ui").toExternalForm();
        context.setResourceBase(resourceBasePath);
        context.setWelcomeFiles(new String[] { "index.html" });

        // Add any Listeners that have been bound, for example, the
        // GuiceResteasyBootstrapServletContextListener which gets bound in the RestEasyModule.
        eventListenerScanner.accept((listener) -> {
            context.addEventListener(listener);
        });

        final HandlerCollection handlers = new HandlerCollection();

        // The Application context is currently the server handler, add it to the list.
        handlers.addHandler(server.getHandler());

        // Add any Handlers that have been bound
        handlerScanner.accept((handler) -> {
            handlers.addHandler(handler);
        });

        server.setHandler(handlers);
        server.start();
        server.join();
    }
}

/*
eventListenerScanner.accept(new Visitor<EventListener>() {

    @Override
    public void visit(EventListener listener) {
        context.addEventListener(listener);
    }
});
*/

/*
handlerScanner.accept(new Visitor<Handler>() {

    @Override
    public void visit(Handler handler) {
        handlers.addHandler(handler);
    }
});
*/

// http://stackoverflow.com/questions/10874188/jax-rs-application-on-the-root-context-how-can-it-be-done
// https://github.com/gwizard/gwizard/tree/master/gwizard-web/src/main/java/org/gwizard/web
// https://github.com/gwizard/gwizard/blob/master/gwizard-web/src/main/java/org/gwizard/web/WebServer.java