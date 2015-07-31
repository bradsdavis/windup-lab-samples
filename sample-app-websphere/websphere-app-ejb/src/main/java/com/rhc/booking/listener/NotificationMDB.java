package com.rhc.booking.listener;

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rhc.booking.services.ValidationException;

public class NotificationMDB implements MessageListener, MessageDrivenBean
{
    private static final Log log = LogFactory.getLog(NotificationMDB.class);
    
    @Override
    public void onMessage(Message msg)
    {
        if(msg instanceof TextMessage) {
            TextMessage txt = (TextMessage) msg;
            try
            {
                String message = txt.getText();
                log.info("Notification Received");
                log.info("===========================");
                log.info(" - Message: "+message);
            }
            catch (JMSException e)
            {
                throw new ValidationException("Exception receiving message.", e);
            }
        }
        else {
            throw new ValidationException("Exception receiving message.");
        }
    }

    @Override
    public void ejbRemove() throws EJBException
    {
        
    }

    @Override
    public void setMessageDrivenContext(MessageDrivenContext context) throws EJBException
    {
        
    }
}
