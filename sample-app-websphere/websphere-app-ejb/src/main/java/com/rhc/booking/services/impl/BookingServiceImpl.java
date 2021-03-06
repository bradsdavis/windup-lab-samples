package com.rhc.booking.services.impl;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rhc.booking.entities.Booking;
import com.rhc.booking.entities.Hotel;
import com.rhc.booking.entities.User;
import com.rhc.booking.services.NotificationServiceLocal;
import com.rhc.booking.services.ValidationException;

public class BookingServiceImpl implements SessionBean
{
    private static final Log LOG = LogFactory.getLog(BookingServiceImpl.class);
    private NotificationServiceLocal notificationService;
    private EntityManager em;

    public Booking find(String confirmationId)
    {
        Query query = getEntityManager().createNamedQuery("booking.byConfirmationCode");
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

        throw new ValidationException("More than one booking for: " + confirmationId);
    }

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

        try
        {
            getNotificationService().sendNotification("Confirmation Code: "+booking.getConfirmationCode());
        }
        catch (NamingException e)
        {
            LOG.error("Exception sending confirmation: "+booking.getConfirmationCode(), e);
        }
        
        return booking;
    }
    
    private NotificationServiceLocal getNotificationService() throws NamingException {
        if(notificationService == null) {
            Context ctx = new InitialContext(); 
            notificationService = (NotificationServiceLocal) ctx.lookup("ejb/local/NotificationServiceLocal");
        }
        return notificationService;
    }
    
    

    public boolean cancel(Booking booking)
    {
        em.remove(booking);
        return true;
    }
    
    public EntityManager getEntityManager() {
        if(em != null) {
            return em;
        }
        
        Context ctx;
        try
        {
            ctx = new InitialContext();
            em = (EntityManager) ctx.lookup("java:comp/env/em/HotelEntityManager");
        }
        catch (NamingException e)
        {
            throw new EJBException(e);
        }
        
        return em;
    }

    @Override
    public void ejbActivate() throws EJBException, RemoteException
    {
        
    }

    @Override
    public void ejbPassivate() throws EJBException, RemoteException
    {
        
    }

    @Override
    public void ejbRemove() throws EJBException, RemoteException
    {
        
    }

    @Override
    public void setSessionContext(SessionContext arg0) throws EJBException, RemoteException
    {

    }
}
