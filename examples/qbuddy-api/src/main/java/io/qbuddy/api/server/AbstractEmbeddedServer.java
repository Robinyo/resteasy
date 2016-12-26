package io.qbuddy.api.server;

public abstract class AbstractEmbeddedServer implements EmbeddedServer {

    static final int DEFAULT_PORT = 9001;

    protected int port = DEFAULT_PORT;

    @Override
    public EmbeddedServer setPort(int port) {
        this.port = port;
        return this;
    }
}

// https://github.com/puniverse/CascadingFailureExample/blob/master/src/main/java/co/paralleluniverse/examples/cascading/AbstractEmbeddedServer.java