package com.rhc.booking.services;

import javax.ejb.EJBObject;

import com.rhc.booking.entities.User;

public interface UserService extends EJBObject
{
    public boolean isUsernameAvailable(String nameName);

    public User registerUser(String name, String userName, String password);
}