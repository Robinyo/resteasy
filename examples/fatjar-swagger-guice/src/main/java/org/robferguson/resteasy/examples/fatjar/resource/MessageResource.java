package org.robferguson.resteasy.examples.fatjar.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/hello")
@Produces(MediaType.TEXT_PLAIN)
@Api(value = "hello", description = "Endpoint for Hello specific operations")
public class MessageResource {

    @GET
    @Path("/{param}")
    @ApiOperation(value = "Returns param", notes = "Returns param", response = MessageResource.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of param value", response = MessageResource.class) })
    public Response printMessage(@PathParam("param") String msg) {
        String result = "Hello " + msg + "!";
        return Response.status(200).entity(result).build();
    }
}