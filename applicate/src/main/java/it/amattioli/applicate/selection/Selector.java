package it.amattioli.applicate.selection;

/**
 * This interface should be implemented by all services that allows the selection
 * of an object.
 * 
 * It has methods for retrieving the selected object and for registering listeners
 * to be notified of selection events.
 * 
 * @author andrea
 *
 * @param <I>
 * @param <T>
 */
public interface Selector<T> {

	/**
     * Retrieves the object currently selected.
     *
     * @return the object currently selected or null if no object is selected
     */
    public T getSelectedObject();

    /**
     * Register a listener for the object selection.
     * Every time an object is selected in this browser all the registered
     * objects will be notified.
     *
     * @param listener the listener to be registered
     */
    public void addSelectionListener(SelectionListener listener);

    /**
     * Remove a listener from the collection of the listeners that will be
     * notified when an object is selected in this browser.
     *
     * @param listener the listener to be removed
     */
    public void removeSelectionListener(SelectionListener listener);
}
