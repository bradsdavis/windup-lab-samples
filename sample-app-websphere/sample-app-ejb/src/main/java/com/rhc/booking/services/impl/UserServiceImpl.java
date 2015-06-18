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

public class UserServiceImpl implements SessionBean
{
    private EntityManager em;
    
    public boolean isUsernameAvailable(String userName)
    {
        System.out.println("Username: "+userName);
        Query query = getEntityManager().createNamedQuery("user.findByUserName");
        query.setParameter("userName", userName);
        
        List<User> results = query.getResultList();
        
        //make sure there are no users with this username...
        return (results.size() == 0);
    }

    public User registerUser(String name, String userName, String password)
    {
         System.out.println(name+", "+userName);
        if(isUsernameAvailable(userName)) {
            User user = new User(name, password, userName);
            getEntityManager().merge(user);
            
            return user;
        }
        
        throw new IllegalStateException("Username is taken: "+userName);
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
