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
		servletHolder.setInitParameter("javax.ws.rs.Application",
				                       "org.robferguson.resteasy.examples.fatjar.FatJarApplication");

		ServletContextHandler context = new ServletContextHandler();
		context.addServlet(servletHolder, "/");

		server.setHandler(context);
		server.start();
		server.join();
    } 
}

// * Jetty Docs: <a href="https://www.eclipse.org/jetty/documentation/9.3.x/embedding-jetty.html" target="_blank">Embedding Jetty</a>
// * Jetty Project (GitHub): <a href="https://github.com/jetty-project/embedded-jetty-uber-jar" target="_blank">Example of an Uber JAR (Fat JAR) to start a server using Embedded Jetty</a>
// * Jetty Project (GitHub): <a href="https://github.com/jetty-project/embedded-jetty-cookbook" target="_blank">Short examples of various features of Embedded Jetty</a>