package com.rhc.booking.services.impl;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.rhc.booking.entities.User;

public class AuthenticatorServiceImpl implements SessionBean 
{
    private EntityManager em;

    public boolean authenticate(String user, String pass)
    {
        Query query = em.createNamedQuery("user.authenticate");
        query.setParameter("userName", user);
        query.setParameter("password", pass);
        
        List<User> results = query.getResultList();
        //only return true if we match exactly 1 account.
        if(results.size() == 1) {
            return true;
        }
        return false;
    }

    @Override
    public void ejbActivate() throws EJBException, RemoteException
    {
        Context ctx;
        try
        {
            ctx = new InitialContext();
            em = (EntityManager) ctx.lookup("java:comp/em/HotelEntityManager");
        }
        catch (NamingException e)
        {
            throw new EJBException(e);
        }
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
