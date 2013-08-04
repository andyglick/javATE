package it.amattioli.applicate.commands.tree;

import java.util.WeakHashMap;

/**
 * Helper class that simplify {@link ContentChangeListener} registration and the
 * notification of {@link ContentChangeEvent} to registered listeners.
 *
 * @author andrea
 *
 */
public class TreeEventSupport {
	private static final String DUMMY = "dummy";

	private WeakHashMap<TreeEventListener, String> treeEventListeners;

	/**
	 * Register a new listener so that it will be notified of selection events.
	 *
	 * @param listener the listener to be registered
	 */
	public void addTreeListener(TreeEventListener listener) {
        if (treeEventListeners == null) {
            treeEventListeners = new WeakHashMap<TreeEventListener, String>();
        }
        treeEventListeners.put(listener, DUMMY);
    }

	/**
	 * De-register a previously registered listener.
	 *
	 * @param listener the listener to be de-registered
	 */
    public void removeTreeListener(TreeEventListener listener) {
        if (treeEventListeners != null) {
            treeEventListeners.remove(listener);
        }
    }

    /**
     * Notify a new event to all the registered listeners.
     *
     * @param event the event to be notified
     */
    public void notifyTreeListeners(TreeEvent event) {
        if (treeEventListeners != null) {
            for (TreeEventListener curr : treeEventListeners.keySet()) {
                curr.treeChanged(event);
            }
        }
    }

}
