package it.itadinanta.lightcms.toolkit.dictionary;

import java.util.Locale;
import java.util.Set;

public interface LocaleFilter {
	public Locale getDefaultLocale();
	public Set<Locale> getAllowedLocales();
}
