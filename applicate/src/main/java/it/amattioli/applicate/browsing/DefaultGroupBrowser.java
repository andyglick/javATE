package it.amattioli.applicate.browsing;

import it.amattioli.applicate.commands.CommandEvent;
import it.amattioli.applicate.selection.SelectionEvent;
import it.amattioli.applicate.selection.SelectionListener;
import it.amattioli.applicate.selection.SelectionSupport;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.groups.EntityGroup;
import it.amattioli.dominate.groups.EntityGroupFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DefaultGroupBrowser<I extends Serializable, T extends Entity<I>> implements GroupBrowser<I,T> {
	private EntityGroupFactory<I,T> groupFactory;
	private List<EntityGroup<I, T>> groups;
	private ContentChangeSupport contentChange = new ContentChangeSupport();
	private Selection selection;
	private List<Selection> selections = new ArrayList<Selection>();
	private SelectionSupport selectionSupport = new SelectionSupport();
	private boolean multiple = false;
	
	public DefaultGroupBrowser() {
		
	}
	
	public DefaultGroupBrowser(EntityGroupFactory<I, T> groupFactory) {
		this.groupFactory = groupFactory;
	}
	
	public void setGroupFactory(EntityGroupFactory<I, T> groupFactory) {
		this.groupFactory = groupFactory;
		invalidateContent();
	}

	private void invalidateContent() {
		groups = null;
		selection = null;
		fireContentChange();
	}
	
	public EntityGroupFactory<I, T> getGroupFactory() {
		return groupFactory;
	}
	
	@Override
	public List<EntityGroup<I, T>> getGroups() {
		if (groups == null) {
			groups = groupFactory.createGroups();
		}
		return groups;
	}
	
	@Override
	public EntityGroup<I, T> getGroup(int index) {
		return getGroups().get(index);
	}
	
	@Override
	public int getGroupSize(int index) {
		return getGroup(index).size();
	}

	@Override
	public T getMember(int groupIndex, int memberIndex) {
		return getGroup(groupIndex).getMember(memberIndex);
	}

	@Override
	public void addContentChangeListener(ContentChangeListener listener) {
		contentChange.addContentChangeListener(listener);
	}

	@Override
	public void removeContentChangeListener(ContentChangeListener listener) {
		contentChange.removeContentChangeListener(listener);
	}
	
	@Override
	public void release() {
		contentChange.disable();
	}

	@Override
	public void commandDone(CommandEvent e) {
		invalidateContent();
	}
	
	protected void fireContentChange() {
		contentChange.notifyContentChangeListeners(new ContentChangeEvent(this));
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
		selectionSupport.addSelectionListener(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		selectionSupport.removeSelectionListener(listener);
	}

	@Override
	public boolean isMultiple() {
		return multiple;
	}

	@Override
	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	@Override
	public T getSelectedObject() {
		if (selection == null) {
			return null;
		}
		return getMember(selection.getGroupIndex(), selection.getMemberIndex());
	}

	@Override
	public List<T> getSelectedObjects() {
		List<T> result = new ArrayList<T>();
		for (Selection curr: getSelections()) {
			result.add(getMember(curr.getGroupIndex(), curr.getMemberIndex()));
		}
		return result;
	}

	@Override
	public Selection getSelection() {
		return selection;
	}

	@Override
	public List<Selection> getSelections() {
		return selections;
	}

	@Override
	public void select(int groupIndex, int memberIndex) {
		if (getGroups().size() <= groupIndex) {
			throw new ArrayIndexOutOfBoundsException();
		}
		if (getGroups().get(groupIndex).size() <= memberIndex) {
			throw new ArrayIndexOutOfBoundsException();
		}
		Selection selected = new Selection(groupIndex, memberIndex);
		if (!isMultiple()) {
			selections.clear();
		}
		if (selections.contains(selected)) {
			selections.remove(selected);
		} else {
			selections.add(0, selected);
		}
		if (selections.isEmpty()) {
			this.selection = null;
		} else {
			this.selection = selections.get(0);
		}
		selectionSupport.notifySelectionListeners(new SelectionEvent(this));
	}

	@Override
	public void select(T toBeSelected) {
		if (toBeSelected == null) {
			this.selection = null;
			selections.clear();
			selectionSupport.notifySelectionListeners(new SelectionEvent(this));
		} else {
			for (EntityGroup<I, T> curr: getGroups()) {
				if (curr.contains(toBeSelected)) {
					select(getGroups().indexOf(curr),curr.list().indexOf(toBeSelected));
					return;
				}
			}
		}
	}
	
	@Override
	public boolean isSelected(int index, int i) {
		return getSelections().contains(new Selection(index, i));
	}
	
	@Override
	public void selectGroup(int index) {
		if (getGroups().size() <= index) {
			throw new ArrayIndexOutOfBoundsException();
		}
		boolean groupWasSelected = isGroupSelected(index);
		int groupSize = getGroup(index).size();
		for (int i=0; i < groupSize; i++) {
			if (groupWasSelected || !isSelected(index, i)) {
				select(index, i);
			}
		}
	}

	@Override
	public void selectGroup(EntityGroup<I, T> toBeSelected) {
		int groupIndex = getGroups().indexOf(toBeSelected);
		if (groupIndex >= 0) {
			selectGroup(groupIndex);
		}
	}

	@Override
	public List<Integer> getSelectedGroupsIndex() {
		List<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < getGroups().size(); i++) {
			if (isGroupSelected(i)) {
				result.add(i);
			}
		}
		return result;
	}

	@Override
	public boolean isGroupSelected(int index) {
		return getSelectedObjects().containsAll(getGroup(index).list()) && !getGroup(index).list().isEmpty();
	}

	@Override
	public void deselect() {
		select((T)null);
	}

}
