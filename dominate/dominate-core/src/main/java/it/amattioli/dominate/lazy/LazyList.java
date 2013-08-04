package it.amattioli.dominate.lazy;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;

public abstract class LazyList<T> extends AbstractList<T> implements Lazy {
	private List<T> target;
	
	protected abstract List<T> findTarget();
	
	private void checkTarget() {
		if (!wasLoaded()) {
			target = findTarget();
		}
	}
	
	@Override
	public Iterator<T> iterator() {
		checkTarget();
		return target.iterator();
	}

	@Override
	public int size() {
		checkTarget();
		return target.size();
	}

	@Override
	public boolean add(T e) {
		checkTarget();
		return target.add(e);
	}

	@Override
	public T get(int index) {
		checkTarget();
		return target.get(index);
	}

	@Override
	public boolean wasLoaded() {
		return target != null;
	}

}