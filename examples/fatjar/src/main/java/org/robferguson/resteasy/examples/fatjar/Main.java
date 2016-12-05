package org.robferguson.resteasy.examples.fatjar;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;

public class Main {
	
	public Main() {}
	
    public static void main( String[] args ) throws Exception
    {
        try
        {
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
        Server server = new Server(port);
        
    	ServletHolder servletHolder = new ServletHolder(new HttpServletDispatcher());
		servletHolder.setInitParameter("javax.ws.rs.core.Application",
				                       "org.robferguson.resteasy.examples.fatjar.FatJarApplication");

		ServletContextHandler context = new ServletContextHandler();
		context.addServlet(servletHolder, "/");

		server.setHandler(context);
		server.start();
		server.join();
    } 
}

// See: https://github.com/jetty-project/embedded-jetty-uber-jar/blob/master/src/main/java/jetty/uber/ServerMain.java