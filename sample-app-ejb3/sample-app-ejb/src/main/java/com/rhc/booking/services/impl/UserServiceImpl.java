package com.rhc.booking.services.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.rhc.booking.entities.User;
import com.rhc.booking.services.UserService;

@Stateless
public class UserServiceImpl implements UserService
{

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public boolean isUsernameAvailable(String userName)
    {
        Query query = em.createNativeQuery("user.findByUserName");
        query.setParameter("userName", userName);
        
        List<User> results = query.getResultList();
        
        //make sure there are no users with this username...
        return (results.size() == 0);
    }

    @Override
    public User registerUser(String name, String userName, String password)
    {
        if(isUsernameAvailable(userName)) {
            User user = new User(name, password, userName);
            em.merge(user);
            
            return user;
        }
        
        throw new IllegalStateException("Username is taken: "+userName);
    }

}
