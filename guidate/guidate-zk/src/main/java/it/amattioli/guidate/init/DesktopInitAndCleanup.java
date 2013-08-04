package it.amattioli.guidate.init;

import it.amattioli.applicate.sessions.ApplicateSession;
import it.amattioli.applicate.sessions.Application;
import it.amattioli.guidate.config.GuidateConfig;
import it.amattioli.guidate.util.DesktopAttributes;

import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.util.DesktopCleanup;
import org.zkoss.zk.ui.util.DesktopInit;

public class DesktopInitAndCleanup implements DesktopInit, DesktopCleanup {
	
    public void init(Desktop desktop, Object obj) throws Exception {
    	if (GuidateConfig.instance.isSessionInDesktop()) {
	    	Application app = Guidate.getApplication();
	    	ApplicateSession session = app.createSession();
	    	configureSession(desktop, session);
	        desktop.setAttribute(DesktopAttributes.SESSION, session);
    	} else {
    		Object session = desktop.getSession().getAttribute(SessionInitAndCleanup.ORDINI_SESSION);
    		desktop.setAttribute(DesktopAttributes.SESSION, session);
    	}
    }
    
    public void configureSession(Desktop desktop, ApplicateSession session) {
    	// Default does nothing
    }

	public void cleanup(Desktop desktop) throws Exception {
		if (GuidateConfig.instance.isSessionInDesktop()) {
			((ApplicateSession)desktop.getAttribute(DesktopAttributes.SESSION)).release();
		}
		desktop.setAttribute(DesktopAttributes.SESSION, null);
	}

}
