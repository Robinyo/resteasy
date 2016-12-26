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

    static final String APPLICATION_PATH = "/api";
    static final String CONTEXT_ROOT = "/";

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {

        try {

            final Injector injector = createInjector();
            final JettyServer server = injector.getInstance(JettyServer.class);

            log.info("main()");

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

    /*
    
    private void printLogLevel() {
    
        if (log.isTraceEnabled()) {
            log.trace("TRACE level logging is enabled.");
        }
        if (log.isDebugEnabled()) {
            log.debug("DEBUG level logging is enabled.");
        }
        if (log.isInfoEnabled()) {
            log.info("INFO level logging is enabled.");
        }
        if (log.isWarnEnabled()) {
            log.warn("WARN level logging is enabled.");
        }
        if (log.isErrorEnabled()) {
            log.error("ERROR level logging is enabled.");
        }
    }
    
    private void printLoggingStatus() {
    
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(lc);
    }
    
    */
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

// Log.setLog(new Slf4jLog());

// http://logback.qos.ch/manual/configuration.html
// http://stackoverflow.com/questions/10874188/jax-rs-application-on-the-root-context-how-can-it-be-done
// https://github.com/gwizard/gwizard/tree/master/gwizard-web/src/main/java/org/gwizard/web
// https://github.com/gwizard/gwizard/blob/master/gwizard-web/src/main/java/org/gwizard/web/WebServer.java