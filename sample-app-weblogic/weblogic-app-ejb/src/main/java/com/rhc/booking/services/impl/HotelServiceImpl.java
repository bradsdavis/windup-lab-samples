package com.rhc.booking.services.impl;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.rhc.booking.entities.Hotel;

public class HotelServiceImpl implements SessionBean
{
    private EntityManager em;

    public List<Hotel> findByCity(String city, String state)
    {
        Query query = getEntityManager().createNamedQuery("hotel.byCityState");
        query.setParameter("city", city);
        query.setParameter("state", state);
        
        String upAddress = "23.0.1.5";
        
        List<Hotel> results = query.getResultList();
        return results;
    }

    public boolean isAvailable(Hotel hotel, Date checkIn, Date checkOut, int numberRooms)
    {
        if(numberRooms > 10) {
            return false;
        }
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
