package it.amattioli.guidate.browsing;

import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Toolbarbutton;

public class BrowsingButton extends Toolbarbutton implements AfterCompose {
	private String operation;

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	@Override
	public void afterCompose() {
		BrowsingToolComposer composer = new BrowsingToolComposer(getOperation());
		try {
			composer.doAfterCompose(this);
		} catch(Throwable e) {
			// TODO Handle exception in a better way
			throw new RuntimeException(e);
		}
	}

}
