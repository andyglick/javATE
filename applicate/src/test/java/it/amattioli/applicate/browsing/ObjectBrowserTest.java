package it.amattioli.applicate.browsing;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.beanutils.PropertyUtils;

import it.amattioli.dominate.Repository;
import it.amattioli.dominate.memory.MemoryRepository;
import it.amattioli.applicate.properties.MockPropertyChangeListener;
import junit.framework.TestCase;

public class ObjectBrowserTest extends TestCase {

	private Repository<Long, EntityStub> createRepository() {
		Repository<Long, EntityStub> rep = new MemoryRepository<Long, EntityStub>();
		EntityStub e1 = new EntityStub();
		e1.setId(1L);
		e1.setProperty1("value11");
		e1.setProperty2("value12");
		rep.put(e1);
		EntityStub e2 = new EntityStub();
		e2.setId(2L);
		e2.setProperty1("value21");
		e2.setProperty2("value22");
		Collection<DetailEntityStub> details = new ArrayList<DetailEntityStub>();
		DetailEntityStub de1 = new DetailEntityStub();
		details.add(de1);
		e2.setDetails(details);
		rep.put(e2);
		return rep;
	}
	
	private ObjectBrowserImpl<Long, EntityStub> createObjectBrowser() {
		Repository<Long, EntityStub> rep = createRepository();
		ObjectBrowserImpl<Long, EntityStub> result = new ObjectBrowserImpl<Long, EntityStub>(1L, rep);
		return result;
	}
	
	public void testHold() {
		ObjectBrowser<Long, EntityStub> brw = createObjectBrowser();
		EntityStub hold = brw.getHold();
		assertEquals(new Long(1L), hold.getId());
	}
	
	public void testRefresh() {
		boolean propertyChangeNotified = false;
		ObjectBrowser<Long, EntityStub> brw = createObjectBrowser();
		MockPropertyChangeListener listener = new MockPropertyChangeListener("hold");
		brw.addPropertyChangeListener(listener);
		MockContentChangeListener contentListener = new MockContentChangeListener();
        brw.addContentChangeListener(contentListener);
		brw.refresh();
		assertTrue(listener.isPropertyChangeNotified());
		assertTrue(contentListener.isNotified());
	}
	
	public void testNotificationWhenHoldSet() {
		boolean propertyChangeNotified = false;
		ObjectBrowserImpl<Long, EntityStub> brw = createObjectBrowser();
		MockPropertyChangeListener listener = new MockPropertyChangeListener("hold");
		brw.addPropertyChangeListener(listener);
		MockContentChangeListener contentListener = new MockContentChangeListener();
        brw.addContentChangeListener(contentListener);
        brw.setHoldId(2L);
        assertTrue(listener.isPropertyChangeNotified());
		assertTrue(contentListener.isNotified());
	}
	
	public void testAsDynaBean() throws Exception {
		ObjectBrowser<Long, EntityStub> brw = createObjectBrowser();
		String property1Value = (String)PropertyUtils.getProperty(brw, "property1");
		assertEquals("value11", property1Value);
	}
	
	public void testDetailsBrowser() throws Exception {
		ListBrowser<Long, EntityStub> lbrw = new ListBrowserImpl<Long, EntityStub>(createRepository());
		lbrw.select(0);
		ObjectBrowser<Long, EntityStub> brw = lbrw.getSelectedObjectBrowser();
		ListBrowser<?, ?> dbrw = brw.getDetailsBrowser("details");
		assertTrue(dbrw.getList().isEmpty());
		lbrw.select(1);
		assertFalse(dbrw.getList().isEmpty());
	}

}
