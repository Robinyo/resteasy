package org.robferguson.resteasy.examples.fatjar;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Slf4jLog;

import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.robferguson.resteasy.examples.fatjar.resource.MessageResource;

import io.swagger.jaxrs.config.BeanConfig;

// import io.swagger.jaxrs.config.DefaultJaxrsConfig;

public class Main {

	public Main() {}
	
    public static void main( String[] args ) throws Exception
    {
        try
        {
        	Log.setLog(new Slf4jLog());
        	
        	configureSwagger();

            new Main().run();
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
    
    public void run() throws Exception
    {
    	int port = 8080;
    	
        final Server server = new Server(port);
    	
    	// setup Application context
    	ServletContextHandler context = new ServletContextHandler();
    	context.setContextPath("/");
        server.setHandler(context);
        
    	// setup JAX-RS (RESTEasy) resources
    	ServletHolder apiServlet = new ServletHolder(new HttpServletDispatcher());
    	apiServlet .setInitOrder(1);
    	apiServlet .setInitParameter("javax.ws.rs.Application",
    	   "org.robferguson.resteasy.examples.fatjar.FatJarApplication");
    	
    	// setup Swagger servlet
    	// ServletHolder swaggerServlet = new ServletHolder(new DefaultJaxrsConfig());
    	// swaggerServlet.setInitOrder(2);
    	// swaggerServlet.setInitParameter("api.version", "1.0.0");
    	// swaggerServlet.setInitParameter("swagger.api.basepath", "/api");
    	
    	// setup Swagger-UI static resources
    	String resourceBasePath = Main.class.getResource("/swagger-ui").toExternalForm();
    	context.setResourceBase(resourceBasePath);
    	context.setWelcomeFiles(new String[] {"index.html"});
    	ServletHolder swaggerUiServlet = new ServletHolder(new DefaultServlet());

    	context.addServlet(apiServlet , "/api/*");
    	// context.addServlet(swaggerServlet, "/swagger");
    	context.addServlet(swaggerUiServlet , "/*");

		server.start();
		server.join();
    }   
    
    private static void configureSwagger()
    {
    	BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/api");
        beanConfig.setResourcePackage( MessageResource.class.getPackage().getName() );
        beanConfig.setTitle( "Fat JAR, Swagger and Swagger UI Example" );
        beanConfig.setDescription( "Hello World API to demonstrate Swagger with RESTEasy in an "
          + "embedded Jetty instance, with no web.xml." );
        beanConfig.setScan(true);
    }
}

