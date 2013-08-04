package it.amattioli.guidate.validators;

import it.amattioli.guidate.containers.BackBeans;

import java.util.ArrayList;
import java.util.Collection;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericComposer;

public class ValidatingComposer extends GenericComposer {
	public static final String ON_VALIDATE_ELEMENT = "onValidateElement";
	public static final String ON_REGISTER_VALIDABLE_ELEMENT = "onRegisterValidableElement";
	private Collection<Component> validableElements = new ArrayList<Component>();
	
	public Collection<Component> validableElements() {
    	return validableElements;
    }
    
    public void registerValidableElement(Component element) {
    	validableElements.add(element);
    }
    
    public void onRegisterValidableElement(Event evt) {
    	registerValidableElement((Component)evt.getData());
    }
    
    public void onValidateElement(Event evt) {
    	for (Component element : validableElements()) {
    		if (isParent(evt.getTarget(), element)) {
	    		Events.sendEvent(new Event(ON_VALIDATE_ELEMENT, element));
    		}
		}
    }
    
    public void onCreate(Event evt) throws Exception {
    	Component comp = evt.getTarget();
    	if (comp.getParent() != null) {
        	Component container = BackBeans.findContainer(comp.getParent());
    		if (container != null) {
    			Events.sendEvent(new Event(ON_REGISTER_VALIDABLE_ELEMENT, container, comp));
    		}
    	}
    }

    /**
     * Check if a component is parent of another one
     * 
     * @param parent
     * @param comp
     * @return true if parent id parent of comp
     */
    private boolean isParent(Component parent, Component comp) {
    	Component curr = comp;
    	while (curr != null) {
    		if (curr == parent) {
    			return true;
    		}
    		curr = curr.getParent();
    	}
    	return false;
    }
}
