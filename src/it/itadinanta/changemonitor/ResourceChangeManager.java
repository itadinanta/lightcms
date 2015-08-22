package it.itadinanta.changemonitor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;

public class ResourceChangeManager implements Runnable {
	static public final long DEFAULT_INTERVAL = 2000;
	static private final Logger LOG = Logger.getLogger(ResourceChangeManager.class);
	private HashMap<Resource, ResourceChangeMonitor> monitors = new LinkedHashMap<Resource, ResourceChangeMonitor>();
	private volatile boolean running;
	private Thread monitorThread;
	private long interval = DEFAULT_INTERVAL;

	public ResourceChangeManager() {
	}
	public void setInterval(long interval) {
		this.interval = interval;
	}
	public void start() {
		monitorThread = createThread();
		running = true;
		if (LOG.isDebugEnabled())
			LOG.debug("Resource change monitor thread started");
		monitorThread.start();
	}
	protected Thread createThread() {
		return new Thread(this);
	}
	public void registerListener(Resource resource, ResourceChangeListener listener) {
		synchronized (monitors) {
			ResourceChangeMonitor monitor = monitors.get(resource);
			if (monitor == null) {
				monitor = new ResourceChangeMonitor(resource);
				monitors.put(resource, monitor);
			}
			monitor.registerListener(listener);
			if (LOG.isDebugEnabled())
				LOG.debug("Enabling monitoring of " + resource.getDescription() + " requested by " + listener);
		}
	}
	public void unregisterListener(Resource resource, ResourceChangeListener listener) {
		synchronized (monitors) {
			ResourceChangeMonitor monitor = monitors.get(resource);
			if (monitor != null) {
				monitor.unregisterListener(listener);
				if (monitor.size() == 0)
					monitors.remove(resource);
			}
			if (LOG.isDebugEnabled())
				LOG.debug("Disabling monitoring of " + resource.getDescription() + " requested by " + listener);
		}
	}
	public void run() {
		while (running) {
			synchronized (monitors) {
				for (ResourceChangeMonitor monitor : monitors.values())
					monitor.monitor();
			}
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				LOG.error("Monitor thread interrupted unexpectedly");
				break;
			}
		}
	}
	public void stop() {
		if (LOG.isDebugEnabled())
			LOG.debug("Stopping monitor thread...");
		this.running = false;
		try {
			monitorThread.join();
			if (LOG.isDebugEnabled())
				LOG.debug("Monitor thread stopped successfully");
		} catch (InterruptedException e) {
			LOG.error("Exception stopping monitor thread ", e);
		}
	}
}
