package org.robferguson.resteasy.examples.fatjar;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.robferguson.resteasy.examples.fatjar.resource.MessageResource;

import io.swagger.jaxrs.config.BeanConfig;

public class FatJarApplication extends Application {

    HashSet<Object> singletons = new HashSet<Object>();

    public FatJarApplication() {
        configureSwagger();
    }

    @Override
    public Set<Class<?>> getClasses() {

        HashSet<Class<?>> set = new HashSet<Class<?>>();

        set.add(MessageResource.class);

        set.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        set.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);

        return set;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }

    private void configureSwagger() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[] { "http" });
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/api");
        beanConfig.setResourcePackage(MessageResource.class.getPackage().getName());
        beanConfig.setTitle("RESTEasy, Embedded Jetty, Fat JAR, Swagger and Swagger UI Example");
        beanConfig.setDescription("Sample RESTful API built using RESTEasy, Swagger and Swagger UI "
                + "packaged in a Fat JAR with an embedded Jetty instance and no web.xml.");
        beanConfig.setScan(true);
    }
}
