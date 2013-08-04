package it.amattioli.guidate.browsing.tree;

import org.zkoss.zk.ui.Component;

import it.amattioli.guidate.util.ComponentVisitor;

public class TreeItemSelectionApplier extends ComponentVisitor {
	private String id;
	
	public TreeItemSelectionApplier(String id) {
		this.id = id;
	}
	
	@Override
	protected void postVisit(Component comp) {
		TreeItemSelectionComposer composer = new TreeItemSelectionComposer(id);
		try {
			composer.doAfterCompose(comp);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void preVisit(Component comp) {
		// TODO Auto-generated method stub
		
	}
}
