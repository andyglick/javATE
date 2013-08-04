package it.amattioli.guidate.properties;

import it.amattioli.dominate.properties.PropertyAvailabilityRetriever;
import it.amattioli.dominate.properties.PropertyAvailabilityRetrieverImpl;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ObjectUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.impl.InputElement;

public class AvailableComposer extends PropertyComposer {

	public AvailableComposer() {
		
	}
	
	@Override
	protected Object getPropertyValue(Component comp) throws Exception {
		Object backBean = getBackBean(comp);
		PropertyAvailabilityRetriever retriever = new PropertyAvailabilityRetrieverImpl(backBean);
		return retriever.isPropertyAvailable(super.getPropertyName());
	}

	@Override
	public String getPropertyName() {
		return PropertyAvailabilityRetrieverImpl.availableProperty(super.getPropertyName());
	}

	@Override
	protected String getComponentValueAttribute(Component comp) {
		return "visible";
	}
	
	@Override
	protected void setComponentValue(Component comp, Object val) throws Exception {
		comp.setVisible((Boolean)val);
		Object oldVal = PropertyUtils.getProperty(comp, getComponentValueAttribute(comp));
		if (!ObjectUtils.equals(oldVal, val)) {
			cleanValidation(comp);
		}
	}

	private void cleanValidation(Component cmp) {
		if (cmp instanceof InputElement) {
			((InputElement)cmp).clearErrorMessage();
		}
		for (Object child: cmp.getChildren()) {
			cleanValidation((Component)child);
		}
	}
	
	protected TypeConverter getConverter(Component comp) {
		return new AvailableConverter();
	}
	
	private static class AvailableConverter implements TypeConverter {

		@Override
		public Object coerceToBean(Object val, Component comp) {
			return val;
		}

		@Override
		public Object coerceToUi(Object val, Component comp) {
			return val == null ? true : (Boolean)val;
		}

	}
}
