package it.amattioli.applicate.browsing;

import it.amattioli.applicate.commands.CommandListener;
import it.amattioli.applicate.selection.FilteredSelector;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Specification;

import java.io.Serializable;
import java.util.List;

/**
 * A TreeBrowser allows the navigation through a set of entities arranged as the
 * nodes of a tree structure.
 * The tree structure must have a single root that can be retrieved using the
 * {@link #getRoot()} method.
 * 
 * @author andrea
 *
 * @param <I> the identifier class of the entities that can be browsed
 * 
 * @param <T> the class of the entities that can be browsed
 */
public interface TreeBrowser<I extends Serializable, T extends Entity<I>> extends Browser<I, T>, FilteredSelector<I,T>, CommandListener {

	/**
	 * Retrieves the root of the tree
	 * 
	 * @return
	 */
	public T getRoot();
	
	/**
	 * Retrieves a tree node given its path inside the tree
	 * 
	 * @param path
	 * @return
	 */
	public T getTargetOf(TreePath path);
	
	/**
	 * Retrieves the path of node
	 * 
	 * @param target
	 * @return
	 */
	public TreePath getPathOf(T target);
	
	/**
	 * Retrieves the list of the children of a given node
	 * @param target
	 * @return
	 */
	public List<T> getChildrenOf(T target);
	
	/**
	 * Select a node
	 * 
	 * @param selected
	 */
	public void select(T selected);
	
	/**
	 * Select a node given its path
	 * 
	 * @param path
	 */
	public void select(TreePath path);
	
	public void deselect();
	
	/**
	 * Retrieves the path of the selected object
	 * @return
	 */
	public TreePath getSelectedPath();

	/**
	 * Select next node in tree. If the current selected node has children
	 * the first children will be selected, otherwise the next sibling. If
	 * the current node is the last sibling its parent next sibling will be
	 * selected.
	 * If a specification was set using the {@link #setSpecification(Specification)}
	 * method the next node that satisfy the specification will be selected. 
	 */
	public void next();
	
	/**
	 * Select previous node in tree. If the current selected node is the first
	 * of its sibling its parent will be selected otherwise the first children
	 * of its parent previous sibling will be selected.
	 * If a specification was set using the {@link #setSpecification(Specification)}
	 * method the previous node that satisfy the specification will be selected.
	 */
	public void previous();
	
	/**
	 * Set a specification that can modify the behaviour of the {@link #next()} and
	 * {@link #previous()} methods.
	 *  
	 * @param spec
	 */
	public void setSpecification(Specification<T> spec);
	
	/**
	 * Retrieves the specification set using {@link #setSpecification(Specification)}
	 * 
	 * @return the specification set using {@link #setSpecification(Specification)}
	 */
	public Specification<T> getSpecification();
	
}
