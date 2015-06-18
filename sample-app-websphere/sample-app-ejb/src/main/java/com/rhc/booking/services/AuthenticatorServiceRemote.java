package com.rhc.booking.services;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

public interface AuthenticatorServiceRemote extends EJBObject 
{
   boolean authenticate(String user, String pass) throws RemoteException;
}
