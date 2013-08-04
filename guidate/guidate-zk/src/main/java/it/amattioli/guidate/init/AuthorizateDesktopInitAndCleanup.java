package it.amattioli.guidate.init;

import org.zkoss.zk.ui.Desktop;

import it.amattioli.applicate.sessions.ApplicateSession;
import it.amattioli.authorizate.sessions.DefaultAuthorizationSession;
import it.amattioli.guidate.config.GuidateConfig;

public class AuthorizateDesktopInitAndCleanup extends DesktopInitAndCleanup {

	@Override
	public void configureSession(Desktop desktop, ApplicateSession session) {
		super.configureSession(desktop, session);
		if (session instanceof DefaultAuthorizationSession) {
			DefaultAuthorizationSession authSession = (DefaultAuthorizationSession)session;
			authSession.setUser(desktop.getExecution().getRemoteUser());
			if (authSession.getAuthorizationManager() == null) {
				try {
					authSession.setAuthorizationManager(GuidateConfig.instance.getAuthManagerClass().newInstance());
				} catch (InstantiationException e) {
					throw new RuntimeException(e);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	
}
