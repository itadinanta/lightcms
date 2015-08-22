package it.itadinanta.lightcms.commands;

import it.itadinanta.lightcms.auth.User;
import it.itadinanta.lightcms.toolkit.Toolkit;
import it.itadinanta.lightcms.toolkit.dictionary.Language;
import it.itadinanta.lightcms.toolkit.dictionary.LanguageMapWrapper;
import it.itadinanta.lightcms.toolkit.sitemap.ClearanceFilteredNavigator;
import it.itadinanta.lightcms.toolkit.sitemap.Navigator;
import it.itadinanta.lightcms.toolkit.sitemap.NavigatorForwardIterator;
import it.itadinanta.lightcms.toolkit.sitemap.NavigatorSiblingIterator;
import it.itadinanta.lightcms.toolkit.sitemap.SitemapNode;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class BaseCommand {
	private static final String METHOD_PREFIX = "do";
	private static final Class[] EMPTY_ARG_LIST = new Class[] {};
	private static final Object[] EMPTY_ARGS_LIST = new Object[] {};
	private static final Logger LOG = Logger.getLogger(BaseCommand.class);
	private String body;
	private HashMap<String, Object> context;
	private Navigator navigator;
	private HttpServletRequest request;
	private Language language;
	private Locale locale;
	private String title;
	private Toolkit toolkit;
	private String view;
	private User user;
	public BaseCommand() {
		this.context = new HashMap<String, Object>();
	}
	protected void afterMethod() {
	}
	protected String autoResolveMethod() throws Exception {
		String requestURI = request.getRequestURI();
		StringBuilder builder = new StringBuilder(METHOD_PREFIX);
		final int initialLength = builder.length();
		CharacterIterator ci = new StringCharacterIterator(requestURI);
		char c;
		boolean STATE_UPPER = true;
		boolean STATE_IGNORE = false;
		for (c = ci.first(); c != CharacterIterator.DONE; c = ci.next()) {
			switch (c) {
			case '/':
				builder.setLength(initialLength);
				STATE_IGNORE = false;
				STATE_UPPER = true;
				break;
			case '_':
				STATE_UPPER = true;
				break;
			default:
				if (STATE_IGNORE)
					break;
				if (STATE_UPPER)
					c = Character.toUpperCase(c);
				builder.append(c);
				STATE_UPPER = false;
				break;
			case '.':
				STATE_IGNORE = true;
				break;
			}
		}
		return builder.toString();
	}
	protected void beforeMethod() {
	}
	protected void close() {
	}
	public final void execute() {
		init();
		try {
			beforeMethod();
			if (user.getClearance() < navigator.current().getClearance()) {
				executeLogin();
			}
			else {
				String methodName = navigator.current().getMethod();
				if (methodName == null || methodName.equals("$"))
					methodName = autoResolveMethod();
				Method method = findMethod(methodName);
				if (method != null)
					executeMethod(method);
				else
					executeDefault();
			}
			initExports();
		}
		catch (Exception e) {
			LOG.error("Error", e);
		}
		finally {
			afterMethod();
			close();
		}
	}
	protected void executeLogin() throws Exception {
		useBody("login");
	}
	protected void executeDefault() throws Exception {
	}
	public void doDefault() {
	}
	protected void executeMethod(Method method) throws Exception {
		if (method == null)
			return;
		try {
			method.invoke(this, EMPTY_ARGS_LIST);
		}
		catch (InvocationTargetException ex) {
			if (ex.getCause() instanceof Exception)
				throw (Exception) ex.getCause();
		}
	}
	protected void export(String key, Object value) {
		if (key == null)
			return;
		if (value == null)
			context.remove(key);
		else
			context.put(key, value);
	}
	protected Method findMethod(String methodName) {
		try {
			return this.getClass().getMethod(methodName, EMPTY_ARG_LIST);
		}
		catch (Exception ex) {
			LOG.warn("Method not found: " + ex);
			return null;
		}
	}
	public String getBody() {
		return body != null ? body : navigator.current().getBody();
	}
	public String getBodyTranslated() {
		return getBody().replace("%l", getLocale().getLanguage());
	}
	public String getCharacterEncoding() {
		return request.getCharacterEncoding();
	}
	public int getContentLength() {
		return request.getContentLength();
	}
	public String getContentType() {
		return request.getContentType();
	}
	public Map getContext() {
		return context;
	}
	public String getContextPath() {
		return request.getContextPath();
	}
	public Navigator getNavigator() {
		return navigator;
	}
	public SitemapNode getSitemapNode() {
		return navigator.current();
	}
	public String getRemoteAddr() {
		return request.getRemoteAddr();
	}
	public String getRemoteHost() {
		return request.getRemoteHost();
	}
	public int getRemotePort() {
		return request.getRemotePort();
	}
	public String getRemoteUser() {
		return request.getRemoteUser();
	}
	public String getRequestURI() {
		return request.getRequestURI();
	}
	public StringBuffer getRequestURL() {
		return request.getRequestURL();
	}
	public String getScheme() {
		return request.getScheme();
	}
	public String getServerName() {
		return request.getServerName();
	}
	public int getServerPort() {
		return request.getServerPort();
	}
	public String getServletPath() {
		return request.getServletPath();
	}
	public String getTitle() {
		return title != null ? title : navigator.current().getTitle();
	}
	public String getView() {
		return view != null ? view : navigator.current().getView();
	}
	protected void init() {
		export("navigator", navigator);
		export("toolkit", toolkit);
		export("user", user);
		export("language", language);
		export("locale", locale);
		export("MSG", new LanguageMapWrapper(language));
	}
	public Language getLanguage() {
		return language;
	}
	public void initContext(HashMap<String, Object> context) {
		this.context = context;
	}
	public void initUser(User user) {
		this.user = user;
	}
	protected void initExports() {
		export("cmd", this);
	}
	public void initNavigator(Navigator node, Navigator home) {
		this.navigator = node;
	}
	public void initRequest(HttpServletRequest request) {
		this.request = request;
	}
	public void initToolkit(Toolkit toolkit) {
		this.toolkit = toolkit;
	}
	protected void useBody(String body) {
		this.body = body;
	}
	protected void useTitle(String title) {
		this.title = title;
	}
	protected void useView(String view) {
		this.view = view;
	}
	protected User getUser() {
		return this.user;
	}
	protected Navigator createFilter(Navigator target) {
		return new ClearanceFilteredNavigator(user, target);
	}
	public SitemapNode getNavprev() {
		return createFilter(navigator.newInstance()).prev();
	}
	public SitemapNode getNavnext() {
		return createFilter(navigator.newInstance()).next();
	}
	public SitemapNode getNavparent() {
		return navigator.current().getParent();
	}
	public Iterator<SitemapNode> getNavtree() {
		return new NavigatorForwardIterator(createFilter(toolkit.getSitemap().getHome()));
	}
	public Iterator<SitemapNode> getNavchildren() {
		Navigator children=navigator.newInstance();
		children.firstChild();
		return new NavigatorSiblingIterator(createFilter(children));
	}
	public Iterator<SitemapNode> getNavcrumbs() {
		List<SitemapNode> path = new LinkedList<SitemapNode>();
		Navigator parent = navigator.newInstance();
		while (parent.parent() != null)
			path.add(parent.current());
		Collections.reverse(path);
		return path.iterator();
	}
	public Locale getLocale() {
		return locale;
	}
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}
}
