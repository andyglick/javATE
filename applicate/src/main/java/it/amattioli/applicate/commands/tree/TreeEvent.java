package it.amattioli.applicate.commands.tree;

import it.amattioli.applicate.browsing.TreePath;

public class TreeEvent {
	public enum Type {
		CHILD_ADDED,
		CHILD_REMOVED,
		NODE_CHANGED;
	}
	
	private Type type;
	private TreePath path;

	public TreeEvent(Type type, TreePath path) {
		this.type = type;
		this.path = path;
	}
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public TreePath getPath() {
		return path;
	}

	public void setPath(TreePath path) {
		this.path = path;
	}

}
