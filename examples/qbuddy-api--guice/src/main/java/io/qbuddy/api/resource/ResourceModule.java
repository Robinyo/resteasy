package io.qbuddy.api.resource;

import com.google.inject.AbstractModule;

public class ResourceModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(MessageResource.class);
    }
}