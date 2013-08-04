package it.amattioli.guidate.init;

import it.amattioli.guidate.config.GuidateConfig;
import it.amattioli.guidate.util.DesktopAttributes;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.UiLifeCycle;

public class PageInit implements UiLifeCycle {

	@Override
	public void afterComponentAttached(Component arg0, Page arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterComponentDetached(Component arg0, Page arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterComponentMoved(Component arg0, Component arg1, Component arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterPageAttached(Page page, Desktop desktop) {
		page.setVariable(GuidateConfig.instance.getApplicateSessionVariable(), 
				         desktop.getAttribute(DesktopAttributes.SESSION));
	}

	@Override
	public void afterPageDetached(Page arg0, Desktop arg1) {
		// TODO Auto-generated method stub

	}

}
