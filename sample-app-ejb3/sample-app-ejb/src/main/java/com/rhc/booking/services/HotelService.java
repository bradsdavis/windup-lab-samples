package com.rhc.booking.services;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import com.rhc.booking.entities.Hotel;

@Local
public interface HotelService
{
    public List<Hotel> findByCity(String city, String state);

    public boolean isAvailable(Hotel hotel, Date checkIn, Date checkOut, int numberRooms);
}