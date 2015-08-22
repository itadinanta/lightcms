package it.itadinanta;

import it.itadinanta.changemonitor.ResourceChange;
import it.itadinanta.changemonitor.ResourceChangeEvent;
import it.itadinanta.changemonitor.ResourceChangeListener;
import it.itadinanta.changemonitor.ResourceChangeManager;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import junit.framework.TestCase;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class ChangeMonitorTest extends TestCase implements ResourceChangeListener {
	private static final int MODIFY_PAUSE = 1000;
	private static final int POLL_INTERVAL = 2000;
	private ResourceChangeManager manager;
	private ResourceChangeEvent event;
	private Resource resource = new FileSystemResource("test/changedfile.txt");
	private void deleteTestResource() throws IOException {
		resource.getFile().delete();
	}
	private void createTestResource() throws IOException {
		changeTestResource();
	}
	private void changeTestResource() throws IOException {
		OutputStream os = new FileOutputStream(resource.getFile());
		os.write(Long.toString(System.currentTimeMillis()).getBytes());
		os.close();
	}
	public void setUp() {
		manager = new ResourceChangeManager();
		manager.setInterval(POLL_INTERVAL);
		manager.start();
	}
	public void testCreate() throws Exception {
		deleteTestResource();
		manager.registerListener(resource, this);
		createTestResource();
		Thread.sleep(200);
		assertNotNull(event);
		assertEquals(ResourceChange.CREATE, event.getChange());
		manager.unregisterListener(resource, this);
	}
	public void testChange() throws Exception {
		createTestResource();
		manager.registerListener(resource, this);
		Thread.sleep(MODIFY_PAUSE);
		createTestResource();
		Thread.sleep(MODIFY_PAUSE);
		assertNotNull(event);
		assertEquals(ResourceChange.MODIFY, event.getChange());
		event = null;
		Thread.sleep(MODIFY_PAUSE);
		assertNull(event);
		manager.unregisterListener(resource, this);
	}
	public void testDelete() throws Exception {
		createTestResource();
		manager.registerListener(resource, this);
		deleteTestResource();
		Thread.sleep(MODIFY_PAUSE);
		assertNotNull(event);
		assertEquals(ResourceChange.DELETE, event.getChange());
		event = null;
		Thread.sleep(MODIFY_PAUSE);
		assertNull(event);
		manager.unregisterListener(resource, this);
	}
	public void testUnregister() throws Exception {
		deleteTestResource();
		manager.registerListener(resource, this);
		manager.unregisterListener(resource, this);
		createTestResource();
		Thread.sleep(200);
		assertNull(event);
	}
	public void tearDown() {
		manager.stop();
	}
	public void onResourceChange(ResourceChangeEvent event) {
		this.event = event;
	}
}
