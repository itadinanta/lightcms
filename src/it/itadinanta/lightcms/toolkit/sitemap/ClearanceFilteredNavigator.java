package it.itadinanta.lightcms.toolkit.sitemap;

import it.itadinanta.lightcms.auth.User;

public class ClearanceFilteredNavigator extends FilteredNavigator {
	private User user;
	public ClearanceFilteredNavigator(User user, Navigator target) {
		super(target);
		this.user = user;
	}
	@Override
	protected boolean accept(SitemapNode node) {
		return node.getClearance() <= user.getClearance();
	}
}
