package it.amattioli.dominate.memory;

import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.Specification;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class EmptyRepository<I extends Serializable,T extends Entity<I>> implements Repository<I,T> {
	private int first = 0;
	private int last = 0;
	private String orderProperty;
	private boolean reverseOrder;
	
	public T get(I id) {
		return null;
	}

	public int getFirst() {
		return first;
	}

	public int getLast() {
		return last;
	}

	public List<T> list() {
		return Collections.emptyList();
	}

	@Override
	public List<T> list(Specification<T> spec) {
		return Collections.emptyList();
	}

	public void put(T object) {
		throw new UnsupportedOperationException();
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public void setLast(int last) {
		this.last = last;
	}

	public void setOrder(String property, boolean reverse) {
		this.orderProperty = property;
		this.reverseOrder = reverse;
	}

	@Override
	public void addOrder(String property, boolean reverse) {
		// Does nothing
	}

	@Override
	public void removeLastOrder() {
		// Does nothing
	}

	public String getOrderProperty() {
        return orderProperty;
    }

    public boolean isReverseOrder() {
        return reverseOrder;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected EmptyRepository<I,T> clone() throws CloneNotSupportedException {
        return (EmptyRepository<I,T>)super.clone();
    }

    public void refresh(I objectId) {
		// Does nothing
	}

	public void refresh(T object) {
		// Does nothing
	}

	@Override
	public boolean isRemoveAllowed() {
		return false;
	}

	@Override
	public void remove(I objectId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void remove(T object) {
		throw new UnsupportedOperationException();
	}

	public T getByPropertyValue(String propertyName, Object value) {
		return null;
	}
}
