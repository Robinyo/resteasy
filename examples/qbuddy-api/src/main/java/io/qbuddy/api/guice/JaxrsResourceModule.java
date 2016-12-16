package io.qbuddy.api.guice;

import com.google.inject.AbstractModule;

import io.qbuddy.api.resource.MessageResource;

public class JaxrsResourceModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(MessageResource.class);
    }
}