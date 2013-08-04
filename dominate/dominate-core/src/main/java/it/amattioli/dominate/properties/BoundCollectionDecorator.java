package it.amattioli.dominate.properties;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

/**
 * A decorator for collections that fires an event every time the collection or one of its
 * elements is modified.<p>
 * 
 * If a collection is decorated with this class when the {@link #add(Object)} or {@link #remove(Object)}
 * method is called a {@link CollectionChangeEvent} is fired and every {@link CollectionChangeListener}
 * registered using the {@link #addCollectionChangeListener(CollectionChangeListener)} method is
 * notified. The same happens every time one of the elements of the collection fires a 
 * {@link PropertyChangeEvent}.
 * 
 * @author andrea
 *
 * @param <T>
 */
public class BoundCollectionDecorator<T> extends AbstractCollection<T> {
	private Collection<T> decorated;
	private CollectionChangeSupport changeSupport = new CollectionChangeSupport();
	private PropertyChangeListener elementsListener = new PropertyChangeListener() {
		
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			fireChangeEvent();
		}

	};
	
	public BoundCollectionDecorator(Collection<T> decorated) {
		this.decorated = decorated;
	}
	
	private void fireChangeEvent() {
		changeSupport.fireEvent(new CollectionChangeEvent(this));
	}
	
	@Override
	public Iterator<T> iterator() {
		return new BoundCollectionIterator(decorated.iterator());
	}

	@Override
	public boolean add(T e) {
		boolean result = decorated.add(e);
		if (e instanceof PropertyChangeEmitter) {
			((PropertyChangeEmitter) e).addPropertyChangeListener(elementsListener);
		}
		fireChangeEvent();
		return result;
	}

	@Override
	public int size() {
		return decorated.size();
	}
	
	public void addCollectionChangeListener(CollectionChangeListener listener) {
		changeSupport.addListener(listener);
	}
	
	public void removeCollectionChangeListener(CollectionChangeListener listener) {
		changeSupport.removeListener(listener);
	}

	private class BoundCollectionIterator implements Iterator<T> {
		private Iterator<T> decoratedIterator;
		T curr;
		
		public BoundCollectionIterator(Iterator<T> decoratedIterator) {
			this.decoratedIterator = decoratedIterator;
		}
		
		@Override
		public boolean hasNext() {
			return decoratedIterator.hasNext();
		}

		@Override
		public T next() {
			curr = decoratedIterator.next();
			if (curr instanceof PropertyChangeEmitter) {
				((PropertyChangeEmitter) curr).addPropertyChangeListener(elementsListener);
			}
			return curr;
		}

		@Override
		public void remove() {
			decoratedIterator.remove();
			if (curr instanceof PropertyChangeEmitter) {
				((PropertyChangeEmitter) curr).removePropertyChangeListener(elementsListener);
			}
			fireChangeEvent();
		}
		
	}
}
