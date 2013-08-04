package it.amattioli.guidate.init;

import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.util.WebAppCleanup;
import org.zkoss.zk.ui.util.WebAppInit;

public class ApplicationInit implements WebAppInit, WebAppCleanup {
	
	@Override
	public void init(WebApp app) throws Exception {
		Guidate.getApplication().init();
	}

	@Override
	public void cleanup(WebApp app) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
