package it.itadinanta.lightcms;

import org.springframework.web.servlet.DispatcherServlet;

public class CmsServlet extends DispatcherServlet {
	private static final long serialVersionUID = 3546361738548293941L;

	public CmsServlet() {
		super();
		setNamespace("module");
	}
}
