package it.itadinanta.lightcms;

import java.util.List;
import org.mortbay.http.HttpContext;
import org.mortbay.http.HttpListener;
import org.mortbay.http.handler.ResourceHandler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.ServletHandler;

public class JettyCmsServer implements CmsServer {
	private String modulesPath = "modules";
	private List<HttpListener> listeners;
	private List<String> modules;
	private Server server;
	private String servletClass;
	private String servletMapping;
	private String resourcePath = "/web/resources";
	private String resourceContextSuffix = "_resources";

	public JettyCmsServer() {
	}
	public void setModulesPath(String modulesPath) {
		this.modulesPath = modulesPath;
	}
	public void setListeners(List<HttpListener> listeners) {
		this.listeners = listeners;
	}
	public void setServer(Server server) {
		this.server = server;
	}
	public void setServletClass(String servletClass) {
		this.servletClass = servletClass;
	}
	public void setServletMapping(String servletMapping) {
		this.servletMapping = servletMapping;
	}
	public void start() throws Exception {
		for (Object l : listeners)
			if (l instanceof HttpListener)
				server.addListener((HttpListener) l);
		HttpContext context;
		for (String module : modules) {
			// static resources
			ResourceHandler resource = new ResourceHandler();
			resource.setAllowedMethods(new String[] { "GET" });
			context = new HttpContext();
			context.setContextPath(module + resourceContextSuffix);
			context.setResourceBase(modulesPath + "/" + module + resourcePath);
			context.addHandler(resource);
			server.addContext(context);
			// dynamic resources
			ServletHandler servlets = new ServletHandler();
			servlets.addServlet(module, servletMapping, servletClass);
			context = new HttpContext();
			context.setContextPath(module);
			context.setResourceBase(modulesPath + "/" + module);
			context.addHandler(servlets);
			server.addContext(context);
		}
		server.start();
	}
	public void setModules(List<String> modules) {
		this.modules = modules;
	}
	public void stop() throws Exception {
		server.stop(true);
	}
}