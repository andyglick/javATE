package it.amattioli.applicate.editors;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import it.amattioli.dominate.properties.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import it.amattioli.applicate.context.LongRunningContext;
import it.amattioli.applicate.editors.BeanEditor;
import it.amattioli.applicate.selection.SelectionEvent;
import it.amattioli.applicate.selection.SelectionListener;
import it.amattioli.applicate.selection.SelectionSupport;
import it.amattioli.dominate.properties.PropertyChangeEmitter;

public class ListEditorImpl<T> implements ListEditor<T>, PropertyChangeEmitter  {
	public static final String EDITING_LIST = "editingList";
	private EditingListManager<T> editingListManager;
	private List<BeanEditor<T>> elementEditors;
	private Integer selectedIndex;
	private boolean emptyRow = false;
	private PropertyChangeSupport pChange = new PropertyChangeSupport(this);
	private PropertyChangeListener editingListChangeListener;
	private SelectionSupport selectionSupport = new SelectionSupport();
	
	public ListEditorImpl() {
		
	}
	
	public ListEditorImpl(EditingListManager<T> editingListManager) {
		setEditingListManager(editingListManager);
	}
	
	public ListEditorImpl(List<T> editingList, Class<T> entityClass) {
		setEditingListManager(new DefaultEditingListManager<T>(editingList, entityClass));
	}
	
	public void setEditingListManager(EditingListManager<T> editingListManager) {
		this.editingListManager = editingListManager;
		editingListChangeListener = new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				initEditors();
			}
			
		};
		this.editingListManager.addPropertyChangeListener(editingListChangeListener);
		//initEditors();
		firePropertyChange(EDITING_LIST, null, null);
	}
	
	protected EditingListManager<T> getEditingListManager() {
		return editingListManager;
	}

	private void initEditors() {
		elementEditors.clear();
		for (T curr : this.editingListManager.getEditingList()) {
			BeanEditor<T> elementEditor = this.editingListManager.createElementEditor(curr);
			elementEditor = LongRunningContext.withCurrentSessionManager(elementEditor);
			elementEditors.add(elementEditor);
		}
	}
	
	/* (non-Javadoc)
	 * @see it.amattioli.applicate.commands.ListEditor#setEditingList(java.util.List)
	 */
	public void setEditingList(List<T> editingList) {
		editingListManager.setEditingList(editingList);
	}

	/* (non-Javadoc)
	 * @see it.amattioli.applicate.commands.ListEditor#getEditingList()
	 */
	public List<T> getEditingList() {
		if (elementEditors == null) {
			elementEditors = new ArrayList<BeanEditor<T>>();
			initEditors();
		}
		return editingListManager.getEditingList();
	}

	@Override
	public int getSize() {
		return getEditingList().size();
	}

	/* (non-Javadoc)
	 * @see it.amattioli.applicate.commands.ListEditor#isEmptyRow()
	 */
	public boolean isEmptyRow() {
		return emptyRow;
	}

	/* (non-Javadoc)
	 * @see it.amattioli.applicate.commands.ListEditor#setEmptyRow(boolean)
	 */
	public void setEmptyRow(boolean emptyRow) {
		this.emptyRow = emptyRow;
	}

	protected void addRowIfEmpty() {
		if (emptyRow && this.editingListManager.getEditingList().isEmpty()) {
			addRow();
		}
	}
	
	/* (non-Javadoc)
	 * @see it.amattioli.applicate.commands.ListEditor#select(int)
	 */
	public void select(int idx) {
		this.selectedIndex = idx;
		selectionSupport.notifySelectionListeners(new SelectionEvent(this));
	}

	/* (non-Javadoc)
	 * @see it.amattioli.applicate.commands.ListEditor#getSelectedIndex()
	 */
	public Integer getSelectedIndex() {
		return selectedIndex;
	}

	@Override
	public T getSelectedObject() {
		if (getSelectedIndex() == null) {
			return null;
		}
		return getEditingList().get(getSelectedIndex());
	}
	
	private List<BeanEditor<T>> getElementEditors() {
		if (elementEditors == null) {
			elementEditors = new ArrayList<BeanEditor<T>>();
			initEditors();
		}
		return elementEditors;
	}
	
	/* (non-Javadoc)
	 * @see it.amattioli.applicate.commands.ListEditor#getElementEditor(int)
	 */
	public BeanEditor<T> getElementEditor(int index) {
		return getElementEditors().get(index);
	}
	
	/* (non-Javadoc)
	 * @see it.amattioli.applicate.commands.ListEditor#addRow()
	 */
	public T addRow(Object... param) {
		List<BeanEditor<T>> elementEditors = getElementEditors();
		BeanEditor<T> newEditor = editingListManager.addRow(param);
		newEditor = LongRunningContext.withCurrentSessionManager(newEditor);
		elementEditors.add(newEditor);
		firePropertyChange(EDITING_LIST, null, getEditingList());
		return newEditor.getEditingBean();
	}
	
	public boolean canDeleteRow() {
		return editingListManager.canDeleteRow(getSelectedIndex());
	}
	
	/* (non-Javadoc)
	 * @see it.amattioli.applicate.commands.ListEditor#deleteRow()
	 */
	public T deleteRow() {
		T deleted = null;
		if (canDeleteRow()) {
			int idx = getSelectedIndex();
			List<BeanEditor<T>> elementEditors = getElementEditors();
			deleted = editingListManager.deleteRow(idx);
			elementEditors.remove(idx);
			addRowIfEmpty();
			if (idx >= getEditingList().size()) {
				if (getEditingList().isEmpty()) {
					this.selectedIndex = null;
				} else {
					this.selectedIndex = getEditingList().size() - 1;
				}
			}
			firePropertyChange(EDITING_LIST, null, getEditingList());
		}
		return deleted;
	}

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		pChange.firePropertyChange(propertyName, oldValue, newValue);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
	    pChange.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
	    pChange.removePropertyChangeListener(listener);
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
		selectionSupport.addSelectionListener(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		selectionSupport.removeSelectionListener(listener);
	}
}
