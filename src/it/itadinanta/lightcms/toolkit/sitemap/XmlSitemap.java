package it.itadinanta.lightcms.toolkit.sitemap;

import it.itadinanta.lightcms.toolkit.XmlLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class XmlSitemap extends XmlLoader implements Sitemap {
	private static final Logger LOG = Logger.getLogger(XmlSitemap.class);
	private static final String ELEMENT_ERROR = "error";
	private static final String ATTR_ATTRIBUTE = "attribute";
	private static final String ATTR_NAME = "name";
	private static final String ELEMENT_ENTRY = "node";
	private static final String ELEMENT_DEFAULTS = "defaults";
	private List<BaseSitemapNode> allEntries = new ArrayList<BaseSitemapNode>();
	private BaseSitemapNode homePage;
	private BaseSitemapNode lastNode;
	public HashMap<String, BaseSitemapNode> linkIndex = new HashMap<String, BaseSitemapNode>();
	public HashMap<String, BaseSitemapNode> nameIndex = new LinkedHashMap<String, BaseSitemapNode>();
	private BeanWrapper wrapper = new BeanWrapperImpl();
	private BaseSitemapNode defaultNode;
	private BaseSitemapNode errorNode;
	private Navigator createNavigator(BaseSitemapNode entry) {
		if (entry == null)
			return null;
		return new LinkedNavigator(entry);
	}
	public Navigator findNodeByName(String name) {
		if (LOG.isDebugEnabled())
			LOG.debug("Finding node by name '" + name + "'");
		return createNavigator(nameIndex.get(name));
	}
	public Navigator findNodeByPath(String path) {
		if (LOG.isDebugEnabled())
			LOG.debug("Find node by path '" + path + "'");
		return createNavigator(linkIndex.get(path));
	}
	public Navigator getNode(int id) {
		return createNavigator(allEntries.get(id));
	}
	public Navigator getHome() {
		return createNavigator(homePage);
	}
	public Collection getNodes() {
		return allEntries;
	}
	protected BaseSitemapNode createNode(int id, BaseSitemapNode prototype) {
		if (prototype == null)
			prototype = defaultNode;
		return prototype != null ? prototype.newInstance(id) : new BaseSitemapNode(id);
	}
	public void initXml(Document xml) throws Exception {
		clear();
		Element root = xml.getRootElement();
		defaultNode = parseSpecialNode(root.element(ELEMENT_DEFAULTS), ELEMENT_DEFAULTS);
		errorNode = parseSpecialNode(root.element(ELEMENT_ERROR), ELEMENT_ERROR);
		Element home = root.element(ELEMENT_ENTRY);
		homePage = parseEntry(home, null);
	}
	private void clear() {
		allEntries.clear();
		linkIndex.clear();
		nameIndex.clear();
		homePage = null;
		lastNode = null;
		defaultNode = null;
		errorNode = null;
	}
	private BaseSitemapNode parseSpecialNode(Element nodeElement, String nodeName) {
		BaseSitemapNode specialNode = createNode(-1, null);
		if (nodeElement != null) {
			wrapper.setWrappedInstance(specialNode);
			parseElement(wrapper, nodeElement);
		}
		else
			LOG.warn("Special node " + nodeName + " not defined");
		return specialNode;
	}
	@SuppressWarnings("unchecked")
	public BaseSitemapNode parseEntry(Element entryElement, BaseSitemapNode parent) throws Exception {
		BaseSitemapNode entry = createNode(allEntries.size(), parent);
		wrapper.setWrappedInstance(entry);
		parseElement(wrapper, entryElement);
		for (Element attribute : (List<Element>) entryElement.elements(ATTR_ATTRIBUTE)) {
			String key = attribute.attributeValue(ATTR_NAME);
			String value = attribute.getTextTrim();
			entry.setAttribute(key, value);
		}
		registerNode(entry, parent);
		LOG.debug("Node loaded: " + entry.toString());
		for (Element childElement : (List<Element>) entryElement.elements(ELEMENT_ENTRY))
			parseEntry(childElement, entry);
		return entry;
	}
	private void registerNode(BaseSitemapNode entry, BaseSitemapNode parent) {
		if (parent != null)
			parent.addChild(entry);
		allEntries.add(entry);
		nameIndex.put(entry.getName(), entry);
		linkIndex.put(entry.getUrl(), entry);
		lastNode = entry.appendTo(lastNode);
	}
	public Navigator getDefaultNode() {
		return createNavigator(defaultNode);
	}
	public Navigator getErrorNode() {
		return createNavigator(errorNode);
	}
}
