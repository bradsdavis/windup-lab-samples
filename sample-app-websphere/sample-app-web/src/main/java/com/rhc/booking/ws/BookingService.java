package com.rhc.booking.ws;

import java.rmi.RemoteException;

import javax.annotation.Resource;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.rhc.booking.entities.User;
import com.rhc.booking.services.NotificationServiceLocal;
import com.rhc.booking.services.UserServiceRemote;

@Path("/BookingService")
public class BookingService
{
    @Resource(mappedName="java:comp/env/ejb/remote/UserServiceRemote")
    private UserServiceRemote userService;
    
    @Resource(mappedName="java:comp/env/ejb/local/NotificationServiceLocal")
    private NotificationServiceLocal notificationService;
    
    
    @POST
    @Path("/registerUser")
    @Produces({ "application/json" })
    public String registerUser(@HeaderParam("name") String name, @HeaderParam("userName") String userName, @HeaderParam("password") String password) {
        try
        {
            notificationService.sendNotification("Notification!");
            
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
