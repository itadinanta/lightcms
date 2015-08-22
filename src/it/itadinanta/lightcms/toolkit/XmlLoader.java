package it.itadinanta.lightcms.toolkit;

import java.io.InputStream;
import java.util.List;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.BeanWrapper;

public abstract class XmlLoader extends Loader implements XmlConfigurator {
	@Override
	public void loadConfig(InputStream inputStream) throws Exception {
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		initXml(document);
	}
	@SuppressWarnings("unchecked")
	public Object parseElement(BeanWrapper item, Element element) {
		for (Attribute attribute : (List<Attribute>) element.attributes()) {
			item.setPropertyValue(attribute.getName(), attribute.getValue());
		}
		return item.getWrappedInstance();
	}
}
