package it.amattioli.applicate.browsing;

import it.amattioli.applicate.selection.Selector;
import it.amattioli.dominate.Entity;

import java.io.Serializable;

/**
 * Browsers let you navigate through the network of the objects that compose your object model.
 * This interface declares the very basic methods that all the browsers must implement:
 * 
 * <ul>
 *   <li>Selection methods (extending {@link Selector})
 *   <li>Content change support methods ({@link #addContentChangeListener(ContentChangeListener)} and
 *       {@link #removeContentChangeListener(ContentChangeListener)})
 *   <li>Releasing ({@link #release()})
 * </ul>
 *
 * @param <I> the identifier class of the entities that can be browsed
 * @param <T> the class of the entities that can be browsed 
 */
public interface Browser<I extends Serializable, T extends Entity<I>> extends Selector<T> {

	/**
	 * Releases eventually acquired resources. After calling this method the
	 * browser can no longer be used.
	 */
	public void release();
	
	/**
	 * Add a listener for events fired when the browser content changes.
	 * 
	 * @param listener the listener to be added
	 */
	public void addContentChangeListener(ContentChangeListener listener);
	
	/**
	 * Remove a listener for events fired when the browser content changes.
	 * 
	 * @param listener the listener to be removed
	 */
	public void removeContentChangeListener(ContentChangeListener listener);
	
}
