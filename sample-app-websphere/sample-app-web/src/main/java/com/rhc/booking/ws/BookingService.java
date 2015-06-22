package com.rhc.booking.ws;

import java.rmi.RemoteException;

import javax.annotation.Resource;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rhc.booking.entities.User;
import com.rhc.booking.services.NotificationServiceLocal;
import com.rhc.booking.services.UserServiceRemote;

@Path("/BookingService")
public class BookingService
{
    private static final Log LOG = LogFactory.getLog(BookingService.class);
    
    @Resource(mappedName="java:comp/env/ejb/remote/UserServiceRemote")
    private UserServiceRemote userService;
    
    @Resource(mappedName="java:comp/env/ejb/local/NotificationServiceLocal")
    private NotificationServiceLocal notificationService;
    
    
    @POST
    @Path("/registerUser")
    @Produces({ "application/json" })
    public String registerUser(@QueryParam("name") String name, @QueryParam("userName") String userName, @QueryParam("password") String password) {
        try
        {
            LOG.info("Received Parameters");
            LOG.info("==============================");
            LOG.info(" - Name: "+name);
            LOG.info(" - UserName: "+userName);
            LOG.info(" - Password: "+password);
            
            User user = userService.registerUser(name, userName, password);
            return "{\"result\":\"" + user.getUsername() + "\"}";
        }
        catch (RemoteException e)
        {
            return "{\"error\":\"" + e.getCause().getMessage() + "\"}";
        }
    }

    
    @POST
    @Path("/sendNotification")
    @Produces({ "application/json" })
    public String sendNotification(@QueryParam("message") String message) {
        try
        {
            LOG.info("Received Parameters");
            LOG.info("==============================");
            LOG.info(" - Message: "+message);
            
            notificationService.sendNotification(message);
            return "{\"result\":\"" + "sent" + "\"}";
        }
        catch (Exception e)
        {
            return "{\"error\":\"" + e.getCause().getMessage() + "\"}";
        }
    }
}
