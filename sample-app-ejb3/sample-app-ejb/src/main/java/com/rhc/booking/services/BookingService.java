//$Id: BookingList.java 5579 2007-06-27 00:06:49Z gavin $
package com.rhc.booking.services;

import java.util.Date;

import javax.ejb.Local;

import com.rhc.booking.entities.Booking;
import com.rhc.booking.entities.Hotel;
import com.rhc.booking.entities.User;

@Local
public interface BookingService
{
    public Booking find(String confirmationId);

    public Booking create(Hotel hotel, User user, Date checkIn, Date checkOut, int beds);

    public boolean cancel(Booking booking);
}