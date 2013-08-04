package it.amattioli.guidate.properties;

import org.apache.commons.beanutils.PropertyUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Checkbox;

public class CheckboxComposer extends PropertyComposer {
	
	public CheckboxComposer() {
		setConverter(new CheckBoxConverter());
	}
	
	public CheckboxComposer(String propertyName) {
		setPropertyName(propertyName);
		setConverter(new CheckBoxConverter());
	}
	
	public void onCheck(Event evt) throws Exception {
		Component comp = evt.getTarget();
		Boolean val = ((Checkbox)comp).isChecked();
		PropertyUtils.setProperty(getBackBean(comp), getPropertyName(), val);
	}

	@Override
	protected String getComponentValueAttribute(Component comp) {
		return "checked";
	}
	
	/**
	 * Needed because checkBox does not support null in "checked" property
	 * 
	 * @author andrea
	 *
	 */
	private static class CheckBoxConverter implements TypeConverter {

		@Override
		public Object coerceToBean(Object val, Component comp) {
			return val;
		}

		@Override
		public Object coerceToUi(Object val, Component comp) {
			return val == null ? false : val;
		}

	}

}
