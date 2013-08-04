package it.amattioli.applicate.browsing;

import it.amattioli.applicate.commands.CommandListener;
import it.amattioli.applicate.selection.FilteredSelector;
import it.amattioli.applicate.selection.SelectionEvent;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Specification;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.List;

/**
 * A ListBrowser object allows the navigation throw the entities contained in a
 * repository in a sequential way.
 * The browser allows objects filtering and sorting, the selection of objects
 * and forward/backward selection navigation.
 * You can obtain and {@link ObjectBrowser} on the selected object whose content
 * change when a new object is selected in this browser.
 *
 * @param <I> the identifier class of the entities that can be browsed
 *
 * @param <T> the class of the entities that can be browsed
 *
 * @author a.mattioli
 *
 */
public interface ListBrowser<I extends Serializable, T extends Entity<I>> extends Browser<I, T>, FilteredSelector<I,T>, CommandListener {

    /**
     * Retrieves the list of the browsing objects. This list will be the content of
     * the repository on which this browser is based, filtered using the criteria
     * specified by {@link #setCriteria(Criteria) setCriteria}.
     * The list will be sorted using the property specified by {@link #setOrder(String) setOrder}.
     * If something causes the changing of the list of browsing objects a PropertyChange
     * event will be fired.
     *
     * @return the list of the browsing objects
     */
    public List<T> getList();
    
    /**
     * Set the specification to be used to filter the browsing objects.
     * The {@link #getList()} method will return only the objects that
     * satisfy the specification.
     * The browser will listen on property changes of the specification bean
     * so to refresh the browsing objects accordingly.
     * 
     * @param spec the specification that will be used to filter the browsing objects
     */
    public void setSpecification(Specification<T> spec);
    
    /**
     * Retrieves the specification object set by {@link #setSpecification(Specification)}
     * 
     * @return the specification object set by {@link #setSpecification(Specification)}
     */
    public Specification<T> getSpecification();

    /**
     * Set the property that will be used to sort the browsing objects.
     *
     * @param property the name of a property of the browsing objects that will be
     *        used to sort the list of browsing objects
     * @param reverse if true the objects will be sort in reverse order
     */
    public void setOrder(String property, boolean reverse);

    /**
     * Set the property that will be used to sort the browsing objects.
     * If this method is called more than one time with the same parameter
     * the order will be reversed.
     *
     * @param property the name of a property of the browsing objects that will be
     *        used to sort the list of browsing objects
     */
    public void setOrder(String property);
    
    /**
     * Add a property that will be used to sort the browsing objects.
     * If a previous order property was set it will remain in place 
     * and the new property will be added to the list.
     * 
     * @param property
     */
    public void addOrder(String property);

    /**
     * Retrieves the name of the property of the browsing objects that will be
     * used to sort the list of browsing objects.
     *
     * @return the name of the property of the browsing objects that will be
     *         used to sort the list of browsing objects or null if no sorting
     *         property was set
     */
    public String getOrderProperty();

    /**
     * Indicates if the order is direct or reverse.
     *
     * @return true if the order is reversed
     */
    public boolean getReverseOrder();

    /**
     * Select an object using its index inside the list retrieved using {@link #getList()}.
     * After the selection a {@link SelectionEvent} will be notified to all
     * the objects that has been registered using
     * {@link #addContentChangeListener(BrowserSelectionListener)}.
     * If multiple selection is enabled (using {@link #setMultipleSelection(boolean)}) selecting
     * an object twice will deselect it.
     *
     * @param index the index inside the list retrieved using {@link #getList()}
     *        of the object to be selected
     */
    public void select(int index);

    /**
     * Select an object.
     * After the selection a {@link SelectionEvent} will be notified to all
     * the objects that has been registered using
     * {@link #addContentChangeListener(BrowserSelectionListener)}.
     *
     * @param object the object to be selected
     */
    public void select(T object);

    /**
     * Removes the current selection.
     * After this every call to {@link #getSelectedObject()} and {@link #getSelectedIndex()}
     * will return null.
     *
     */
    public void deselect();

    /**
     * Retrieves the index of the object currently selected.
     * It is the same number that must be used to select it using
     * {@link #select(int)}.
     * if multiple selection is enabled this method will return the index of the last
     * selected object.
     *
     * @return the index of the object currently selected or null if no object is
     *         selected
     */
    public Integer getSelectedIndex();

    /**
     * Move the selection to the object next to the currently selected object.
     * If the currently selected object is the last of the list, nothing will happen.
     *
     */
    public void next();

    /**
     * Indicate if there is an object next to the currently selected object.
     *
     * @return true if there is an object next to the currently selected object,
     *         false if the currently selected object is the last in the list
     */
    public boolean getHasNext();

    /**
     * Move the selection to the object previous to the currently selected object.
     * If the currently selected object is the first of the list, nothing will happen.
     *
     */
    public void previous();

    /**
     * Indicate if there is an object previous to the currently selected object.
     *
     * @return true if there is an object previous to the currently selected object,
     *         false if the currently selected object is the first in the list
     */
    public boolean getHasPrevious();

    /**
     * Set the class that will be used by {@link #newObjectBrowser()}.
     *
     * @param objectBrowserClass the class that will be used by {@link #newObjectBrowser()}
     */
    public void useObjectBrowserClass(Class<? extends ObjectBrowserImpl<I, T>> objectBrowserClass);

    /**
     * Create a new {@link ObjectBrowser} for the currently selected object.
     * The object created will be an instance of the class set using
     * {@link #useObjectBrowserClass(Class)}. If no object browser class was
     * specified {@link ObjectBrowserImpl} will be used.
     *
     * @return the new {@link ObjectBrowser}
     */
    public ObjectBrowser<I, T> newObjectBrowser();

    /**
     * Retrieves a {@link ObjectBrowser} for the selected object.
     * This {@link ObjectBrowser} will be created the first time
     * this method is called and will be linked to the currently
     * selected object. Every time a new object is elected in the browser
     * the {@link ObjectBrowser} will be notified so it can refresh
     * itself to contain the currently slected object.
     *
     * @return a {@link ObjectBrowser} for the selected object
     */
    public ObjectBrowser<I, T> getSelectedObjectBrowser();

    /**
     * Add a listener for {@link PropertyChangeEvent} events.
     *
     * @param listener a listener for {@link PropertyChangeEvent} events
     */
    public void addPropertyChangeListener(PropertyChangeListener listener);

    /**
     * Remove a listener for {@link PropertyChangeEvent} events.
     *
     * @param listener a listener for {@link PropertyChangeEvent} events
     */
    public void removePropertyChangeListener(PropertyChangeListener listener);

    /**
     * Set the possibility to use multiple selection.
     * 
     * @param multipleSelection
     */
	public void setMultipleSelection(boolean multipleSelection);

	/**
	 * Check if this browser supports multiple selection.
	 * 
	 * @return true if this browser supports multiple selection
	 */
	public boolean isMultipleSelection();

	/**
	 * Retrieves a list of all the selected objects. If multiple selection is not enabled
	 * this method will return a collection containing only one object (if an object is
	 * selected) or an empty collection if no object is selected.
	 * 
	 * @return a list of all the selected objects
	 */
	public abstract List<T> getSelectedObjects();

	/**
	 * Retrieves a list of the indexes of all the selected objects. If multiple selection is not enabled
	 * this method will return a collection containing only one object (if an object is
	 * selected) or an empty collection if no object is selected.
	 * 
	 * @return a list of the indexes of all the selected objects
	 */
	public abstract List<Integer> getSelectedIndexes();
}
