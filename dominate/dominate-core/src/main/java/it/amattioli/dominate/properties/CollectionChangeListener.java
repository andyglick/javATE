package it.amattioli.dominate.properties;

import java.util.EventListener;

/**
 * Listener for {@link CollectionChangeEvent}.
 * 
 * @author andrea
 *
 */
public interface CollectionChangeListener extends EventListener {

	public void collectionChanged(CollectionChangeEvent event);
	
}
