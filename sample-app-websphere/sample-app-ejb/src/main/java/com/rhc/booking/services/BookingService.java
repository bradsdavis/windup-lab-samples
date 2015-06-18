package com.rhc.booking.services;

import java.util.Date;

import javax.ejb.EJBObject;

import com.rhc.booking.entities.Booking;
import com.rhc.booking.entities.Hotel;
import com.rhc.booking.entities.User;

public interface BookingService extends EJBObject
{
    public Booking find(String confirmationId);

    public Booking create(Hotel hotel, User user, Date checkIn, Date checkOut, int beds);

    public boolean cancel(Booking booking);
}