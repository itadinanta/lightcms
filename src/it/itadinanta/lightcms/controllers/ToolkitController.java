package it.itadinanta.lightcms.controllers;

import java.util.Locale;
import it.itadinanta.lightcms.auth.User;
import it.itadinanta.lightcms.auth.UserBean;
import it.itadinanta.lightcms.commands.BaseCommand;
import it.itadinanta.lightcms.toolkit.Toolkit;
import it.itadinanta.lightcms.toolkit.dictionary.Language;
import it.itadinanta.lightcms.toolkit.sitemap.Navigator;
import it.itadinanta.lightcms.toolkit.sitemap.Sitemap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;
import org.springframework.web.servlet.support.RequestContextUtils;

public class ToolkitController extends AbstractCommandController {
	static private final Logger LOG = Logger.getLogger(ToolkitController.class);
	protected Toolkit toolkit;
	public ToolkitController() {
	}
	public void setToolkit(Toolkit toolkit) {
		this.toolkit = toolkit;
	}
	protected Navigator findCurrentNode(HttpServletRequest request) {
		Sitemap sitemap = toolkit.getSitemap();
		if (LOG.isDebugEnabled()) {
			LOG.debug("Looking for path: " + request.getServletPath());
			LOG.debug("Request path: " + request.getRequestURI());
			LOG.debug("Path info: " + request.getPathInfo());
		}
		Navigator currentNode = sitemap.findNodeByPath(request.getPathInfo());
		if (currentNode == null)
			currentNode = sitemap.getErrorNode();
		return currentNode;
	}
	@Override
	protected Object getCommand(HttpServletRequest request) throws Exception {
		Navigator currentNode = findCurrentNode(request);
		Navigator homeNode = toolkit.getSitemap().getHome();
		BaseCommand command = (BaseCommand) BeanUtils.instantiateClass(currentNode.current().getHandler());
		Locale locale = RequestContextUtils.getLocale(request);
		Language language = toolkit.getDictionary().getLanguage(locale.getLanguage());
		command.setLocale(locale);
		command.setLanguage(language);
		command.initNavigator(currentNode, homeNode);
		command.initUser(createUser(request));
		command.initToolkit(toolkit);
		command.initRequest(request);
		return command;
	}
	protected User createUser(HttpServletRequest request) {
		return new UserBean(-1, "Guest", 0);
	}
	@Override
	protected ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object commandObject,
			BindException exception) throws Exception {
		BaseCommand command = (BaseCommand) commandObject;
		command.execute();
		String view = command.getView();
		if (view == null)
			view = request.getPathInfo();
		return new ModelAndView(view, command.getContext());
	}
}
