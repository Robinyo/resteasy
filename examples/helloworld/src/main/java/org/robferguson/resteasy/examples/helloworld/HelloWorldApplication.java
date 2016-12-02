package org.robferguson.resteasy.examples.helloworld;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.robferguson.resteasy.examples.helloworld.resource.MessageResource;

@ApplicationPath("")
public class HelloWorldApplication extends Application {

	public HelloWorldApplication() {}
	
	/*
	
	public Set<Class<?>> getClasses() {
		HashSet<Class<?>> set = new HashSet<Class<?>>();
		set.add(MessageResource.class);
		return set;
	}
	
	*/
	
	@Override
	public Set<Object> getSingletons() {
		HashSet<Object> set = new HashSet<Object>();
		set.add(new MessageResource());
        return set;
	}
}
