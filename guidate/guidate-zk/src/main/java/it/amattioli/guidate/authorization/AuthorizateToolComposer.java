package it.amattioli.guidate.authorization;

import it.amattioli.authorizate.sessions.AuthorizationSession;
import it.amattioli.guidate.util.DesktopAttributes;

import org.zkoss.zk.ui.event.Event;

public abstract class AuthorizateToolComposer extends AuthorizateComposer {

	public void onClick(Event evt) throws Exception {
		if (getOperation() != null) {
			AuthorizationSession session = (AuthorizationSession)evt.getTarget().getDesktop().getAttribute(DesktopAttributes.SESSION);
			if (session == null) {
				throw new IllegalStateException("Authorization session not initialized");
			}
			if (session.checkRule(getOperation(), getSubject())) {
				perform(evt);
			}
		} else {
			perform(evt);
		}
	}
	
	protected abstract void perform(Event evt) throws Exception;
}
