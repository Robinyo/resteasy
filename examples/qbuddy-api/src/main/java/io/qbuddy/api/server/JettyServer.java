package io.qbuddy.api.server;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.Servlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.qbuddy.api.guice.EventListenerScanner;
import io.qbuddy.api.guice.HandlerScanner;

public class JettyServer extends AbstractEmbeddedServer {

    static final String DEFAULT_PORT = "8080";
    static final String CONTEXT_ROOT = "/";

    public static final int MIN_THREADS = 10;
    public static final int MAX_THREADS = 200;
    public static final int IDLE_TIMEOUT = 30000;
    public static final int ACCEPT_QUEUE_SIZE = 60000;

    private String port;

    private QueuedThreadPool threadPool;
    private int minThreads = MIN_THREADS;
    private int maxThreads = MAX_THREADS;
    private int idleTimeout = IDLE_TIMEOUT;
    private int acceptQueueSize = IDLE_TIMEOUT;

    private Server server;
    private ServerConnector http;
    private ServletContextHandler context;

    private EventListenerScanner eventListenerScanner;
    private HandlerScanner handlerScanner;
    private HandlerCollection handlers;

    private static final Logger log = LoggerFactory.getLogger(JettyServer.class);

    // Guice can work with both javax and guice annotations.
    @Inject
    JettyServer(EventListenerScanner eventListenerScanner, HandlerScanner handlerScanner, HandlerCollection handlers) {

        this.eventListenerScanner = eventListenerScanner;
        this.handlerScanner = handlerScanner;
        this.handlers = handlers;

        String port = System.getenv("PORT");

        if (port == null || port.isEmpty()) {
            port = DEFAULT_PORT;
            log.info("PORT=" + DEFAULT_PORT);
        }

        log.info("Port: " + port);
    }

    private void build() {

        if (server != null)
            return;

        this.threadPool = new QueuedThreadPool(maxThreads, minThreads, idleTimeout);
        this.server = new Server(threadPool);
        this.http = new ServerConnector(server);
        // http.setPort(Integer.valueOf(port));
        http.setPort(Integer.valueOf(8080));
        http.setAcceptQueueSize(acceptQueueSize);
        server.addConnector(http);

        // Setup the basic Application "context" at "/".
        // This is also known as the handler tree (in Jetty speak).
        this.context = new ServletContextHandler(server, CONTEXT_ROOT);
        // this.context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        // Add any Listeners that have been bound, for example, the
        // GuiceResteasyBootstrapServletContextListener which gets bound in the RestEasyModule.
        eventListenerScanner.accept((listener) -> {
            context.addEventListener(listener);
        });

        // The Application context is currently the server handler, add it to the list.
        handlers.addHandler(server.getHandler());

        // Add any Handlers that have been bound
        handlerScanner.accept((handler) -> {
            handlers.addHandler(handler);
        });

        // Set the path to our static (Swagger UI) resources
        String resourceBasePath = JettyServer.class.getResource("/swagger-ui").toExternalForm();
        context.setResourceBase(resourceBasePath);
        context.setWelcomeFiles(new String[] { "index.html" });
    }

    public void run() throws Exception {

        server.setHandler(handlers);
        server.start();
        server.join();
    }

    public void addFilter(Filter filter, String pathSpec) {

        build();

        final FilterHolder filterHolder = new FilterHolder(filter);
        context.addFilter(filterHolder, pathSpec, null);
    }

    public void addServlet(Servlet servlet, String pathSpec) {

        build();

        final ServletHolder servletHolder = new ServletHolder(servlet);
        context.addServlet(servletHolder, pathSpec);
    }
}

// https://github.com/puniverse/CascadingFailureExample/blob/master/src/main/java/co/paralleluniverse/examples/cascading/JettyServer.java