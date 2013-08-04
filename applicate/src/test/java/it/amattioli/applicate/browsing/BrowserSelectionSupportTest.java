package it.amattioli.applicate.browsing;

import it.amattioli.applicate.selection.MockSelectionListener;
import it.amattioli.applicate.selection.SelectionEvent;
import it.amattioli.applicate.selection.SelectionSupport;
import it.amattioli.dominate.Entity;
import junit.framework.TestCase;

public class BrowserSelectionSupportTest extends TestCase {

	public void testNotifySelection() {
		ListBrowser brw = new ListBrowserImpl<Long, Entity<Long>>();
		SelectionSupport support = new SelectionSupport();
		MockSelectionListener listener = new MockSelectionListener();
		support.addSelectionListener(listener);
		support.notifySelectionListeners(new SelectionEvent(brw));
		assertTrue(listener.isSelectionNotified());
	}
	
}
