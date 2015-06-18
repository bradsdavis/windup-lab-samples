package com.rhc.booking.ws;

import java.rmi.RemoteException;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.rhc.booking.entities.User;
import com.rhc.booking.services.UserServiceRemote;

@Path("/BookingService")
public class BookingService
{
    @Resource(mappedName="java:global/sample-app-ear-1.0.0/sample-app-ejb-1.0.0/UserService!com.rhc.booking.services.UserServiceRemote")
    private UserServiceRemote userService;
    
    
    @GET
    @Path("/json")
    @Produces({ "application/json" })
    public String registerUser() {
        try
        {
            User user = userService.registerUser("Brad", "brad", "bd1");
            return "{\"result\":\"" + user.getUsername() + "\"}";
        }
        catch (RemoteException e)
        {
            return "{\"error\":\"" + e.getMessage() + "\"}";
        }
        
        
    }

}
