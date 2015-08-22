package it.itadinanta.lightcms.toolkit.dictionary;

import it.itadinanta.lightcms.toolkit.XmlLoader;
import java.util.List;
import java.util.Locale;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

public class XmlMessageSource extends XmlLoader implements MessageSource {
	private static final String ATTR_LANGUAGE = "language";
	private static final String ELEMENT_TEXT = "text";
	private static final String ATTR_KEY = "key";
	private static final String ELEMENT_ENTRY = "entry";
	private KeyValueMessageSource delegate = new KeyValueMessageSource();
	static private final Logger LOG = Logger.getLogger(XmlMessageSource.class);

	@SuppressWarnings("unchecked")
	public void initXml(Document xml) throws Exception {
		Element root = xml.getRootElement();
		List<Element> entries = root.elements(ELEMENT_ENTRY);
		for (Element entry : entries) {
			String key = entry.attributeValue(ATTR_KEY);
			if (key == null) {
				LOG.error("'key' attribute required for <entry> element");
			} else {
				for (Element text : (List<Element>) entry.elements(ELEMENT_TEXT)) {
					String language = text.attributeValue(ATTR_LANGUAGE);
					String value = text.getText();
					if (language != null && value != null) {
						delegate.addKey(key, new Locale(language), value);
					} else {
						LOG.error("Both 'language' and 'value' attributes required for <text> element at key " + key);
					}
				}
			}
		}
	}
	public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
		return delegate.getMessage(code, args, defaultMessage, locale);
	}
	public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
		return delegate.getMessage(code, args, locale);
	}
	public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
		return delegate.getMessage(resolvable, locale);
	}
}
