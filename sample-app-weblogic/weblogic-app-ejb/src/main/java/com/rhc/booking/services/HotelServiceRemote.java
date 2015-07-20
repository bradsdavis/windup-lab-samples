package com.rhc.booking.services;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import javax.ejb.EJBObject;

import com.rhc.booking.entities.Hotel;

public interface HotelServiceRemote extends EJBObject
{
    public List<Hotel> findByCity(String city, String state) throws RemoteException;

    public boolean isAvailable(Hotel hotel, Date checkIn, Date checkOut, int numberRooms)  throws RemoteException;
}