package it.itadinanta.lightcms.toolkit.sitemap;

import java.util.Iterator;

public abstract class NavigatorIterator implements Iterator<SitemapNode> {
	protected Navigator target;
	public NavigatorIterator(Navigator target) {
		this.target = target.newInstance();
	}
	public final boolean hasNext() {
		return target.current() != null;
	}
	protected abstract void advance();
	public final SitemapNode next() {
		SitemapNode current = target.current();
		advance();
		return current;
	}
	public final void remove() {
	}
}
