package it.itadinanta.lightcms.toolkit.sitemap;

public class NavigatorForwardIterator extends NavigatorIterator {
	public NavigatorForwardIterator(Navigator target) {
		super(target);
	}
	@Override
	protected void advance() {
		target.next();
	}
}
