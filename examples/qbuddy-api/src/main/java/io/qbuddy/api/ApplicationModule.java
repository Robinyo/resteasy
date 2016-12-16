package io.qbuddy.api;

import com.google.inject.AbstractModule;

import io.qbuddy.api.resource.MessageResource;

public class ApplicationModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(MessageResource.class);
    }
}