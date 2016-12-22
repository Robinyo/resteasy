package org.robferguson.resteasy.examples.fatjar.swagger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.robferguson.resteasy.examples.fatjar.resource.MessageResource;

import io.swagger.config.ScannerFactory;
import io.swagger.jaxrs.config.BeanConfig;

final class SwaggerServletContextListener implements ServletContextListener {

    SwaggerServletContextListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {

        BeanConfig beanConfig = getBeanConfig();
        event.getServletContext().setAttribute("reader", beanConfig);
        event.getServletContext().setAttribute("swagger", beanConfig.getSwagger());
        event.getServletContext().setAttribute("scanner", ScannerFactory.getScanner());
    }

    private BeanConfig getBeanConfig() {

        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[] { "http" });
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/api");

        beanConfig.setTitle("RESTEasy, Embedded Jetty, Swagger and Guice Example");
        beanConfig.setDescription("Sample RESTful API built using RESTEasy, Swagger, Swagger UI and "
                + "Guice packaged in a Fat JAR with an embedded Jetty instance and no web.xml.");

        // setScan() must be called last
        beanConfig.setResourcePackage(MessageResource.class.getPackage().getName());
        beanConfig.setScan(true);

        return beanConfig;
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
    }
}

// https://github.com/swagger-api/swagger-core/issues/997
// https://gist.github.com/ben-manes/af6ab5e857f29f55d990
// https://github.com/gwizard/gwizard/blob/master/gwizard-swagger/src/main/java/org/gwizard/swagger/SwaggerServletContextListener.java