package it.itadinanta.lightcms.controllers;

import it.itadinanta.lightcms.auth.AuthService;
import it.itadinanta.lightcms.auth.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ToolkitAccessController extends ToolkitController {
	private static final String ATTR_PASSWORD = "password";
	private static final String ATTR_USERNAME = "username";
	private static final String SESSION_ATTRIBUTE_USER = "user";
	protected AuthService authService;
	public void setAuthService(AuthService authService) {
		this.authService = authService;
	}
	protected User createUser(HttpServletRequest request) {
		String username = request.getParameter(ATTR_USERNAME);
		String password = request.getParameter(ATTR_PASSWORD);
		User user = null;
		if (username == null) {
			HttpSession session = request.getSession(false);
			if (session != null)
				user = (User) session.getAttribute(SESSION_ATTRIBUTE_USER);
		}
		else {
			user = authenticate(username, password);
		}
		if (user == null)
			return super.createUser(request);
		else {
			HttpSession session = request.getSession(true);
			session.setAttribute(SESSION_ATTRIBUTE_USER, user);
			return user;
		}
	}
	protected User authenticate(String username, String password) {
		if (authService != null)
			return authService.authenticate(username, password);
		return null;
	}
}
