package it.itadinanta;

import java.util.Locale;
import org.springframework.core.io.FileSystemResource;
import it.itadinanta.lightcms.toolkit.dictionary.XmlMessageSource;
import junit.framework.TestCase;

public class DictionaryTest extends TestCase {
	private XmlMessageSource source;
	public void setUp() throws Exception {
		source = new XmlMessageSource();
		source.setConfigLocation(new FileSystemResource("test/dictionary.xml"));
		source.loadConfig();
	}
	public void testTranslate() {
		String italianMessage = source.getMessage("test1", null, Locale.ITALIAN);
		String englishMessage = source.getMessage("test1", null, Locale.ENGLISH);
		assertEquals("Messaggio di prova in italiano", italianMessage);
		assertEquals("English test message", englishMessage);
	}
	public void testTranslateDefault() {
		String italianMessage = source.getMessage("unknown", null, "Messaggio di default", Locale.ITALIAN);
		String englishMessage = source.getMessage("unknown", null, "Default message", Locale.ENGLISH);
		assertEquals("Messaggio di default", italianMessage);
		assertEquals("Default message", englishMessage);
	}
	public void testFormat() {
		Object[] args = new Object[] { 10 };
		String italianMessage = source.getMessage("format1", args, Locale.ITALIAN);
		String englishMessage = source.getMessage("format1", args, Locale.ENGLISH);
		assertEquals("Messaggio formattato in italiano valore=10", italianMessage);
		assertEquals("English formatted message value=10", englishMessage);
	}
}