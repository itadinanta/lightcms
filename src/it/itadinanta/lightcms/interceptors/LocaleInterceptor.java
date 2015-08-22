package it.itadinanta.lightcms.interceptors;

import it.itadinanta.lightcms.toolkit.dictionary.Dictionary;
import it.itadinanta.lightcms.toolkit.dictionary.LocaleFilter;
import java.util.Locale;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

public class LocaleInterceptor extends LocaleChangeInterceptor implements LocaleFilter {
	private Dictionary dictionary;
	private Locale defaultLocale;
	private Set<Locale> allowedLocales;

	public void setDictionary(Dictionary dictionary) {
		this.dictionary = dictionary;
	}
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
		boolean retValue = super.preHandle(request, response, handler);
		if (retValue && dictionary != null && defaultLocale != null) {
			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			Locale locale = RequestContextUtils.getLocale(request);
			if (allowedLocales == null || !allowedLocales.contains(locale))
				localeResolver.setLocale(request, response, defaultLocale);
		}
		return retValue;
	}
	public void setDefaultLocale(Locale defaultLocale) {
		this.defaultLocale = defaultLocale;
	}
	public void setAllowedLocales(Set<Locale> allowedLocales) {
		this.allowedLocales = allowedLocales;
	}
	public Set<Locale> getAllowedLocales() {
		return allowedLocales;
	}
	public Locale getDefaultLocale() {
		return defaultLocale;
	}
}
