package it.amattioli.guidate.converters;

import it.amattioli.dominate.properties.PropertyClass;
import it.amattioli.dominate.properties.PropertyClassRetrieverImpl;
import it.amattioli.guidate.containers.BackBeans;
import it.amattioli.guidate.properties.PropertyNameRetriever;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

public class AutoConverter implements TypeConverter {
	private TypeConverter converter = new NullConverter();
	private boolean converterFound = false;
	
	@Override
	public Object coerceToBean(Object val, Component comp) {
		if (!converterFound) {
			findConverter(comp);
		}
		return converter.coerceToBean(val, comp);
	}

	@Override
	public Object coerceToUi(Object val, Component comp) {
		if (!converterFound) {
			findConverter(comp);
		}
		return converter.coerceToUi(val, comp);
	}
	
	private void findConverter(Component comp) {
		PropertyNameRetriever pnRetriever = new PropertyNameRetriever(comp);
		String propertyName = pnRetriever.retrieve();
		Object backBean = BackBeans.findBackBean(comp);
		PropertyClass pClass;
		try {
			pClass = new PropertyClassRetrieverImpl(backBean).retrievePropertyClass(propertyName);
		} catch(Exception e) {
			pClass = null;
		}
		if (pClass != null && pClass.getElementClass() != null) {
			converter = Converters.createFor(pClass.getElementClass());
			converterFound = true;
		}
	}

}
