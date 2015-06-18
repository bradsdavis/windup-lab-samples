package com.rhc.booking.ws;

import java.rmi.RemoteException;

import javax.annotation.Resource;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.rhc.booking.entities.User;
import com.rhc.booking.services.UserServiceRemote;

@Path("/BookingService")
public class BookingService
{
    @Resource(mappedName="java:global/sample-app-ear-1.0.0/sample-app-ejb-1.0.0/UserService!com.rhc.booking.services.UserServiceRemote")
    private UserServiceRemote userService;
    
    
    @POST
    @Path("/registerUser")
    @Produces({ "application/json" })
    public String registerUser(@HeaderParam("name") String name, @HeaderParam("userName") String userName, @HeaderParam("password") String password) {
        try
        {
            System.out.println("Name: "+name);
            System.out.println("UserName: "+userName);
            System.out.println("Password: "+password);
            User user = userService.registerUser(name, userName, password);
            return "{\"result\":\"" + user.getUsername() + "\"}";
        }
        catch (RemoteException e)
        {
            return "{\"error\":\"" + e.getCause().getMessage() + "\"}";
        }
        
        
    }

}
