package it.amattioli.dominate.properties;

import java.util.Collection;
import java.util.EventObject;
import java.util.List;

/**
 * An event fired by a {@link Collection} decorated with {@link BoundCollectionDecorator} or a
 * {@link List} decorated with {@link BoundListDecorator}.
 * 
 * @author andrea
 *
 */
public class CollectionChangeEvent extends EventObject {

	public CollectionChangeEvent(Collection<?> source) {
		super(source);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof CollectionChangeEvent &&
		       this.getSource() == ((CollectionChangeEvent)obj).getSource();
	}

	@Override
	public int hashCode() {
		return 2069 + 3467 * getSource().hashCode();
	}
	
}
