package com.rhc.booking.ws;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rhc.booking.entities.User;
import com.rhc.booking.exception.BookingException;
import com.rhc.booking.services.EventServer;
import com.rhc.booking.services.NotificationServiceLocal;
import com.rhc.booking.services.UserServiceRemote;

@Path("/BookingService")
public class BookingService
{
    private static final Log log = LogFactory.getLog(BookingService.class);
    
    @Resource(mappedName="java:comp/env/ejb/remote/UserServiceRemote")
    private UserServiceRemote userService;
    
    @Resource(mappedName="java:comp/env/ejb/local/NotificationServiceLocal")
    private NotificationServiceLocal notificationService;
    
    private EventServer eventServer;
    
    @GET
    @Path("/registerUser")
    @Produces({ "application/json" })
    public String registerUser(@QueryParam("name") String name, @QueryParam("userName") String userName, @QueryParam("password") String password) {
        try
        {
            log.info("Received Parameters");
            log.info("==============================");
            log.info(" - Name: "+name);
            log.info(" - UserName: "+userName);
            log.info(" - Password: "+password);
            
            User user = userService.registerUser(name, userName, password);
            
            eventServer.processEvent("Registration Event: "+userName);
            
            return "{\"result\":\"" + user.getUsername() + "\"}";
        }
        catch (RemoteException e)
        {
            return "{\"error\":\"" + e.getCause().getMessage() + "\"}";
        }
    }

    
    @GET
    @Path("/sendNotification")
    @Produces({ "application/json" })
    public String sendNotification(@QueryParam("message") String message) {
        try
        {
            log.info("Received Parameters");
            log.info("==============================");
            log.info(" - Message: "+message);
            
            notificationService.sendNotification(message);
            
            log.info(" - Sent to JMS Queue: java:comp/env/jms/NotificationQueue");
            return "{\"result\":\"" + "sent" + "\"}";
        }
        catch (Exception e)
        {
            return "{\"error\":\"" + e.getCause().getMessage() + "\"}";
        }
    }
    
    
    @GET
    @Path("/sendEvent")
    @Produces({ "application/json" })
    public String sendEvent(@QueryParam("message") String message) {
        try
        {
            log.info("Received Parameters");
            log.info("==============================");
            log.info(" - Message: "+message);
            
            String event = getEventServer().processEvent(message);
            return "{\"result\":\"" + event + "\"}";
        }
        catch (Exception e)
        {
            return "{\"error\":\"" + e.getCause().getMessage() + "\"}";
        }
    }
    
    private EventServer getEventServer() {
	    if(eventServer == null) {
	    	Registry registry;
			try {
				registry = LocateRegistry.getRegistry("localhost");
				eventServer = (EventServer) registry.lookup("eventServer");
			} catch (Exception e) {
				throw new BookingException("Exception setting up event server.", e);
			}
	    }
	    return eventServer;
	}
}
