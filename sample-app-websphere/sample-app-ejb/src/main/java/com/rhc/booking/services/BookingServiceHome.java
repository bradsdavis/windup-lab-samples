package com.rhc.booking.services;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface BookingServiceHome extends EJBHome
{
    public BookingServiceRemote create() throws CreateException, RemoteException;
}