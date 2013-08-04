package it.amattioli.applicate.selection;

import java.util.EventObject;

/**
 * An event notified to {@link SelectionListener} objects every time
 * an object has been selected in a service that implements the {@link Selector}
 * interface.
 * The browser on which the event was generated can be retrieved using
 * {@link #getSource()}.
 *
 * @author andrea
 *
 */
public class SelectionEvent extends EventObject {

	/**
	 * Construct a {@link SelectionEvent} given the {@link Selector} service
	 * that generated it.
	 *
	 * @param source the service that generated the event
	 */
    public SelectionEvent(Selector<?> source) {
        super(source);
    }

    /**
     * Retrieves the {@link Selector} service that generated the event.
     *
     * @return the {@link Selector} service that generated the event
     */
	@Override
	public Selector<?> getSource() {
		return (Selector<?>)super.getSource();
	}

	@Override
	public String toString() {
		return "SelectionEvent[source=" + getSource() + "]";
	}

}
