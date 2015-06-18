package com.rhc.booking.services.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.rhc.booking.entities.Booking;
import com.rhc.booking.entities.Hotel;
import com.rhc.booking.entities.User;
import com.rhc.booking.services.BookingService;

@Stateless
public class BookingServiceImpl implements BookingService
{
    @PersistenceContext
    private EntityManager em;

    @Override
    public Booking find(String confirmationId)
    {
        Query query = em.createNamedQuery("booking.byConfirmationCode");
        query.setParameter("confirmationCode", confirmationId);

        List<Booking> results = query.getResultList();
        // only return true if we match exactly 1 account.
        if (results.size() == 0)
        {
            return null;
        }
        else if (results.size() == 1)
        {
            return results.get(0);
        }

        throw new IllegalStateException("More than one booking for: " + confirmationId);
    }

    @Override
    public Booking create(Hotel hotel, User user, Date checkIn, Date checkOut, int beds)
    {
        Booking booking = new Booking();
        booking.setBeds(beds);
        booking.setCheckinDate(checkIn);
        booking.setCheckoutDate(checkOut);
        booking.setHotel(hotel);
        booking.setUser(user);
        booking.setConfirmationCode(UUID.randomUUID().toString());
        em.merge(booking);

        return booking;
    }

    @Override
    public boolean cancel(Booking booking)
    {
        em.remove(booking);
        return true;
    }

}
