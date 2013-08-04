package it.amattioli.guidate.wizard;

import static it.amattioli.guidate.validators.ValidatingComposer.ON_VALIDATE_ELEMENT;
import it.amattioli.guidate.containers.BackBeans;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericComposer;

public abstract class AbstractWizardComposer extends GenericComposer {
	protected static final String WIZARD_TAG = "it.amattioli.guidate.wizard";
	private WizardState state;
	
	public static Component find(Component comp) {
		if (comp == null) {
			return null;
		}
		if (Boolean.TRUE.equals(comp.getAttribute(WIZARD_TAG))) {
			return comp;
		}
		return find(comp.getParent());
	}
	
	public AbstractWizardComposer(WizardState state) {
		this.state = state;
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		comp.setAttribute(WIZARD_TAG, true);
	}

	public void onCreate(Event evt) {
		state.setBackBean(BackBeans.findTargetBackBean(evt));
		fireNavigationEvent();
	}
	
	public boolean hasPrevious() {
		return state.hasPrevious();
	}
	
	public boolean hasNext() {
		return state.hasNext();
	}
	
	public boolean hasFinish() {
		return state.hasFinish();
	}
	
	public String getCurrentPage() {
		return state.getCurrentPage();
	}
	
	public void onPrevious(Event evt) {
		if (state.hasPrevious()) {
			Events.sendEvent(new Event(ON_VALIDATE_ELEMENT, evt.getTarget()));
			state.previous();
			fireNavigationEvent();
		}
	}
	
	public void onNext(Event evt) {
		if (state.hasNext()) {
			Events.sendEvent(new Event(ON_VALIDATE_ELEMENT, evt.getTarget()));
			state.next();
			fireNavigationEvent();
		}
	}
	
	public void onFinish(Event evt) {
		if (state.hasFinish()) {
			Events.sendEvent(new Event(ON_VALIDATE_ELEMENT, evt.getTarget()));
			state.finish();
		}
	}
	
	private void fireNavigationEvent() {
		Events.postEvent(new WizardNavigationEvent(this));
	}
}
