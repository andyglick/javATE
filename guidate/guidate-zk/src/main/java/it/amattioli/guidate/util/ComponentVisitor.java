package it.amattioli.guidate.util;

import java.util.Collection;

import org.zkoss.zk.ui.Component;

public abstract class ComponentVisitor {

	public void visit(Component comp) {
		preVisit(comp);
		for (Component curr: (Collection<Component>)comp.getChildren()) {
			visit(curr);
		}
		postVisit(comp);
	}

	protected abstract void preVisit(Component comp);

	protected abstract void postVisit(Component comp);
	
}
