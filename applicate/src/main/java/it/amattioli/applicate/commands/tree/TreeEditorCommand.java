package it.amattioli.applicate.commands.tree;

import java.io.Serializable;
import java.util.List;

import it.amattioli.applicate.browsing.TreePath;
import it.amattioli.applicate.commands.AbstractCommand;
import it.amattioli.applicate.editors.BeanEditor;
import it.amattioli.applicate.selection.SelectionListener;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.Repository;
import it.amattioli.dominate.RepositoryRegistry;

public class TreeEditorCommand<I extends Serializable, T extends Entity<I>> extends AbstractCommand implements TreeEditor<I,T> {
	private DefaultTreeEditor<I, T> editor = new DefaultTreeEditor<I, T>();
	private Repository<I, T> repository;
	
	public TreeEditorCommand() {
		
	}

	public TreeEditorCommand(Repository<I,T> repository) {
		this();
		this.repository = repository;
	}
	
	public TreeEditorCommand(Class<T> entityClass) {
		this(RepositoryRegistry.instance().getRepository(entityClass));
	}
	
	public void setRoot(T root) {
		editor.setTreeManager(new DefaultTreeManager<T>(root));
	}
	
	public void setRootId(I rootId) {
		T root = repository.get(rootId);
		setRoot(root);
	}

	public void deselect() {
		editor.deselect();
	}

	public List<T> getChildrenOf(T target) {
		return editor.getChildrenOf(target);
	}

	public T getParentOf(T target) {
		return editor.getParentOf(target);
	}

	public TreePath getPathOf(T target) {
		return editor.getPathOf(target);
	}

	public T getRoot() {
		return editor.getRoot();
	}

	public T getSelectedObject() {
		return editor.getSelectedObject();
	}

	public TreePath getSelectedPath() {
		return editor.getSelectedPath();
	}

	public T getTargetOf(TreePath path) {
		return editor.getTargetOf(path);
	}

	public void select(TreePath path) {
		editor.select(path);
	}

	public void select(T selected) {
		editor.select(selected);
	}

	@Override
	public T addChild() {
		return editor.addChild();
	}
	
	@Override
	public T addSibling() {
		return editor.addSibling();
	}

	@Override
	public T remove() {
		return editor.remove();
	}

	public void moveDown() {
		editor.moveDown();
	}

	public void moveUp() {
		editor.moveUp();
	}

	@Override
	public BeanEditor<T> getNodeEditor(TreePath path) {
		return editor.getNodeEditor(path);
	}

	@Override
	public BeanEditor<T> getNodeEditor(T node) {
		return editor.getNodeEditor(node);
	}

	@Override
	public void addTreeListener(TreeEventListener listener) {
		editor.addTreeListener(listener);
	}

	@Override
	public void removeTreeListener(TreeEventListener listener) {
		editor.removeTreeListener(listener);
	}

	@Override
	public void addSelectionListener(SelectionListener listener) {
		editor.addSelectionListener(listener);
	}

	@Override
	public void removeSelectionListener(SelectionListener listener) {
		editor.removeSelectionListener(listener);
	}
	
}
