package it.amattioli.dominate.specifications;

import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpecificationChangeSupport {
	private static final String DUMMY = "dummy";
	private static final Logger logger = LoggerFactory.getLogger(SpecificationChangeSupport.class);
	private WeakHashMap<SpecificationChangeListener, String> listeners;

	private WeakHashMap<SpecificationChangeListener, String> getGenericListeners() {
        if (listeners == null) {
            listeners = new WeakHashMap<SpecificationChangeListener, String>();
        }
        return listeners;
    }

    public void addListener(SpecificationChangeListener listener) {
    	logger.debug("Registering specification listener {}", listener);
        getGenericListeners().put(listener, DUMMY);
    }

    public void removeListener(SpecificationChangeListener listener) {
    	logger.debug("Deregistering specification listener {}", listener);
    	getGenericListeners().remove(listener);
    }

    private void fireSpecificationEvent(SpecificationChangeEvent evt, SpecificationChangeListener listener) {
    	logger.debug("Notifying {} to {}", evt, listener);
		listener.specificationChange(evt);
    }

    public void fireSpecificationEvent(SpecificationChangeEvent evt) {
    	for (SpecificationChangeListener curr : getGenericListeners().keySet()) {
    		fireSpecificationEvent(evt, curr);
        }
    	logger.debug("Specification Listeners notified");
    }
}
