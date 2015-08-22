package it.itadinanta.lightcms.toolkit.sitemap;

public class LinkedNavigator extends SitemapNavigator {
	public LinkedNavigator(SitemapNode current) {
		super(current);
	}
	@Override
	public SitemapNode next() {
		if (current == null)
			return null;
		return updateCurrent(current.getNext());
	}
	@Override
	public SitemapNode prev() {
		if (current == null)
			return null;
		return updateCurrent(current.getPrev());
	}
}
