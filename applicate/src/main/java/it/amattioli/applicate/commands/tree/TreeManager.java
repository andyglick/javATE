package it.amattioli.applicate.commands.tree;

import java.util.List;

import it.amattioli.applicate.browsing.TreePath;
import it.amattioli.applicate.editors.BeanEditor;
import it.amattioli.dominate.properties.PropertyChangeEmitter;

public interface TreeManager<T> extends PropertyChangeEmitter {

	public T getRoot();
	
	public void setRoot(T root);
	
	public List<T> getChildrenOf(T target);
	
	public T getParentOf(T target);
	
	public TreePath getPathOf(T target);
	
	public T getTargetOf(TreePath path);
	
	public void addChild(T parent, T child);
	
	public BeanEditor<T> addChild(T parent);
	
	public T remove(T toBeRemoved);
	
	public BeanEditor<T> createNodeEditor(T node);

}
