package io.qbuddy.api.swagger;

import javax.servlet.ServletContextListener;

import com.google.common.base.Preconditions;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.servlet.ServletModule;

public class SwaggerModule extends ServletModule {

    private final String path;

    public SwaggerModule() {
        this.path = null;
    }

    public SwaggerModule(final String path) {
        Preconditions.checkArgument(path.length() == 0 || path.startsWith("/"), "Path must begin with '/'");
        Preconditions.checkArgument(!path.endsWith("/"), "Path must not have a trailing '/'");
        this.path = path;
    }

    @Override
    protected void configureServlets() {

        Multibinder.newSetBinder(binder(), ServletContextListener.class).addBinding()
                .to(SwaggerServletContextListener.class);

        bind(io.swagger.jaxrs.listing.ApiListingResource.class);
        bind(io.swagger.jaxrs.listing.SwaggerSerializers.class);

        if (path == null) {
            filter("/*").through(ApiOriginFilter.class);
        } else {
            filter(path + "/*").through(ApiOriginFilter.class);
        }
    }
}

// https://github.com/swagger-api/swagger-core/issues/997
// https://gist.github.com/ben-manes/af6ab5e857f29f55d990
// https://github.com/gwizard/gwizard/blob/master/gwizard-swagger/src/main/java/org/gwizard/swagger/SwaggerModule.java