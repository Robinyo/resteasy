package org.robferguson.resteasy.examples.fatjar.jetty;

import com.google.inject.servlet.GuiceFilter;
import com.google.inject.servlet.ServletModule;

public class JettyModule extends ServletModule {

    @Override
    protected void configureServlets() {

        bind(GuiceFilter.class);
    }
}
