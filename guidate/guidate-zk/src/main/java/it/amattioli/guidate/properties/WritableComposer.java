package it.amattioli.guidate.properties;

import it.amattioli.dominate.properties.PropertyWritabilityRetriever;
import it.amattioli.dominate.properties.PropertyWritabilityRetrieverImpl;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Listbox;

public class WritableComposer extends PropertyComposer {

	public WritableComposer() {
		
	}
	
	@Override
	protected Object getPropertyValue(Component comp) throws Exception {
		Object backBean = getBackBean(comp);
		PropertyWritabilityRetriever retriever = new PropertyWritabilityRetrieverImpl(backBean);
		return retriever.isPropertyWritable(super.getPropertyName());
	}

	@Override
	public String getPropertyName() {
		return PropertyWritabilityRetrieverImpl.writableProperty(super.getPropertyName());
	}

	@Override
	protected String getComponentValueAttribute(Component comp) {
		if (comp instanceof Listbox) {
			return "disabled";
		} else {
			return "readonly";
		}
	}
	
	protected TypeConverter getConverter(Component comp) {
		return new WritableConverter();
	}
	
	private static class WritableConverter implements TypeConverter {

		@Override
		public Object coerceToBean(Object val, Component comp) {
			return val;
		}

		@Override
		public Object coerceToUi(Object val, Component comp) {
			return val == null ? true : !(Boolean)val;
		}

	}
}
