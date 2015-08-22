package it.itadinanta.changemonitor;

import org.springframework.core.io.Resource;

public interface ResourceChangeEvent {
	public ResourceChange getChange();
	public Resource getResource();
}
