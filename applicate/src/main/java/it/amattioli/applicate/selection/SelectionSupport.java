package it.amattioli.applicate.selection;

import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class that simplify {@link SelectionListener} registration and the
 * notification of {@link SelectionEvent} to registered listeners.
 *
 * @author andrea
 *
 */
public class SelectionSupport {
	private static final String DUMMY = "dummy";
	private static final Logger logger = LoggerFactory.getLogger(SelectionSupport.class);
	private WeakHashMap<SelectionListener, String> selectionListeners;
	private boolean enabled = true;

	/**
	 * Enable the notification of events to the listeners
	 */
	public void enable() {
		enabled = true;
	}
	
	/**
	 * Disable the notification of events to the listeners
	 */
	public void disable() {
		enabled = false;
	}
	
	/**
	 * Check if notification of events to the listeners is enabled
	 * 
	 * @return
	 */
	public boolean isEnabled() {
		return enabled;
	}
	
	/**
	 * Register a new listener so that it will be notified of selection events.
	 *
	 * @param listener the listener to be registered
	 */
	public void addSelectionListener(SelectionListener listener) {
		logger.debug("Registering selection listener {}", listener);
        if (selectionListeners == null) {
            selectionListeners = new WeakHashMap<SelectionListener, String>();
        }
        selectionListeners.put(listener, DUMMY);
    }

	/**
	 * De-register a previously registered listener.
	 *
	 * @param listener the listener to be de-registered
	 */
    public void removeSelectionListener(SelectionListener listener) {
    	logger.debug("Removing selection listener {}", listener);
        if (selectionListeners != null) {
            selectionListeners.remove(listener);
        }
    }

    /**
     * Notify a new event to all the registered listeners.
     * The event will not be notified if this object has been disabled using {@link #disable()}
     *
     * @param event the event to be notified
     */
    public void notifySelectionListeners(SelectionEvent event) {
        if (enabled && selectionListeners != null) {
            for (SelectionListener curr : selectionListeners.keySet()) {
            	logger.debug("Notifying {} to {}", event, curr);
                curr.objectSelected(event);
            }
            logger.debug("Selection Listeners notified");
        }
    }

}
