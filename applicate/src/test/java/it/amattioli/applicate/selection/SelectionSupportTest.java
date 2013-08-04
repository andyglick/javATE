package it.amattioli.applicate.selection;

import junit.framework.TestCase;

public class SelectionSupportTest extends TestCase {
	
	public void testEnabledAfterConstruction() {
		SelectionSupport s = new SelectionSupport();
		assertTrue(s.isEnabled());
	}
	
	public void testReceiveNotificationByDefault() {
		SelectionSupport s = new SelectionSupport();
		MockSelectionListener listener = new MockSelectionListener();
		s.addSelectionListener(listener);
		
		s.notifySelectionListeners(new SelectionEvent(new SelectorStub()));
		
		assertEquals(1, listener.getNotificationsNumber());
	}
	
	public void testDoesNotReceiveNotificationIfDisabled() {
		SelectionSupport s = new SelectionSupport();
		MockSelectionListener listener = new MockSelectionListener();
		s.addSelectionListener(listener);
		
		s.disable();
		s.notifySelectionListeners(new SelectionEvent(new SelectorStub()));
		
		assertEquals(0, listener.getNotificationsNumber());
	}
	
	public void testDoesNotReceiveNotificationIfRemoved() {
		SelectionSupport s = new SelectionSupport();
		MockSelectionListener listener = new MockSelectionListener();
		s.addSelectionListener(listener);
		
		s.notifySelectionListeners(new SelectionEvent(new SelectorStub()));
		s.removeSelectionListener(listener);
		s.notifySelectionListeners(new SelectionEvent(new SelectorStub()));
		
		assertEquals(1, listener.getNotificationsNumber());
	}
	
	public void testReceiveNotificationIfReenabled() {
		SelectionSupport s = new SelectionSupport();
		MockSelectionListener listener = new MockSelectionListener();
		s.addSelectionListener(listener);
		
		s.disable();
		s.notifySelectionListeners(new SelectionEvent(new SelectorStub()));
		s.enable();
		s.notifySelectionListeners(new SelectionEvent(new SelectorStub()));
		
		assertEquals(1, listener.getNotificationsNumber());
	}
	
}
