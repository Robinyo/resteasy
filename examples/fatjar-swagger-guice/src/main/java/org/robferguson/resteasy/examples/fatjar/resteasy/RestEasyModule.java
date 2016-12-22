package org.robferguson.resteasy.examples.fatjar.resteasy;

import java.util.Map;

import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;

public class RestEasyModule extends ServletModule {

    private final String path;

    public RestEasyModule() {
        this.path = null;
    }

    public RestEasyModule(final String path) {
        Preconditions.checkArgument(path.length() == 0 || path.startsWith("/"), "Path must begin with '/'");
        Preconditions.checkArgument(!path.endsWith("/"), "Path must not have a trailing '/'");
        this.path = path;  // e.g., "/api"
    }

    @Override
    protected void configureServlets() {

        // We must do dependency injection before the first request is processed.
        // That's why JEE defines the ServletContextListener interface.
        // The RESTEasy framework includes a ServletContextListener implementation
        // (i.e., GuiceResteasyBootstrapServletContextListener).
        bind(GuiceResteasyBootstrapServletContextListener.class);

        bind(HttpServletDispatcher.class).in(Singleton.class);

        if (path == null) {
            serve("/*").with(HttpServletDispatcher.class);
        } else {
            final Map<String, String> initParams = ImmutableMap.of("resteasy.servlet.mapping.prefix", path);
            serve(path + "/*").with(HttpServletDispatcher.class, initParams);
        }
    }
}

// https://github.com/gwizard/gwizard/blob/master/gwizard-rest/src/main/java/org/gwizard/rest/RestModule.java