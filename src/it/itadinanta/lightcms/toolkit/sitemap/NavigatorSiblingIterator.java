package it.itadinanta.lightcms.toolkit.sitemap;

public class NavigatorSiblingIterator extends NavigatorIterator {
	public NavigatorSiblingIterator(Navigator target) {
		super(target);
	}
	@Override
	protected void advance() {
		target.nextSibling();
	}
}
