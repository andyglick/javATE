package it.amattioli.guidate.browsing;

import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Listbox;

public class BrowserListbox extends Listbox implements AfterCompose {

	@Override
	public void afterCompose() {
		BrowserListboxComposer composer = new BrowserListboxComposer();
		try {
			composer.doAfterCompose(this);
		} catch(Throwable e) {
			// TODO Handle exception in a better way
			throw new RuntimeException(e);
		}
	}

}
