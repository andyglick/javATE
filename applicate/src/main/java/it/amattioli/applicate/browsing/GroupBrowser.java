package it.amattioli.applicate.browsing;

import it.amattioli.applicate.commands.CommandListener;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.groups.EntityGroup;

import java.io.Serializable;
import java.util.List;

/**
 * A browser that let you navigate through a set of {@link EntityGroup} and its members.<p>
 * 
 * Selection can work in two distinct modes: single and multiple.<p>
 * 
 * In single selection mode every time you select a member using {@link #select(Entity)} or
 * {@link #select(int, int)} this will be the only one selected. You can retrieve this selection 
 * using {@link #getSelectedObject()}. In single mode {@link #getSelectedObjects()} will always
 * return a list containing one element.<p> 
 * 
 * In multiple selection mode each selection will be maintained in a list that can be retrieved using
 * {@link #getSelectedObjects()} while {@link #getSelectedObject()} will retrieve the last selected
 * object. In multiple mode selecting the same object twice will result in deselecting this object<p>
 * 
 * @author andrea
 *
 * @param <I> The class of the group members id
 * @param <T> The {@link Entity} class of the group members
 */
public interface GroupBrowser<I extends Serializable, T extends Entity<I>> extends Browser<I, T>, CommandListener {

	/**
	 * Retrieves all the available groups
	 * 
	 * @return all the available groups
	 */
	public List<EntityGroup<I, T>> getGroups();
	
	/**
	 * Retrieves the group with a given index inside the {@link #getGroups()} list.
	 * 
	 * @param index the group index
	 * @return the group with a given index
	 */
	public EntityGroup<I, T> getGroup(int index);
	
	/**
	 * Retrieves the size of the group with a given index.
	 * 
	 * @param index the group index
	 * @return the size of the group with the given index
	 */
	public int getGroupSize(int index);
	
	/**
	 * Retrieves a member of a given group.
	 * 
	 * @param groupIndex the index of the group inside the {@link #getGroups()} list
	 * @param memberIndex the index of the member inside the group
	 * @return a member of a given group
	 */
	public T getMember(int groupIndex, int memberIndex);
	
	/**
	 * Select a member of a group using the group and member index.
	 * 
	 * @param groupIndex the index of the group inside the {@link #getGroups()} list
	 * @param memberIndex the index of the member inside the group
	 */
	public void select(int groupIndex, int memberIndex);
	
	/**
	 * Select a member of a group.
	 * 
	 * @param toBeSelected the member to be selected
	 */
	public void select(T toBeSelected);
	
	/**
	 * Select all the members of a group.
	 * 
	 * @param index the index of the group inside the {@link #getGroups()} list
	 */
	public void selectGroup(int index);
	
	/**
	 * Select all the members of a group.
	 * 
	 * @param toBeSelected the group to be selected
	 */
	public void selectGroup(EntityGroup<I, T> toBeSelected);
	
	/**
	 * Removes all the selections
	 */
	public void deselect();
	
	/**
	 * Returns a {@link Selection} object containing the group and member indexes
	 * of the last selected entity.
	 * 
	 * @return
	 */
	public Selection getSelection();
	
	/**
	 * Returns a list of {@link Selection} containing the indexes of all the selected objects.<p>
	 * In single mode the list will contain only the last selected object.
	 * 
	 * @return
	 */
	public List<Selection> getSelections();
	
	/**
	 * Returns a list containing all the selected objects.<p>
	 * In single mode the list will contain only the last selected object.
	 * 
	 * @return
	 */
	public List<T> getSelectedObjects();
	
	/**
	 * Check if an entity is selected.
	 * 
	 * @param groupIndex the group index
	 * @param memberIndex the index of the member inside the group
	 * @return true if the entity with the given indexes is selected
	 */
	public boolean isSelected(int groupIndex, int memberIndex);
	
	/**
	 * Check if a group is selected. A group is considered selected if all its members are
	 * selected.
	 * 
	 * @param index the group index
	 * @return true if the group is selected
	 */
	public boolean isGroupSelected(int index);
	
	/**
	 * Return the indexes of all the groups that are selected as of {@link #isGroupSelected(int)}
	 * 
	 * @return the indexes of all the groups that are selected
	 */
	public List<Integer> getSelectedGroupsIndex();
	
	/**
	 * Set the selection mode. Passing true will set the multiple mode, while passing false will
	 * select the single mode.
	 * 
	 * @param multiple true to set the multiple mode, false to set the single mode
	 */
	public void setMultiple(boolean multiple);
	
	/**
	 * Check if this browser is in multiple mode
	 * 
	 * @return true if this browser is in multiple mode, false if it is in single mode
	 */
	public boolean isMultiple();

	public static class Selection {
		private Integer groupIndex;
		private Integer memberIndex;
		
		public Selection(Integer groupIndex, Integer memberIndex) {
			this.groupIndex = groupIndex;
			this.memberIndex = memberIndex;
		}
		
		public Integer getGroupIndex() {
			return groupIndex;
		}
		
		public Integer getMemberIndex() {
			return memberIndex;
		}

		@Override
		public boolean equals(Object obj) {
			return obj instanceof Selection 
			    && ((Selection)obj).getGroupIndex().equals(getGroupIndex())
			    && ((Selection)obj).getMemberIndex().equals(getMemberIndex());
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((groupIndex == null) ? 0 : groupIndex.hashCode());
			result = prime * result + ((memberIndex == null) ? 0 : memberIndex.hashCode());
			return result;
		}

	}
	
}
