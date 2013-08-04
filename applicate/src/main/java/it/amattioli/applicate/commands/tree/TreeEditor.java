package it.amattioli.applicate.commands.tree;

import it.amattioli.applicate.browsing.TreePath;
import it.amattioli.applicate.editors.BeanEditor;
import it.amattioli.applicate.selection.Selector;
import it.amattioli.dominate.Entity;

import java.io.Serializable;
import java.util.List;

public interface TreeEditor<I extends Serializable, T extends Entity<I>> extends Selector<T> {

	public T getRoot();
	
	public void setRoot(T root);

	public List<T> getChildrenOf(T target);

	public T getParentOf(T target);

	public TreePath getPathOf(T target);

	public T getTargetOf(TreePath path);

	public void select(TreePath path);

	public void select(T selected);

	public void deselect();

	public T getSelectedObject();

	public TreePath getSelectedPath();

	public T addChild();
	
	public T addSibling();
	
	public T remove();
	
	public void moveDown();
	
	public void moveUp();
	
	public void addTreeListener(TreeEventListener listener);
	
	public void removeTreeListener(TreeEventListener listener);
	
	public BeanEditor<T> getNodeEditor(T node);
	
	public BeanEditor<T> getNodeEditor(TreePath path);

}