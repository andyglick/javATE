package it.amattioli.guidate.browsing;

import it.amattioli.guidate.converters.Converters;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

public class BrowserListCell extends Listcell {
	private String propertyName;
	private Object converter;
	private TypeConverter typeConverter;

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String property) {
		this.propertyName = property;
	}

	public Object getConverter() {
		return converter;
	}

	public void setConverter(Object converter) {
		this.converter = converter;
	}
	
	public TypeConverter getTypeConverter() {
		if (typeConverter == null) {
			typeConverter = Converters.createFrom(converter);
		}
		return typeConverter;
	}
	
	protected String getStringValue() throws Exception {
		String sVal = "";
        try {
            Object val = PropertyUtils.getProperty(getData(), getPropertyName());
            sVal = (String)getTypeConverter().coerceToUi(val, this);
        } catch(NestedNullException e) {
            sVal = "";
        }
        return sVal;
	}
	
	protected void createCellContent() throws Exception {
        setLabel(getStringValue());
	}

	private Object getData() {
		return ((Listitem)getParent()).getValue();
	}
	
	public Object getBackBean() {
		return getData();
	}
}
