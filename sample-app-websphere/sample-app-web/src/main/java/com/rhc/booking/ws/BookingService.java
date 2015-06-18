package com.rhc.booking.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/BookingService")
public class BookingService
{
    @GET
    @Path("/json")
    @Produces({ "application/json" })
    public String helloWorld() {
        return "{\"result\":\"" + "\"}";
    }

}
