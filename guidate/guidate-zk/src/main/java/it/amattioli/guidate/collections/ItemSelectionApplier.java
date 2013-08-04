package it.amattioli.guidate.collections;

import org.zkoss.zk.ui.Component;

import it.amattioli.guidate.util.ComponentVisitor;

public class ItemSelectionApplier extends ComponentVisitor {
	private String id;
	
	public ItemSelectionApplier(String id) {
		this.id = id;
	}
	
	@Override
	protected void postVisit(Component comp) {
		ItemSelectionComposer composer = new ItemSelectionComposer(id);
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
