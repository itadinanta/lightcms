package it.itadinanta.lightcms.toolkit.sitemap;

public class FilteredNavigator implements Navigator, Cloneable {
	protected Navigator target;
	public FilteredNavigator(Navigator target) {
		this.target = target;
	}
	protected boolean accept(SitemapNode node) {
		return true;
	}
	private enum AcceptStatus {
		EOF, ACCEPTED, REFUSED
	}
	private SitemapNode pass(SitemapNode node) {
		if (node == null || !accept(node))
			return null;
		return node;
	}
	private AcceptStatus accepted(SitemapNode node) {
		if (node == null)
			return null;
		if (target.isLocked() || accept(node))
			return AcceptStatus.ACCEPTED;
		return AcceptStatus.REFUSED;
	}
	public SitemapNode next() {
		do {
		} while (accepted(target.next()) == AcceptStatus.REFUSED);
		return current();
	}
	public SitemapNode prev() {
		do {
		} while (accepted(target.prev()) == AcceptStatus.REFUSED);
		return current();
	}
	public SitemapNode nextSibling() {
		do {
		} while (accepted(target.nextSibling()) == AcceptStatus.REFUSED);
		return target.current();
	}
	public SitemapNode prevSibling() {
		do {
		} while (accepted(target.prevSibling()) == AcceptStatus.REFUSED);
		return target.current();
	}
	public SitemapNode firstChild() {
		return pass(target.firstChild());
	}
	public SitemapNode lastChild() {
		return pass(target.lastChild());
	}
	public SitemapNode parent() {
		return pass(target.parent());
	}
	public SitemapNode current() {
		return pass(target.current());
	}
	public void lock(boolean lock) {
		target.lock(lock);
	}
	public boolean isLocked() {
		return target.isLocked();
	}
	public Navigator newInstance() {
		try {
			return (Navigator) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
	public boolean equals(Object compareTo) {
		return ((compareTo instanceof FilteredNavigator) &&
			((FilteredNavigator)compareTo).target.equals(target))
			|| ((compareTo instanceof Navigator) &&
					((Navigator)compareTo).equals(target));
	}
}
