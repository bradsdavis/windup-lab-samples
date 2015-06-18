package com.rhc.booking.services;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.rhc.booking.entities.User;

public interface UserServiceRemote extends EJBObject
{
    public boolean isUsernameAvailable(String nameName) throws RemoteException;

    public User registerUser(String name, String userName, String password)  throws RemoteException;
}