package it.itadinanta.lightcms.toolkit;

import it.itadinanta.changemonitor.ResourceChangeEvent;
import it.itadinanta.changemonitor.ResourceChangeListener;
import it.itadinanta.changemonitor.ResourceChangeManager;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;

public abstract class Loader implements ResourceChangeListener {
	static private final Logger LOG = Logger.getLogger(Loader.class);
	private Resource configLocation;
	private ResourceChangeManager resourceChangeManager;

	public void setConfigLocation(Resource configLocation) {
		this.configLocation = configLocation;
	}
	protected abstract void loadConfig(InputStream stream) throws Exception;
	public void setResourceChangeManager(ResourceChangeManager resourceChangeManager) {
		this.resourceChangeManager = resourceChangeManager;
	}
	public void init() throws Exception {
		if (resourceChangeManager != null)
			resourceChangeManager.registerListener(configLocation, this);
		loadConfig();
	}
	public void close() {
		if (resourceChangeManager != null)
			resourceChangeManager.unregisterListener(configLocation, this);
	}
	public void loadConfig() throws Exception {
		InputStream stream = configLocation.getInputStream();
		if (stream == null)
			throw new FileNotFoundException("Configuration file not found :" + configLocation);
		try {
			loadConfig(stream);
		} finally {
			stream.close();
		}
	}
	public void onResourceChange(ResourceChangeEvent event) {
		try {
			loadConfig();
		} catch (Exception e) {
			LOG.error("Error reloading configuration file: " + configLocation, e);
		}
	}
}
