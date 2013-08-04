package it.amattioli.guidate.util;

import org.zkoss.zk.ui.Component;

import it.amattioli.applicate.sessions.ApplicateSession;

public class DesktopAttributes {
	public static final String MESSAGE_BUNDLE = "it.amattioli.guidate.messageBundle";
	public static final String SESSION = "it.amattioli.guidate.session";
	
	private DesktopAttributes() {}
	
	public static ApplicateSession getSession(Component comp) {
		return (ApplicateSession)comp.getDesktop().getAttribute(DesktopAttributes.SESSION);
	}

}
