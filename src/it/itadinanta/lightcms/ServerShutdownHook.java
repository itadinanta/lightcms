package it.itadinanta.lightcms;

import org.apache.log4j.Logger;

/**
 * ServerShutdownHook.java
 * 
 * Created on 26-Mar-2006
 * 
 * (C) 2006 Nicola Orru' - all rights reserved
 */
public class ServerShutdownHook extends Thread {
	static private final Logger LOG = Logger.getLogger(ServerShutdownHook.class);
	private CmsServer server;

	public ServerShutdownHook(CmsServer server) {
		this.server = server;
	}
	public void run() {
		try {
			LOG.info("Shutting down...");
			server.stop();
			LOG.info("Shutdown completed.");
		} catch (Exception e) {
			LOG.error("Exception thrown ", e);
		}
	}
}