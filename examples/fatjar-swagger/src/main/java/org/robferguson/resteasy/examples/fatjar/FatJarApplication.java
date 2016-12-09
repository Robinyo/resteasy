package org.robferguson.resteasy.examples.fatjar;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.robferguson.resteasy.examples.fatjar.resource.MessageResource;

// import io.swagger.jaxrs.config.BeanConfig;
// import io.swagger.jaxrs.listing.ApiListingResource;
// import io.swagger.jaxrs.listing.SwaggerSerializers;

@ApplicationPath("/")
public class FatJarApplication extends Application {
	
	public FatJarApplication() {
		
		/*
		
		BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.2");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/");
        beanConfig.setResourcePackage("io.swagger.resources");
        beanConfig.setScan(true);
        
        */
	}
    
	
	@Override
	public Set<Object> getSingletons() {
		
		HashSet<Object> set = new HashSet<Object>();
		
		set.add(new MessageResource());
		
		// set.add(new ApiListingResource());
		// set.add(new SwaggerSerializers());
		
        return set;
	}
}
