package it.amattioli.guidate.browsing;

import it.amattioli.applicate.selection.SelectionEvent;
import it.amattioli.applicate.selection.SelectionListener;
import it.amattioli.applicate.selection.Selector;
import it.amattioli.authorizate.sessions.AuthorizationSession;
import it.amattioli.guidate.containers.BackBeanNotFoundException;
import it.amattioli.guidate.containers.BackBeans;
import it.amattioli.guidate.util.DesktopAttributes;

import org.zkoss.zk.ui.util.GenericAutowireComposer;

public class BrowsingToolComposer extends GenericAutowireComposer implements SelectionListener {
	private static final String AUTH_OPERATION_ATTRIBUTE = "authOperation";
	private String operation;
	
	public BrowsingToolComposer() {
		
	}
	
	public BrowsingToolComposer(String operation) {
		this.operation = operation;
	}
	
	private String getOperation() {
		String result = operation;
		if (result == null) {
			result = (String)self.getAttribute(AUTH_OPERATION_ATTRIBUTE);
		}
		return result;
	}
	
	public Selector<?> getBrowser() {
		return (Selector<?>)BackBeans.findBackBean(self);
	}
	
	public void onCreate() {
		Selector<?> browser = getBrowser();
		if (browser == null) {
			throw new BackBeanNotFoundException();
		}
		browser.addSelectionListener(this);
		self.setVisible(isActiveFor(browser.getSelectedObject()));
	}

	@Override
	public void objectSelected(SelectionEvent event) {
		Object subject = ((Selector<?>)event.getSource()).getSelectedObject();
		self.setVisible(isActiveFor(subject));
	}
	
	protected boolean isActiveFor(Object subject) {
		boolean active = subject != null && isVisible();
		String op = getOperation();
		Object sessionObj = desktop.getAttribute(DesktopAttributes.SESSION);
		if (op != null && sessionObj instanceof AuthorizationSession) {
			AuthorizationSession session = (AuthorizationSession)sessionObj;
			active = active && session.checkRule(op, subject);
		}
		return active;
	}
	
	protected boolean isVisible() {
		return true;
	}

}
