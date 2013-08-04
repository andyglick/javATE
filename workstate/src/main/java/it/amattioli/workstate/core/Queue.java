package it.amattioli.workstate.core;

import java.util.*;

public class Queue<T> {
	private List<T> elements = new LinkedList<T>();

	public void offer(T o) {
		elements.add(o);
	}

	public T poll() {
		if (elements.size() > 0) {
			return elements.remove(0);
		} else {
			return null;
		}
	}
}
