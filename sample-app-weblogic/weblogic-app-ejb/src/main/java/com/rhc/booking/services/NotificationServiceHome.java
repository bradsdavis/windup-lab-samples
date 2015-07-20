package com.rhc.booking.services;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface NotificationServiceHome extends EJBHome
{
    public NotificationServiceRemote create() throws CreateException, RemoteException;
}