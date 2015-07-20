package com.rhc.booking.services;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

public interface NotificationServiceRemote extends EJBObject {
    public void sendNotification(String notification) throws RemoteException;
}
