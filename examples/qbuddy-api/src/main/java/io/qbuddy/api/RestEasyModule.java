package io.qbuddy.api;

import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;

import com.google.inject.AbstractModule;

public class RestEasyModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(GuiceResteasyBootstrapServletContextListener.class);
    }
}