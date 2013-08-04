package it.amattioli.guidate.properties;

import org.zkoss.zk.ui.Component;

public class LabelCellComposer extends PropertyComposer {
	
	public LabelCellComposer() {
	}
	
	public LabelCellComposer(String propertyName) {
		setPropertyName(propertyName);
	}
	
	@Override
	protected String getComponentValueAttribute(Component comp) {
		return "label";
	}

}