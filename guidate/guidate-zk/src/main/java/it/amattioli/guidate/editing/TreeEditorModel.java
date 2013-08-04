package it.amattioli.guidate.editing;

import java.io.Serializable;
import java.util.List;

import it.amattioli.applicate.browsing.TreePath;
import it.amattioli.applicate.commands.tree.TreeEditor;
import it.amattioli.applicate.commands.tree.TreeEvent;
import it.amattioli.applicate.commands.tree.TreeEventListener;
import it.amattioli.applicate.editors.BeanEditor;
import it.amattioli.dominate.Entity;

import org.zkoss.zul.AbstractTreeModel;
import org.zkoss.zul.event.TreeDataEvent;

public class TreeEditorModel<I extends Serializable, T extends Entity<I>> extends AbstractTreeModel {
	private TreeEditor<I,T> editor;
	private TreeEventListener editorListener;
	
	public TreeEditorModel(TreeEditor<I,T> editorParam) {
		super(editorParam.getNodeEditor(editorParam.getRoot()));
		this.editor = editorParam;
		editorListener = new TreeEventListener() {

			@Override
			public void treeChanged(TreeEvent event) {
				if (TreeEvent.Type.CHILD_ADDED.equals(event.getType())) {
					TreePath path = event.getPath();
					T parent = editor.getParentOf(editor.getTargetOf(path));
					int idx = path.elementAt(path.depth() - 1);
					fireEvent(editor.getNodeEditor(parent), 
					          idx, 
							  idx, 
							  TreeDataEvent.INTERVAL_ADDED);
					T pParent = editor.getParentOf(parent);
					if (pParent != null) {
						int pIdx = path.elementAt(path.depth() - 2);
						fireEvent(editor.getNodeEditor(pParent), 
						          pIdx, 
								  pIdx, 
								  TreeDataEvent.CONTENTS_CHANGED);
					}
				} else if (TreeEvent.Type.CHILD_REMOVED.equals(event.getType())) {
					TreePath path = event.getPath();
					TreePath pPath = path.parentPath();
					T parent = editor.getTargetOf(pPath);
					int idx = path.elementAt(path.depth() - 1);
					fireEvent(editor.getNodeEditor(parent), 
					          idx, 
							  idx, 
							  TreeDataEvent.INTERVAL_REMOVED);
				}
			}
			
		};
		editor.addTreeListener(editorListener);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Object getChild(Object parent, int index) {
		T node = editor.getChildrenOf(((BeanEditor<T>)parent).getEditingBean()).get(index);
		return editor.getNodeEditor(node);
	}

	@Override
	@SuppressWarnings("unchecked")
	public int getChildCount(Object parent) {
		List<T> children = editor.getChildrenOf(((BeanEditor<T>)parent).getEditingBean());
		if (children == null) return 0;
		return children.size();
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean isLeaf(Object node) {
		List<T> children = editor.getChildrenOf(((BeanEditor<T>)node).getEditingBean());
		return children == null || children.isEmpty();
	}

}
