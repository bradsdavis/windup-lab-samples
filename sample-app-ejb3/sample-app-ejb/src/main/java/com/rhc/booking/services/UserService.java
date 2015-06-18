//$Id: ChangePassword.java 5579 2007-06-27 00:06:49Z gavin $
package com.rhc.booking.services;

import javax.ejb.Local;

import com.rhc.booking.entities.User;

@Local
public interface UserService
{
    public boolean isUsernameAvailable(String nameName);

    public User registerUser(String name, String userName, String password);
}