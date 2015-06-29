package com.rhc.booking.listener;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.rhc.booking.services.impl.EventServerImpl;

/**
 * Registers the RMI Service to the server on WAR startup.
 * @author bradsdavis@gmail.com
 *
 */
@WebListener
public class RegisterEventRMI implements ServletContextListener {
	private static final Log LOG = LogFactory.getLog(RegisterEventRMI.class);
	private static final String BIND_LOCATION = "eventServer";
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		Registry registry;
		try {
			registry = LocateRegistry.createRegistry(1099);
	        registry.unbind(BIND_LOCATION);
			LOG.info("ServletContextListener destroyed: "+BIND_LOCATION);
		} catch (Exception e) {
			LOG.error("Exception destroying service: "+BIND_LOCATION, e);
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		Registry registry;
		try {
			registry = LocateRegistry.createRegistry(1099);
	        registry.rebind("eventServer", new EventServerImpl());
			LOG.info("ServletContextListener created. Service bound to: "+BIND_LOCATION);
		} catch (RemoteException e) {
			LOG.error("Exception creating service: "+BIND_LOCATION, e);
		}
	}
}
