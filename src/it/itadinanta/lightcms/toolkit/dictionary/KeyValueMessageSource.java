/**
 * KeyValueMessageSource.java
 *
 * Created on 18-Mar-2006
 * 
 * (C) 2006 Nicola Orru' - all rights reserved
 */
package it.itadinanta.lightcms.toolkit.dictionary;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import org.springframework.context.support.AbstractMessageSource;

class KeyValueMessageSource extends AbstractMessageSource {
	class LanguageTable {
		private HashMap<String, MessageFormat> formats = new HashMap<String, MessageFormat>();
		private HashMap<String, String> strings = new HashMap<String, String>();
		private Locale locale;

		public LanguageTable(Locale locale) {
			this.locale = locale;
		}
		public void addTranslation(String key, String text) {
			if (key != null && text != null) {
				formats.put(key, new MessageFormat(text, locale));
				strings.put(key, text);
			}
		}
		public MessageFormat getFormat(String key) {
			return formats.get(key);
		}
		public String getString(String code) {
			return strings.get(code);
		}
	}

	private HashMap<Locale, LanguageTable> languages = new HashMap<Locale, LanguageTable>();

	KeyValueMessageSource() {
	}
	protected void addKey(String key, Locale locale, String value) {
		LanguageTable languageTable = languages.get(locale);
		if (languageTable == null) {
			languageTable = new LanguageTable(locale);
			languages.put(locale, languageTable);
		}
		languageTable.addTranslation(key, value);
	}
	@Override
	protected MessageFormat resolveCode(String code, Locale locale) {
		LanguageTable language = languages.get(locale);
		if (language == null)
			return null;
		return language.getFormat(code);
	}
	@Override
	protected String resolveCodeWithoutArguments(String code, Locale locale) {
		LanguageTable language = languages.get(locale);
		if (language == null)
			return null;
		return language.getString(code);
	}
}