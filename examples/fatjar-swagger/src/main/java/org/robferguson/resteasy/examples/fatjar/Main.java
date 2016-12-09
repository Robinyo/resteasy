package org.robferguson.resteasy.examples.fatjar;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Slf4jLog;

import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

public class Main {

	public Main() {}
	
    public static void main( String[] args ) throws Exception
    {
        try
        {
        	Log.setLog(new Slf4jLog());

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
    	
    	// setup Application context
    	ServletContextHandler context = new ServletContextHandler();
    	context.setContextPath("/");

    	// setup JAX-RS (RESTEasy) resources
    	ServletHolder apiServlet = new ServletHolder(new HttpServletDispatcher());
    	apiServlet .setInitOrder(1);
    	apiServlet .setInitParameter("javax.ws.rs.Application",
    	   "org.robferguson.resteasy.examples.fatjar.FatJarApplication");
    	
    	// context.addServlet(apiServlet , "/api/*");
    	context.addServlet(apiServlet , "/*");
    	

        final Server server = new Server(port);
        server.setHandler(context);
		server.start();
		server.join();
    }   
}

