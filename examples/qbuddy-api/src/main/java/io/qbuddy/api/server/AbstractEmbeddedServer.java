package io.qbuddy.api.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.qbuddy.api.Main;

public abstract class AbstractEmbeddedServer implements EmbeddedServer {

    protected int port;

    protected static final Logger log = LoggerFactory.getLogger(Main.class);

    @Override
    public EmbeddedServer setPort(int port) {
        this.port = port;
        return this;
    }
}