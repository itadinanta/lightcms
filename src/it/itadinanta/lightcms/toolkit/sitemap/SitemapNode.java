package it.itadinanta.lightcms.toolkit.sitemap;

import java.util.List;

public interface SitemapNode {
	public List getChildren();
	public Sitemap getSitemap();
	public SitemapNode getPrev();
	public SitemapNode getNext();
	public SitemapNode getParent();
	public SitemapNode getFirstChild();
	public SitemapNode getLastChild();
	public SitemapNode getNextSibling();
	public SitemapNode getPrevSibling();
	public int getLevel();
	public int getClearance();
	public String getBody();
	public String getView();
	public String getName();
	public String getUrl();
	public Class getHandler();
	public String getMethod();
	public String getTitle();
	public boolean isEnabled();
	public int getId();
	public String getAttribute(String key);
}