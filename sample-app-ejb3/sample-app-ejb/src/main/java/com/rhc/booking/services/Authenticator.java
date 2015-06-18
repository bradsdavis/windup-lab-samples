package com.rhc.booking.services;

import javax.ejb.Local;

@Local
public interface Authenticator
{
   boolean authenticate(String user, String pass);
}
