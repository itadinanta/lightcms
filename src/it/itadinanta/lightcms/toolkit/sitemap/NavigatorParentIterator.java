package it.itadinanta.lightcms.toolkit.sitemap;

public class NavigatorParentIterator extends NavigatorIterator {
	public NavigatorParentIterator(Navigator target) {
		super(target);
	}
	@Override
	protected void advance() {
		target.parent();
	}
}
