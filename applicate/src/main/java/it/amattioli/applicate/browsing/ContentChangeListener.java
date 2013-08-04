package it.amattioli.applicate.browsing;

/**
 * This interface must be implemented by all the objects that need
 * to be notified of a browser content change event.
 * Every time that a browser content changes, an event will be
 * sent to all listening objects calling {@link #contentChanged(SelectionEvent)}.
 *
 * @author a.mattioli
 *
 */
public interface ContentChangeListener {

    /**
     * Will be called every time that content changes in a browser
     * on which this listener has been registered.
     *
     * @param event the content change event
     */
    public void contentChanged(ContentChangeEvent event);

}
