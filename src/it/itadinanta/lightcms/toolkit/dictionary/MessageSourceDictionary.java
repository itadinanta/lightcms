package it.itadinanta.lightcms.toolkit.dictionary;

import java.util.Locale;
import org.springframework.context.MessageSource;

public class MessageSourceDictionary implements Dictionary {
	private MessageSource source;

	public void setMessageSource(MessageSource source) {
		this.source = source;
	}

	private class LocaleLanguage implements Language {
		private MessageSource source;
		private Locale locale;

		public String getName() {
			return locale.getLanguage();
		}
		public String toString() {
			return locale.getLanguage();
		}
		LocaleLanguage(MessageSource source, Locale locale) {
			this.source = source;
			this.locale = locale;
		}
		public String translate(String key) {
			return source.getMessage(key, new Object[0], locale);
		}
	}

	public Language getLanguage(String language) {
		return new LocaleLanguage(source, new Locale(language));
	}
}
