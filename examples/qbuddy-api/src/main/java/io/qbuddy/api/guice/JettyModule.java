package io.qbuddy.api.guice;

import com.google.inject.AbstractModule;
import com.google.inject.servlet.GuiceFilter;

public class JettyModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(GuiceFilter.class);
    }
}
