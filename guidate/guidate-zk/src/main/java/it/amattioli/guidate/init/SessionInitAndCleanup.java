package it.amattioli.guidate.init;

import it.amattioli.applicate.sessions.ApplicateSession;
import it.amattioli.applicate.sessions.Application;
import it.amattioli.guidate.config.GuidateConfig;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.util.SessionCleanup;
import org.zkoss.zk.ui.util.SessionInit;

public class SessionInitAndCleanup implements SessionInit, SessionCleanup {
	public static final String ORDINI_SESSION = "it.amattioli.guidate.session";

	@Override
	public void init(Session zkSession, Object request) throws Exception {
		if (!GuidateConfig.instance.isSessionInDesktop()) {
			Application app = Guidate.getApplication();
	    	ApplicateSession session = app.createSession();
	        zkSession.setAttribute(ORDINI_SESSION, session);
		}
	}

	@Override
	public void cleanup(Session zkSession) throws Exception {
		if (!GuidateConfig.instance.isSessionInDesktop()) {
			((ApplicateSession)zkSession.getAttribute(ORDINI_SESSION)).release();
			zkSession.setAttribute(ORDINI_SESSION, null);
		}
	}

}
