package it.amattioli.dominate.properties;

import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CollectionChangeSupport {
	private static final String DUMMY = "dummy";
	private static final Logger logger = LoggerFactory.getLogger(CollectionChangeSupport.class);
	private WeakHashMap<CollectionChangeListener, String> listeners;
	
	private WeakHashMap<CollectionChangeListener, String> getGenericListeners() {
        if (listeners == null) {
            listeners = new WeakHashMap<CollectionChangeListener, String>();
        }
        return listeners;
    }

    public void addListener(CollectionChangeListener listener) {
    	logger.debug("Registering collection change listener {}", listener);
        getGenericListeners().put(listener, DUMMY);
    }

    public void removeListener(CollectionChangeListener listener) {
    	logger.debug("Deregistering collection change listener {}", listener);
    	getGenericListeners().remove(listener);
    }

    private void fireEvent(CollectionChangeEvent evt, CollectionChangeListener listener) {
    	logger.debug("Notifying {} to {}", evt, listener);
		listener.collectionChanged(evt);
    }

    public void fireEvent(CollectionChangeEvent evt) {
    	for (CollectionChangeListener curr : getGenericListeners().keySet()) {
    		fireEvent(evt, curr);
        }
    	logger.debug("Collection Change Listeners notified");
    }
}
