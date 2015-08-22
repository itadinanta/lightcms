/**
 * SitemapNavigator.java
 *
 * Created on 15-Mar-2006
 * 
 * (C) 2006 Nicola Orru' - all rights reserved
 */
package it.itadinanta.lightcms.toolkit.sitemap;

import java.util.List;
import org.apache.log4j.Logger;

public class SitemapNavigator implements Navigator, Cloneable {
	private static final Logger LOG = Logger.getLogger(SitemapNavigator.class);
	protected SitemapNode current;
	protected boolean locked;
	public SitemapNavigator(SitemapNode current) {
		this.current = current;
	}
	public Navigator newInstance() {
		try {
			return (Navigator) super.clone();
		} catch (CloneNotSupportedException e) {
			LOG.error("Exception thrown ", e);
			return null;
		}
	}
	public void lock(boolean locked) {
		this.locked = locked;
	}
	protected SitemapNode updateCurrent(SitemapNode newCurrent) {
		if (!locked)
			this.current = newCurrent;
		return newCurrent;
	}
	public SitemapNode next() {
		if (current == null)
			return null;
		SitemapNode newCurrent = current.getFirstChild();
		if (newCurrent == null)
			newCurrent = current.getNextSibling();
		if (newCurrent == null) {
			SitemapNode ancestor = current;
			while (ancestor != null) {
				newCurrent = ancestor.getNextSibling();
				if (newCurrent != null)
					break;
				ancestor = ancestor.getParent();
			}
		}
		return updateCurrent(newCurrent);
	}
	public SitemapNode prev() {
		if (current == null)
			return null;
		SitemapNode relative = current.getPrevSibling();
		SitemapNode lastChild;
		SitemapNode newCurrent = null;
		while (relative != null) {
			lastChild = relative.getLastChild();
			if (lastChild == null) {
				newCurrent = relative;
				break;
			}
			relative = lastChild;
		}
		if (newCurrent == null)
			newCurrent = current.getParent();
		return updateCurrent(newCurrent);
	}
	public SitemapNode nextSibling() {
		if (current == null)
			return null;
		return updateCurrent(current.getNextSibling());
	}
	public SitemapNode prevSibling() {
		if (current == null)
			return null;
		return updateCurrent(current.getPrevSibling());
	}
	public SitemapNode firstChild() {
		if (current == null)
			return null;
		List children = current.getChildren();
		if (children == null || children.size() == 0)
			return updateCurrent(null);
		return updateCurrent((BaseSitemapNode) children.get(0));
	}
	public SitemapNode lastChild() {
		if (current == null)
			return null;
		List children = current.getChildren();
		if (children == null || children.size() == 0)
			return updateCurrent(null);
		return updateCurrent((BaseSitemapNode) children.get(children.size() - 1));
	}
	public SitemapNode parent() {
		if (current == null)
			return null;
		return updateCurrent(current.getParent());
	}
	public SitemapNode current() {
		return current;
	}
	public boolean isLocked() {
		return locked;
	}
	public boolean equals(Object compareTo) {
		return (compareTo != null) && (compareTo instanceof SitemapNavigator)
				&& ((SitemapNavigator) compareTo).current == current;
	}
}