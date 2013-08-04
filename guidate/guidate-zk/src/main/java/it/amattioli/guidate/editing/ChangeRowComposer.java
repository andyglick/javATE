package it.amattioli.guidate.editing;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Row;

public class ChangeRowComposer extends AbstractChangeRowComposer {

	public ChangeRowComposer() {
		super();
	}

	@Override
	protected boolean isRow(Component cmp) {
		return cmp instanceof Row;
	}

}