package com.rhc.booking.services.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rhc.booking.services.EventServer;

public class EventServerImpl extends UnicastRemoteObject implements EventServer{
	private static final Log log = LogFactory.getLog(EventServerImpl.class);
	
	public EventServerImpl() throws RemoteException {
		
	}
	
	public String processEvent(String event) throws RemoteException {
		log.info("Received Event");
        log.info("==============================");
        log.info(" - Event: "+event);
        
		return "Processed: "+event;
	}
}
