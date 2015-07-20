package com.rhc.booking.services.impl;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NotificationServiceImpl implements SessionBean
{
    private static final Log LOG = LogFactory.getLog(NotificationServiceImpl.class);
    private QueueConnection queueConnection = null;
    private QueueSession queueSession = null;
    private QueueSender queueSender = null;

    public void sendNotification(String notification) { 
        try {
            initialize();
            TextMessage msg = queueSession.createTextMessage();
            msg.setText(notification);
            queueSender.send(msg);
        }
        catch(Exception e) {
            throw new EJBException("Exception sending notification: "+notification, e);
        }
    }

    private void initialize() throws JMSException, NamingException {
        if(queueConnection == null || queueSession == null || queueSender == null) {
            Context ctx = new InitialContext(); 
            QueueConnectionFactory qcf = (QueueConnectionFactory)ctx.lookup("java:comp/env/jms/NotificationConnectionFactory");
            Queue notificationQueue = (Queue)ctx.lookup("java:comp/env/jms/NotificationQueue");
            
            queueConnection = qcf.createQueueConnection(); 
            queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            queueSender = queueSession.createSender(notificationQueue);
            
            ctx.close();
        }
    }
    
    private void stopQuietly() {
        try {
            if(queueConnection != null) {
                queueConnection.close();
                queueConnection = null;
            }
            
            if(queueSender != null) {
                queueSender.close();
                queueSender = null;
            }
            
            if(queueSession != null) {
                queueSession.close();
                queueSession = null;
            }
        }
        catch(Exception e) {
            LOG.error("Exception shutting down connections.", e);
        }
    }
    
    @Override
    public void ejbActivate()
    {

    }

    @Override
    public void ejbPassivate()
    {
        stopQuietly();
    }

    @Override
    public void ejbRemove()
    {
        stopQuietly();
    }

    @Override
    public void setSessionContext(SessionContext conext) throws EJBException
    {

    }
}
