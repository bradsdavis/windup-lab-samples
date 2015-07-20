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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rhc.booking.entities.User;
import com.rhc.booking.services.NotificationServiceLocal;
import com.rhc.booking.services.ValidationException;

public class UserServiceImpl implements SessionBean
{
    private static final Log LOG = LogFactory.getLog(UserServiceImpl.class);
    private NotificationServiceLocal notificationService;
    private EntityManager em;
    
    public boolean isUsernameAvailable(String userName)
    {
        Query query = getEntityManager().createNamedQuery("user.findByUserName");
        query.setParameter("userName", userName);
        
        List<User> results = query.getResultList();
        
        //make sure there are no users with this username...
        return (results.size() == 0);
    }

    public User registerUser(String name, String userName, String password)
    {
        if(isUsernameAvailable(userName)) {
            User user = new User(name, password, userName);
            getEntityManager().merge(user);
            
            try {
                getNotificationService().sendNotification("Registered User: "+userName);
            }
            catch(NamingException e) {
                throw new ValidationException("Exception sending notification for username: "+userName, e);
            }
            
            return user;
        }
        
        throw new ValidationException("Username is taken: "+userName);
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


    private NotificationServiceLocal getNotificationService() throws NamingException {
        if(notificationService == null) {
            Context ctx = new InitialContext(); 
            notificationService = (NotificationServiceLocal) ctx.lookup("java:comp/env/ejb/local/NotificationServiceLocal");
        }
        return notificationService;
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
