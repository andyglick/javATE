package it.amattioli.applicate.browsing;

import java.util.ArrayList;
import java.util.List;

import it.amattioli.dominate.EntityImpl;

public class CompositeStub extends EntityImpl {
	private CompositeStub parent;
	private List<CompositeStub> children = new ArrayList<CompositeStub>();

	public CompositeStub() {
		
	}
	
	public CompositeStub(Long id) {
		setId(id);
	}
	
	public CompositeStub getParent() {
		return parent;
	}

	public void setParent(CompositeStub parent) {
		this.parent = parent;
	}

	public List<CompositeStub> getChildren() {
		return children;
	}

	public void setChildren(List<CompositeStub> children) {
		this.children = children;
	}
	
	public void addChild(CompositeStub child) {
		this.children.add(child);
		child.setParent(this);
	}
}
