package it.amattioli.applicate.selection;

/**
 * This interface must be implemented by all the objects that need
 * to be notified of a {@link SelectionEvent}.
 * Every time that an object is selected in a {@link Selector}, an event will be
 * sent to all listening objects caling {@link #objectSelected(SelectionEvent)}.
 *
 * @author a.mattioli
 *
 */
public interface SelectionListener {

    /**
     * Will be called every time an object is selected in a {@link Selector} service
     * on which this listener has been registered.
     *
     * @param event the selection event
     */
    public void objectSelected(SelectionEvent event);

}
