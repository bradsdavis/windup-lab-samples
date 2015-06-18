package com.rhc.booking.services;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface AuthenticatorServiceHome extends EJBHome 
{
    public AuthenticatorServiceRemote create() throws CreateException, RemoteException;
}
