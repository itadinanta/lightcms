package it.itadinanta.lightcms.toolkit.sitemap;

public interface Navigator {
	public SitemapNode next();
	public SitemapNode prev();
	public SitemapNode nextSibling();
	public SitemapNode prevSibling();
	public SitemapNode firstChild();
	public SitemapNode lastChild();
	public SitemapNode parent();
	public SitemapNode current();
	public boolean equals(Object compareTo);
	public Navigator newInstance();
	public void lock(boolean lock);
	public boolean isLocked();
}
