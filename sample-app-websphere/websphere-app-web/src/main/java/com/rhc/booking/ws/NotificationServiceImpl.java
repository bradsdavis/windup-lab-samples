package com.rhc.booking.ws;

import java.rmi.RemoteException;

import javax.annotation.Resource;
import javax.jws.WebService;

import com.rhc.booking.services.NotificationServiceRemote;

@WebService(endpointInterface = "com.rhc.booking.ws.NotificationService")
public class NotificationServiceImpl implements NotificationService
{
    @Resource(mappedName="java:comp/env/ejb/local/NotificationServiceLocal")
    private NotificationServiceRemote notificationService;
         
    @Override
    public void sendNotification(String notification)
    {
        try
        {
            notificationService.sendNotification(notification);
        }
        catch (RemoteException e)
        {
            throw new RuntimeException("Exception sending notification.", e);
        }
    }
}
