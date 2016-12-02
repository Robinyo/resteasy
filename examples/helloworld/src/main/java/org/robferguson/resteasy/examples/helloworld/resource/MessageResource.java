package org.robferguson.resteasy.examples.helloworld.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hello")
@Produces(MediaType.TEXT_PLAIN)
public class MessageResource {
	
	@GET
	@Path("/{param}")
	public Response printMessage(@PathParam("param") String msg) {

		String result = "Hello " + msg + "!";

		return Response.status(200).entity(result).build();
	}
}
