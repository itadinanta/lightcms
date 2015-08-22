package it.itadinanta.changemonitor;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;

public class ResourceChangeDescriptor {
	static private final Logger LOG = Logger.getLogger(ResourceChangeDescriptor.class);
	private ResourceChange change;
	private Resource resource;
	private long lastModified;

	public ResourceChangeDescriptor(Resource resource) {
		this.resource = resource;
		change = ResourceChange.UNCHANGED;
		lastModified = getLastModified();
	}
	protected long getLastModified() {
		if (resource.exists())
			try {
				return resource.getFile().lastModified();
			} catch (IOException e) {
			}
		return 0;
	}
	public ResourceChange acknowledge() {
		ResourceChange currentState = change;
		change = ResourceChange.UNCHANGED;
		return currentState;
	}
	private void detectedChange(ResourceChange changeState, long newLastModified) {
		if (LOG.isDebugEnabled())
			LOG.debug("Detected change " + changeState + " on resource " + resource.getDescription());
		this.change = changeState;
		lastModified = newLastModified;
	}
	public ResourceChange monitor() {
		long newLastModified = getLastModified();
		if (newLastModified > 0) {
			if (lastModified == 0)
				detectedChange(ResourceChange.CREATE, newLastModified);
			else if (newLastModified > lastModified)
				detectedChange(ResourceChange.MODIFY, newLastModified);
		} else {
			if (lastModified != 0)
				detectedChange(ResourceChange.DELETE, 0);
		}
		return change;
	}
	public ResourceChange getChange() {
		return change;
	}
	public Resource getResource() {
		return resource;
	}
}