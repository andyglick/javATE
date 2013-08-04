package it.amattioli.guidate.browsing;

import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Listheader;

public class BrowserListheader extends Listheader implements AfterCompose {

	@Override
	public void afterCompose() {
		BrowserListheaderComposer composer = new BrowserListheaderComposer();
		try {
			composer.doAfterCompose(this);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
