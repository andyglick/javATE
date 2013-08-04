package it.amattioli.guidate.properties;

import org.zkoss.zk.ui.Component;

/**
 * A composer that binds the "value" attribute of a component to the property
 * of the back-bean.
 * 
 * @author andrea
 *
 */
public class LabelPropertyComposer extends PropertyComposer {
	public LabelPropertyComposer() {
	}
	
	public LabelPropertyComposer(String propertyName) {
		setPropertyName(propertyName);
	}
	
	@Override
	protected String getComponentValueAttribute(Component comp) {
		return "value";
	}

}
