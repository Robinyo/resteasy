package io.qbuddy.api.server;

public abstract class AbstractEmbeddedServer implements EmbeddedServer {

    protected int port;

    @Override
    public EmbeddedServer setPort(int port) {
        this.port = port;
        return this;
    }
}

// https://github.com/puniverse/CascadingFailureExample/blob/master/src/main/java/co/paralleluniverse/examples/cascading/AbstractEmbeddedServer.java