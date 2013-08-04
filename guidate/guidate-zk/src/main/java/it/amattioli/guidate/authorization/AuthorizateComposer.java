package it.amattioli.guidate.authorization;

import it.amattioli.authorizate.sessions.AuthorizationSession;
import it.amattioli.guidate.util.DesktopAttributes;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericComposer;

public class AuthorizateComposer extends GenericComposer {
	private static final String AUTH_OPERATION_ATTRIBUTE = "authOperation";
	private static final String AUTH_SUBJECT_ATTRIBUTE = "authSubject";
	private String operation;
	private Object subject;
	
	private String getOperation(Component comp) {
		String result = getOperation();
		if (result == null) {
			result = (String)comp.getAttribute(AUTH_OPERATION_ATTRIBUTE);
		}
		return result;
	}
	
	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	public Object getSubject(Component comp) {
		Object result = getSubject();
		if (result == null) {
			result = comp.getAttribute(AUTH_SUBJECT_ATTRIBUTE);
		}
		return result;
	}
	
	public Object getSubject() {
		return subject;
	}

	public void setSubject(Object subject) {
		this.subject = subject;
	}

	public void onCreate(Event evt) {
		String op = getOperation(evt.getTarget());
		if (op != null) {
			Component target = evt.getTarget();
			target.setVisible(isVisible(target));
		}
	}

	public boolean isVisible(Component target) {
		if (target.getDesktop() == null) {
			return false;
		}
		AuthorizationSession session = (AuthorizationSession)target.getDesktop().getAttribute(DesktopAttributes.SESSION);
		if (session == null) {
			throw new IllegalStateException("Authorization session not initialized");
		}
		return session.checkRule(getOperation(target), getSubject(target));
	}

}
