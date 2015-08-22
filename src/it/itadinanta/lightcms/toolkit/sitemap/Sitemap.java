package it.itadinanta.lightcms.toolkit.sitemap;

import java.util.Collection;

public interface Sitemap {
	public Navigator getHome();
	public Navigator findNodeByName(String name);
	public Navigator findNodeByPath(String name);
	public Navigator getNode(int id);
	public Collection getNodes();
	public Navigator getDefaultNode();
	public Navigator getErrorNode();
}
