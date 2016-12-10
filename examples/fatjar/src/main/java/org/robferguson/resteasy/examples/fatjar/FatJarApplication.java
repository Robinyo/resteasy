package org.robferguson.resteasy.examples.fatjar;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.robferguson.resteasy.examples.fatjar.resource.MessageResource;

public class FatJarApplication extends Application {

    public FatJarApplication() {
    }

    /*
     * 
     * public Set<Class<?>> getClasses() { HashSet<Class<?>> set = new
     * HashSet<Class<?>>(); set.add(MessageResource.class); return set; }
     * 
     */

    @Override
    public Set<Object> getSingletons() {
        HashSet<Object> set = new HashSet<Object>();
        set.add(new MessageResource());
        return set;
    }
}
