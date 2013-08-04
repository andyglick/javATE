package it.amattioli.guidate.editing;


import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Listitem;

public class ChangeListitemComposer extends AbstractChangeRowComposer {

	public ChangeListitemComposer() {
		super();
	}

	@Override
	protected boolean isRow(Component cmp) {
		return cmp instanceof Listitem;
	}

}