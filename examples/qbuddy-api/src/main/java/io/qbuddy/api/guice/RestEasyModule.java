package io.qbuddy.api.guice;

import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;

import com.google.inject.AbstractModule;

public class RestEasyModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(GuiceResteasyBootstrapServletContextListener.class);

        // bind(ObjectMapperContextResolver.class);

        // bind(HttpServletDispatcher.class).in(Singleton.class);
    }
}
