package io.qbuddy.api.server;

public interface EmbeddedServer {

    EmbeddedServer setPort(int port);

    void run() throws Exception;
}

// https://github.com/puniverse/CascadingFailureExample/blob/master/src/main/java/co/paralleluniverse/examples/cascading/EmbeddedServer.java