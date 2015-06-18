package com.rhc.booking.services.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.rhc.booking.entities.User;
import com.rhc.booking.services.Authenticator;

@Stateless
public class AuthenticatorServiceImpl implements Authenticator
{
    @PersistenceContext
    private EntityManager em;

    @Override
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

}
