package it.amattioli.guidate.wizard;

import org.zkoss.zk.ui.event.Event;

public class WizardNavigationEvent extends Event {
	public static final String NAME = "onWizardNavigation";
	private AbstractWizardComposer composer;
	
	public WizardNavigationEvent(AbstractWizardComposer composer) {
		super(NAME, null);
		this.composer = composer;
	}
	
	public boolean hasPrevious() {
		return composer.hasPrevious();
	}
	
	public boolean hasNext() {
		return composer.hasNext();
	}
	
	public boolean hasFinish() {
		return composer.hasFinish();
	}
	
	public String getCurrentPage() {
		return composer.getCurrentPage();
	}
	
}
