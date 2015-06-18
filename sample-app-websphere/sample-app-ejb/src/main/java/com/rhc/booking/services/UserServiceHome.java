package com.rhc.booking.services;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface UserServiceHome extends EJBHome
{
    public UserService create() throws CreateException, RemoteException;
}