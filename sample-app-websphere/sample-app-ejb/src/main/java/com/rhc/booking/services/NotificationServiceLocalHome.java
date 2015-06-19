package com.rhc.booking.services;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface NotificationServiceLocalHome  extends EJBLocalHome {
    NotificationServiceLocal create() throws CreateException, RemoteException;
}
