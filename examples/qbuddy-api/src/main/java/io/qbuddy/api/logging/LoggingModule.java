package io.qbuddy.api.logging;

import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.google.inject.AbstractModule;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.jul.LevelChangePropagator;

public class LoggingModule extends AbstractModule {

    public LoggingModule() {
        hijackJDKLogging();
    }

    @Override
    protected void configure() {
    }

    private void hijackJDKLogging() {

        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        final Logger root = (Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);

        final LevelChangePropagator propagator = new LevelChangePropagator();
        propagator.setContext(root.getLoggerContext());
        propagator.setResetJUL(true);

        root.getLoggerContext().addListener(propagator);
    }
}

/*

Log.setLog(new Slf4jLog());

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