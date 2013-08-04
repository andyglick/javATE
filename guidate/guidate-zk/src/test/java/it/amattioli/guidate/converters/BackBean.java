package it.amattioli.guidate.converters;

import it.amattioli.dominate.EntityImpl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BackBean extends EntityImpl {
	
	public BackBean() {
		setId(5L);
	}
	
	public String getAstringproperty() {
		return "astring";
	}
	
	public Date getAdateproperty() throws ParseException {
		DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		return fmt.parse("04/04/1970");
	}
	
	public Long getAlongproperty() {
		return 1L;
	}
	
	public ADescribedEntity getAdescribedEntityProperty() {
		return new ADescribedEntity();
	}
	
	public ADescribedValue getAdescribedValueProperty() {
		return new ADescribedValue();
	}
	
}
