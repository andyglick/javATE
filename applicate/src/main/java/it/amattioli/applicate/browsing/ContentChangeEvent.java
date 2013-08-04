package it.amattioli.applicate.browsing;

import java.util.EventObject;

/**
 * An event notified to {@link ContentChangeListener} objects every time
 * a browser content changes.
 * The browser on which the event was generated can be retrieved using
 * {@link #getSource()}.
 *
 * @author andrea
 *
 */
public class ContentChangeEvent extends EventObject {

	/**
	 * Construct a {@link ContentChangeEvent} given the browser that generated it.
	 *
	 * @param source the browser that generated the event
	 */
    public ContentChangeEvent(Browser<?, ?> source) {
        super(source);
    }

    /**
     * Retrieves the browser on which the event has been generated.
     *
     * @return the browser on which the event has been generated
     */
	@Override
	public Browser<?, ?> getSource() {
		return (Browser<?, ?>)super.getSource();
	}

	@Override
	public String toString() {
		return "SelectionEvent[source=" + getSource() + "]";
	}

}
