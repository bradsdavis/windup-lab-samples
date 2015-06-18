package com.rhc.booking.services;

import javax.ejb.EJBObject;

public interface AuthenticatorService extends EJBObject 
{
   boolean authenticate(String user, String pass);
}
