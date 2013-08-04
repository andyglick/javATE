package it.amattioli.applicate.browsing;

import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class that simplify {@link ContentChangeListener} registration and the
 * notification of {@link ContentChangeEvent} to registered listeners.
 *
 * @author andrea
 *
 */
public class ContentChangeSupport {
	private static final String DUMMY = "dummy";
	private static final Logger logger = LoggerFactory.getLogger(ContentChangeSupport.class);
	private boolean enabled = true;
	private WeakHashMap<ContentChangeListener, String> listeners;

	/**
	 * Check if event notification is currently enabled.
	 * Event notification can be enabled calling {@link #enable()} and can be disabled
	 * calling {@link #disable()}.<p>
	 * A {@link ContentChangeSupport} object is initially enabled.
	 * 
	 * @return true if event notification is currently enabled, false otherwise
	 */
	public boolean isEnabled() {
		return enabled;
	}
	
	/**
	 * Enable the notification of the events. After calling this method every event passed
	 * to {@link #notifyContentChangeListeners(ContentChangeEvent)} will be notified to the
	 * listeners.<p>
	 * A {@link ContentChangeSupport} object is initially enabled.
	 * 
	 * @see #disable()
	 */
	public void enable() {
		enabled = true;
	}
	
	/**
	 * Disable the notification of the events. After calling this method every event passed
	 * to {@link #notifyContentChangeListeners(ContentChangeEvent)} will not be notified to the
	 * listeners.
	 */
	public void disable() {
		enabled = false;
	}
	
	/**
	 * Register a new listener so that it will be notified of selection events.
	 *
	 * @param listener the listener to be registered
	 */
	public void addContentChangeListener(ContentChangeListener listener) {
		logger.debug("Registering content change listener {}", listener);
        if (listeners == null) {
            listeners = new WeakHashMap<ContentChangeListener, String>();
        }
        listeners.put(listener, DUMMY);
    }

	/**
	 * De-register a previously registered listener.
	 *
	 * @param listener the listener to be de-registered
	 */
    public void removeContentChangeListener(ContentChangeListener listener) {
    	logger.debug("Removing content change listener {}", listener);
        if (listeners != null) {
            listeners.remove(listener);
        }
    }

    /**
     * Notify a new event to all the registered listeners.
     *
     * @param event the event to be notified
     */
    public void notifyContentChangeListeners(ContentChangeEvent event) {
        if (isEnabled() && listeners != null) {
            for (ContentChangeListener curr : listeners.keySet()) {
            	logger.debug("Notifying {} to {}", event, curr);
                curr.contentChanged(event);
            }
            logger.debug("Content Change Listeners notified");
        }
    }

}
