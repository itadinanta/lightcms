package it.itadinanta.lightcms.toolkit.sitemap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;

public class BaseSitemapNode implements SitemapNode, Cloneable {
	private static final Logger LOG = Logger.getLogger(BaseSitemapNode.class);
	private String body;
	private List<BaseSitemapNode> children;
	private HashMap<String, String> attributes;
	private int clearance;
	private boolean enabled;
	private Class handler;
	private int id;
	private int level;
	private String method;
	private String name;
	private BaseSitemapNode next;
	private BaseSitemapNode nextSibling;
	private BaseSitemapNode parent;
	private BaseSitemapNode prev;
	private BaseSitemapNode prevSibling;
	private Sitemap sitemap;
	private String view;
	private BaseSitemapNode tail;
	private String title;
	private String url;
	public BaseSitemapNode(int id) {
		this.id = id;
	}
	public BaseSitemapNode addChild(BaseSitemapNode child) {
		child.parent = this;
		child.level = this.level + 1;
		if (children == null)
			children = new ArrayList<BaseSitemapNode>();
		child.prevSibling = tail;
		if (tail != null)
			tail.nextSibling = child;
		tail = child;
		children.add(child);
		return child;
	}
	public BaseSitemapNode appendTo(BaseSitemapNode prev) {
		this.prev = prev;
		if (prev != null)
			prev.next = this;
		return this;
	}
	public String getBody() {
		return body;
	}
	public List getChildren() {
		return children;
	}
	public int getClearance() {
		return clearance;
	}
	public SitemapNode getFirstChild() {
		if (children == null || children.isEmpty())
			return null;
		return children.get(0);
	}
	public Class getHandler() {
		return handler;
	}
	public int getId() {
		return id;
	}
	public SitemapNode getLastChild() {
		if (children == null || children.isEmpty())
			return null;
		return children.get(children.size() - 1);
	}
	public int getLevel() {
		return level;
	}
	public String getMethod() {
		return method;
	}
	public String getName() {
		return name;
	}
	public SitemapNode getNext() {
		return next;
	}
	public BaseSitemapNode getNextSibling() {
		return nextSibling;
	}
	public SitemapNode getParent() {
		return parent;
	}
	public SitemapNode getPrev() {
		return prev;
	}
	public BaseSitemapNode getPrevSibling() {
		return prevSibling;
	}
	public Sitemap getSitemap() {
		return sitemap;
	}
	public String getView() {
		return view;
	}
	public String getTitle() {
		return title;
	}
	public String getUrl() {
		return url;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public void setClearance(int clearance) {
		this.clearance = clearance;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public void setHandler(Class handler) {
		this.handler = handler;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setView(String skeleton) {
		this.view = skeleton;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAttribute(String key) {
		return attributes == null ? null : attributes.get(key);
	}
	public void setAttribute(String key, String value) {
		if (key == null || value == null)
			return;
		if (attributes == null)
			attributes = new HashMap<String, String>();
		attributes.put(key, value);
	}
	public BaseSitemapNode newInstance(int id) {
		BaseSitemapNode instance;
		try {
			instance = (BaseSitemapNode) super.clone();
			instance.id = id;
			instance.clearLinks();
			return instance;
		}
		catch (CloneNotSupportedException e) {
			LOG.error("Exception thrown ", e);
			return null;
		}
	}
	private void clearLinks() {
		next = null;
		prev = null;
		nextSibling = null;
		prevSibling = null;
		parent = null;
		children = null;
	}
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("name=").append(name).append("; ");
		buf.append("id=").append(id).append("; ");
		buf.append("url=").append(url).append("; ");
		buf.append("handler=").append(handler).append("; ");
		return buf.toString();
	}
}
