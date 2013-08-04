package it.amattioli.guidate.converters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Textbox;

import junit.framework.TestCase;

public class AutoConverterTest extends TestCase {

	public void testStringConverter() {
		TypeConverter converter = new AutoConverter();
		
		Component comp = new Textbox();
		comp.setAttribute("backBean", new BackBean());
		comp.setAttribute("propertyName", "astringproperty");
		
		assertEquals("astring", converter.coerceToUi("astring", comp));
	}
	
	public void testLongConverter() {
		TypeConverter converter = new AutoConverter();
		
		Component comp = new Textbox();
		comp.setAttribute("backBean", new BackBean());
		comp.setAttribute("propertyName", "alongproperty");
		
		assertEquals("1", converter.coerceToUi(new Long(1L), comp));
		assertEquals(new Long(1), converter.coerceToBean("1", comp));
	}
	
	public void testDateConverter() throws Exception {
		TypeConverter converter = new AutoConverter();
		
		Component comp = new Textbox();
		comp.setAttribute("backBean", new BackBean());
		comp.setAttribute("propertyName", "adateproperty");
		comp.setAttribute("conversionFormat", "dd/MM/yyyy");
		
		DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		Date myDate = fmt.parse("04/04/1970");
		assertEquals("04/04/1970", converter.coerceToUi(myDate, comp));
	}
	
	public void testInheritedPropertyConverter() {
		TypeConverter converter = new AutoConverter();
		
		Component comp = new Textbox();
		comp.setAttribute("backBean", new InheritedBackBean());
		comp.setAttribute("propertyName", "alongproperty");
		
		assertEquals("5", converter.coerceToUi(new Long(5L), comp));
		assertEquals(new Long(5), converter.coerceToBean("5", comp));
	}

//
//  A bug in BeanUtils prevents this test correct execution
//
//	public void testEntityIdConverter() {
//		TypeConverter converter = new AutoConverter();
//		
//		Component comp = new Textbox();
//		AnEntity anEntity = new AnEntity();
//		comp.setAttribute("backBean", anEntity);
//		comp.setAttribute("propertyName", "id");
//		
//		assertEquals("5", converter.coerceToUi(new Long(5L), comp));
//		assertEquals(new Long(5), converter.coerceToBean("5", comp));
//	}
	
	public void testDescribedEntityConverter() {
		TypeConverter converter = new AutoConverter();
		
		Component comp = new Textbox();
		comp.setAttribute("backBean", new BackBean());
		comp.setAttribute("propertyName", "adescribedEntityProperty");
		
		assertEquals("description", converter.coerceToUi(new ADescribedEntity(), comp));
	}
	
	public void testDescribedValueConverter() {
		TypeConverter converter = new AutoConverter();
		
		Component comp = new Textbox();
		comp.setAttribute("backBean", new BackBean());
		comp.setAttribute("propertyName", "adescribedValueProperty");
		
		assertEquals("description", converter.coerceToUi(new ADescribedValue(), comp));
	}
}


