package com.rhc.booking.services;

import javax.ejb.EJBLocalObject;

public interface NotificationServiceLocal extends EJBLocalObject {
    public void sendNotification(String notification);
}
