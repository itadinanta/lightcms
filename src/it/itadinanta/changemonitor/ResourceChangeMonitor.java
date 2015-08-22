package it.itadinanta.changemonitor;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;

public class ResourceChangeMonitor {
	static private final Logger LOG = Logger.getLogger(ResourceChangeMonitor.class);
	private ResourceChangeDescriptor descriptor;
	private List<ResourceChangeListener> listeners = new ArrayList<ResourceChangeListener>();

	public class ResourceChangeEventImpl implements ResourceChangeEvent {
		private ResourceChange change;
		private Resource resource;

		public ResourceChangeEventImpl(ResourceChange change, Resource resource) {
			this.change = change;
			this.resource = resource;
		}
		public ResourceChange getChange() {
			return change;
		}
		public Resource getResource() {
			return resource;
		}
	}

	public ResourceChangeMonitor(Resource resource) {
		this.descriptor = new ResourceChangeDescriptor(resource);
	}
	public synchronized void registerListener(ResourceChangeListener listener) {
		listeners.add(listener);
	}
	public synchronized void unregisterListener(ResourceChangeListener listener) {
		listeners.remove(listener);
	}
	public synchronized void monitor() {
		ResourceChange change = descriptor.monitor();
		try {
			if (change != ResourceChange.UNCHANGED)
				for (ResourceChangeListener listener : listeners)
					listener.onResourceChange(new ResourceChangeEventImpl(change, descriptor.getResource()));
		} catch (Exception e) {
			LOG.error("Error in monitor loop: ", e);
		}
		descriptor.acknowledge();
	}
	public int size() {
		return listeners.size();
	}
}
