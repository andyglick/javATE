package it.amattioli.dominate.properties;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.AbstractList;
import java.util.List;

/**
 * A decorator for lists that fires an event every time the collection or one of its
 * elements is modified.<p>
 * 
 * If a collection is decorated with this class when the {@link #add(Object)}, 
 * {@link #set(int, Object)} or {@link #remove(int)} method is called a {@link CollectionChangeEvent} 
 * is fired and every {@link CollectionChangeListener} registered using the 
 * {@link #addCollectionChangeListener(CollectionChangeListener)} method is notified. 
 * The same happens every time one of the elements of the collection fires a {@link PropertyChangeEvent}.
 * 
 * @author andrea
 *
 * @param <T>
 */
public class BoundListDecorator<T> extends AbstractList<T> {
	private List<T> decorated;
	private CollectionChangeSupport changeSupport = new CollectionChangeSupport();
	private PropertyChangeListener elementsListener = new PropertyChangeListener() {
		
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			fireChangeEvent();
		}

	};
	
	public BoundListDecorator(List<T> decorated) {
		this.decorated = decorated;
	}
	
	private void fireChangeEvent() {
		changeSupport.fireEvent(new CollectionChangeEvent(this));
	}
	
	@Override
	public T get(int index) {
		T result = decorated.get(index);
		if (result instanceof PropertyChangeEmitter) {
			((PropertyChangeEmitter) result).addPropertyChangeListener(elementsListener);
		}
		return result;
	}

	@Override
	public void add(int index, T element) {
		decorated.add(index, element);
		if (element instanceof PropertyChangeEmitter) {
			((PropertyChangeEmitter) element).addPropertyChangeListener(elementsListener);
		}
		fireChangeEvent();
	}

	@Override
	public T remove(int index) {
		T result = decorated.remove(index);
		if (result instanceof PropertyChangeEmitter) {
			((PropertyChangeEmitter) result).removePropertyChangeListener(elementsListener);
		}
		fireChangeEvent();
		return result;
	}

	@Override
	public T set(int index, T element) {
		T result = decorated.set(index, element);
		if (element instanceof PropertyChangeEmitter) {
			((PropertyChangeEmitter) element).addPropertyChangeListener(elementsListener);
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

}
