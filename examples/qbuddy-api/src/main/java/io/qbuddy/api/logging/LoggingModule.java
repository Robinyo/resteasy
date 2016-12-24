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