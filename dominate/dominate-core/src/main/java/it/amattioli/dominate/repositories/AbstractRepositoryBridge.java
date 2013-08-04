package it.amattioli.dominate.repositories;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.Specification;

import java.io.Serializable;
import java.util.List;

/**
 * An abstract implementation for a repository bridge.
 * 
 * @author andrea
 *
 * @param <I>
 * @param <T>
 */
public abstract class AbstractRepositoryBridge<I extends Serializable, T extends Entity<I>> implements Repository<I,T> {
	private Repository<I,T> bridged;
	
	protected AbstractRepositoryBridge(Repository<I,T> bridged) {
		this.bridged = bridged;
	}

	protected Repository<I, T> getBridged() {
		return bridged;
	}

	public T get(I id) {
		return bridged.get(id);
	}

	public T getByPropertyValue(String propertyName, Object value) {
		return bridged.getByPropertyValue(propertyName, value);
	}

	public int getFirst() {
		return bridged.getFirst();
	}

	public int getLast() {
		return bridged.getLast();
	}

	public String getOrderProperty() {
		return bridged.getOrderProperty();
	}

	public boolean isRemoveAllowed() {
		return bridged.isRemoveAllowed();
	}

	public boolean isReverseOrder() {
		return bridged.isReverseOrder();
	}

	@Override
	public void addOrder(String property, boolean reverse) {
		bridged.addOrder(property, reverse);
	}

	@Override
	public void removeLastOrder() {
		bridged.removeLastOrder();
	}

	public List<T> list() {
		return bridged.list();
	}

	public List<T> list(Specification<T> spec) {
		return bridged.list(spec);
	}

	public void put(T object) {
		bridged.put(object);
	}

	public void refresh(I objectId) {
		bridged.refresh(objectId);
	}

	public void refresh(T object) {
		bridged.refresh(object);
	}

	public void remove(I objectId) {
		bridged.remove(objectId);
	}

	public void remove(T object) {
		bridged.remove(object);
	}

	public void setFirst(int first) {
		bridged.setFirst(first);
	}

	public void setLast(int last) {
		bridged.setLast(last);
	}

	public void setOrder(String property, boolean reverse) {
		bridged.setOrder(property, reverse);
	}
	
}
