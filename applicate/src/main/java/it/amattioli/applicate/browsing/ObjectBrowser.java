package it.amattioli.applicate.browsing;

import it.amattioli.applicate.commands.CommandListener;
import it.amattioli.applicate.selection.SelectionEvent;
import it.amattioli.applicate.selection.SelectionListener;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.properties.PropertyChangeEmitter;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Map;

/**
 * An ObjectBrowser acts like a place-holder for entities of a certain type.
 * Form this browser you can access the hold object simple properties or obtain a
 * {@link ListBrowser} to access its Collection properties.
 * <p>This browser can act as a BrowserSelectionListener too. Every time it receives
 * a {@link SelectionEvent} it change the hold object so to reflect the
 * currently selected object.
 * <p>As any other browser it listen to command events so to refresh its content every
 * time a command gets executed.
 *
 * @author andrea
 *
 * @param <I> the identifier class of the entities that can be browsed
 *
 * @param <T> the class of the entities that can be browsed
 */
public interface ObjectBrowser<I extends Serializable, T extends Entity<I>>
extends Browser<I,T>, CommandListener, SelectionListener, PropertyChangeListener, PropertyChangeEmitter {

	/**
	 * Retrieves the object that this browser is holding.
	 *
	 * @return the object that this browser is holding
	 */
	public T getHold();

	/**
	 * Refresh the object that this browser is holding retrieving a fresh
	 * copy from the repository.
	 */
	public void refresh();

	/**
	 * Retrieves a map containing all the possible browsers that 
	 * {@link #getDetailsBrowser(String)} will return.
	 *  
	 * @return
	 */
	public Map<String, ListBrowserImpl<?,?>> getDetailsBrowsers();
	
	/**
	 * Retrieves a ListBrowser to navigate the collection of objects contained in
	 * a property.<p>
	 * If the hold object has a property whose name is the passed string and whose 
	 * type is a collection of entities, this method returns a ListBrowser to navigate
	 * the entities contained in this collection.<p>
	 * If such a property does not exists, null will be returned.<p>
	 *  
	 * @param propertyName the name of the property whose content will be browsed
	 * @return a ListBrowser to navigate the collection of objects contained in
	 * a property
	 */
	public ListBrowserImpl<?,?> getDetailsBrowser(String propertyName);

}