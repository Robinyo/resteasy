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
    	ServletHolder servletHolder = new ServletHolder(new HttpServletDispatcher());
		servletHolder.setInitParameter("javax.ws.rs.core.Application",
				                       "org.robferguson.resteasy.examples.fatjar.FatJarApplication");

		ServletContextHandler servletCtxHandler = new ServletContextHandler();
		servletCtxHandler.addServlet(servletHolder, "/");

		Server server = new Server(8080);
		server.setHandler(servletCtxHandler);
		server.start();
		server.join();
    } 
}
