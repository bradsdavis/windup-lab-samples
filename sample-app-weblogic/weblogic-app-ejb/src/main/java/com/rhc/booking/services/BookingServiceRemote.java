package com.rhc.booking.services;

import java.rmi.RemoteException;
import java.util.Date;

import javax.ejb.EJBObject;

import com.rhc.booking.entities.Booking;
import com.rhc.booking.entities.Hotel;
import com.rhc.booking.entities.User;

public interface BookingServiceRemote extends EJBObject
{
    public Booking find(String confirmationId) throws RemoteException;

    public Booking create(Hotel hotel, User user, Date checkIn, Date checkOut, int beds) throws RemoteException;

    public boolean cancel(Booking booking) throws RemoteException;
}