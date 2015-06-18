package com.rhc.booking.services.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.rhc.booking.entities.Hotel;
import com.rhc.booking.services.HotelService;

@Stateless
public class HotelServiceImpl implements HotelService
{
    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Hotel> findByCity(String city, String state)
    {
        Query query = em.createNamedQuery("hotel.byCityState");
        query.setParameter("city", city);
        query.setParameter("state", state);
        
        List<Hotel> results = query.getResultList();
        return results;
    }

    @Override
    public boolean isAvailable(Hotel hotel, Date checkIn, Date checkOut, int numberRooms)
    {
        if(numberRooms > 10) {
            return false;
        }
        return true;
    }

}
