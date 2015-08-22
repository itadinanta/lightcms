package it.itadinanta;

import java.util.Iterator;
import org.springframework.core.io.FileSystemResource;
import it.itadinanta.lightcms.commands.BaseCommand;
import it.itadinanta.lightcms.toolkit.sitemap.BaseSitemapNode;
import it.itadinanta.lightcms.toolkit.sitemap.FilteredNavigator;
import it.itadinanta.lightcms.toolkit.sitemap.Navigator;
import it.itadinanta.lightcms.toolkit.sitemap.NavigatorForwardIterator;
import it.itadinanta.lightcms.toolkit.sitemap.NavigatorParentIterator;
import it.itadinanta.lightcms.toolkit.sitemap.SitemapNavigator;
import it.itadinanta.lightcms.toolkit.sitemap.SitemapNode;
import it.itadinanta.lightcms.toolkit.sitemap.XmlSitemap;
import junit.framework.TestCase;

public class SitemapTest extends TestCase {
	BaseSitemapNode root;
	public void setUp() {
		root = new BaseSitemapNode(0);
		root.addChild(new BaseSitemapNode(1));
		root.addChild(new BaseSitemapNode(2)).addChild(new BaseSitemapNode(3));
		root.addChild(new BaseSitemapNode(4)).addChild(new BaseSitemapNode(5));
	}
	public void testSitemapNode() {
		assertEquals(root.getId(), 0);
		assertEquals(3, root.getChildren().size());
	}
	public void testProperties() {
		root.setClearance(10);
		root.setEnabled(true);
		root.setHandler(BaseCommand.class);
		root.setName("name");
		root.setUrl("/");
		assertEquals(10, root.getClearance());
		assertEquals(true, root.isEnabled());
		assertEquals(BaseCommand.class, root.getHandler());
		assertEquals("name", root.getName());
		assertEquals("/", root.getUrl());
	}
	public void testSiblings() {
		SitemapNode one = root.getFirstChild();
		SitemapNode two = one.getNextSibling();
		SitemapNode four = root.getLastChild();
		assertNotNull(one);
		assertNotNull(two);
		assertNotNull(four);
		assertNotNull(four.getPrevSibling());
		assertEquals(1, one.getId());
		assertEquals(2, two.getId());
		assertEquals(4, four.getId());
		assertEquals(0, root.getLevel());
		assertEquals(1, two.getLevel());
		assertEquals(1, four.getLevel());
		assertSame(two, one.getNextSibling());
		assertSame(four, two.getNextSibling());
		assertSame(null, four.getNextSibling());
		assertSame(null, one.getPrevSibling());
		assertSame(one, two.getPrevSibling());
		assertSame(two, four.getPrevSibling());
	}
	public void testParent() {
		SitemapNode one = root.getFirstChild();
		SitemapNode two = one.getNextSibling();
		SitemapNode three = two.getFirstChild();
		assertEquals(1, one.getId());
		assertEquals(2, two.getId());
		assertEquals(3, three.getId());
		assertSame(null, root.getParent());
		assertSame(root, one.getParent());
		assertSame(two, three.getParent());
		assertEquals(0, root.getLevel());
		assertEquals(1, one.getLevel());
		assertEquals(1, two.getLevel());
		assertEquals(2, three.getLevel());
	}
	public void testNavigator() {
		Navigator forward = new SitemapNavigator(root);
		assertEquals(0, forward.current().getId());
		assertEquals(1, forward.next().getId());
		assertEquals(2, forward.next().getId());
		assertEquals(3, forward.next().getId());
		assertEquals(4, forward.next().getId());
		assertEquals(5, forward.next().getId());
		assertNull(forward.next());
		Navigator backwards = new SitemapNavigator(root.getFirstChild().getNextSibling().getNextSibling().getFirstChild());
		assertEquals(5, backwards.current().getId());
		assertEquals(4, backwards.prev().getId());
		assertEquals(3, backwards.prev().getId());
		assertEquals(2, backwards.prev().getId());
		assertEquals(1, backwards.prev().getId());
		assertEquals(0, backwards.prev().getId());
		assertNull(backwards.prev());
	}
	public void testXmlLoader() throws Exception {
		XmlSitemap sitemap = loadSitemap();
		for (Navigator node = sitemap.getHome(); node.current() != null; node.next()) {
			SitemapNode current = node.current();
			int l = current.getLevel();
			for (int i = 0; i < l; i++)
				System.out.print(" ");
			System.out.print(current.getName());
			System.out.println();
		}
		Navigator ptr = sitemap.findNodeByName("1.1.2");
		assertEquals("10", ptr.current().getAttribute("option"));
	}
	public void testIterator() throws Exception {
		XmlSitemap sitemap = loadSitemap();
		Iterator<SitemapNode> iterator = new NavigatorForwardIterator(sitemap.getHome());
		Navigator reference = sitemap.getHome();
		while (iterator.hasNext()) {
			SitemapNode current = iterator.next();
			assertEquals(current, reference.current());
			reference.next();
			int l = current.getLevel();
			for (int i = 0; i < l; i++)
				System.out.print(" ");
			System.out.print(current.getName());
			System.out.println();
		}
		Iterator<SitemapNode> parents = new NavigatorParentIterator(sitemap.findNodeByName("1.1.3"));
		assertEquals(sitemap.findNodeByName("1.1.3").current(), parents.next());
		assertEquals(sitemap.findNodeByName("1.1").current(), parents.next());
		assertEquals(sitemap.findNodeByName("1").current(), parents.next());
		assertTrue(!parents.hasNext());
	}
	public void testFilter() throws Exception {
		XmlSitemap sitemap = loadSitemap();
		Navigator target = sitemap.getHome();
		Navigator node = new FilteredNavigator(target) {
			protected boolean accept(SitemapNode node) {
				return node.getLevel() < 2;
			}
		};
		for (; node.current() != null; node.next()) {
			SitemapNode current = node.current();
			int l = current.getLevel();
			assertTrue(l < 2);
			for (int i = 0; i < l; i++)
				System.out.print(" ");
			System.out.print(current.getName());
			System.out.println();
		}
	}
	public void testSubTree() throws Exception {
		XmlSitemap sitemap = loadSitemap();
		Navigator node = sitemap.findNodeByName("1.1");
		SitemapNode last = node.current().getNextSibling();
		for (; node.current() != last; node.next()) {
			SitemapNode current = node.current();
			int l = current.getLevel();
			assertTrue(l > 0);
			for (int i = 0; i < l; i++)
				System.out.print(" ");
			System.out.print(current.getName());
			System.out.println();
		}
	}
	private XmlSitemap loadSitemap() throws Exception {
		XmlSitemap sitemap = new XmlSitemap();
		sitemap.setConfigLocation(new FileSystemResource("test/sitemap.xml"));
		sitemap.loadConfig();
		return sitemap;
	}
}
