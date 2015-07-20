package com.rhc.booking.services;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface HotelServiceHome extends EJBHome
{
    public HotelServiceRemote create() throws CreateException, RemoteException;
}