package io.qbuddy.api.swagger;

import javax.servlet.ServletContextListener;

import com.google.inject.multibindings.Multibinder;
import com.google.inject.servlet.ServletModule;

public class SwaggerModule extends ServletModule {

    static final String API_PATH_SPEC = "/api/*";

    @Override
    protected void configureServlets() {

        Multibinder.newSetBinder(binder(), ServletContextListener.class).addBinding()
                .to(SwaggerServletContextListener.class);

        bind(io.swagger.jaxrs.listing.ApiListingResource.class);
        bind(io.swagger.jaxrs.listing.SwaggerSerializers.class);

        filter(API_PATH_SPEC).through(ApiOriginFilter.class);
    }
}

// https://github.com/swagger-api/swagger-core/issues/997
// https://gist.github.com/ben-manes/af6ab5e857f29f55d990
// https://github.com/gwizard/gwizard/blob/master/gwizard-swagger/src/main/java/org/gwizard/swagger/SwaggerModule.java