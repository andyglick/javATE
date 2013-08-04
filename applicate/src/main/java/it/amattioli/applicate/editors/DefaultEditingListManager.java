package it.amattioli.applicate.editors;

import java.util.List;

public class DefaultEditingListManager<T> extends AbstractEditingListManager<T> {
	private Class<T> entityClass;
	
	public DefaultEditingListManager(List<T> editingList, Class<T> entityClass) {
		setEditingList(editingList);
		this.entityClass = entityClass;
	}
	
	public Class<T> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public BeanEditor<T> createElementEditor(T curr) {
		return new BeanEditorImpl<T>(curr);
	}
	
	protected T createObject() {
		try {
			return entityClass.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
	
	public BeanEditor<T> addRow(Object... param) {
		T newElement = createObject();
		getEditingList().add(newElement);
		return createElementEditor(newElement);
	}
	
	public boolean canDeleteRow(int idx) {
		return idx >= 0 && idx < getEditingList().size();
	}
	
	public T deleteRow(int idx) {
		return getEditingList().remove(idx);
	}
	
}
