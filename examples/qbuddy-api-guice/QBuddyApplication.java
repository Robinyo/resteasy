package io.qbuddy.api;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import io.qbuddy.api.resource.MessageResource;
import io.swagger.jaxrs.config.BeanConfig;

public class QBuddyApplication extends Application {

    HashSet<Object> singletons = new HashSet<Object>();
    HashSet<Class<?>> classes = new HashSet<Class<?>>();

    public QBuddyApplication() {
        configureSwagger();
    }

    @Override
    public Set<Class<?>> getClasses() {

        // classes.add(MessageResource.class);

        // classes.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        // classes.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);

        return classes;
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

        beanConfig.setTitle("QBuddy RESTful API");
        beanConfig.setDescription("QBuddy RESTful API built using RESTEasy, Swagger and Swagger UI "
                + "packaged in a Fat JAR with an embedded Jetty instance and no web.xml.");

        // setScan() must be called last
        beanConfig.setResourcePackage(MessageResource.class.getPackage().getName());
        beanConfig.setScan(true);
    }
}
