package it.amattioli.guidate.converters;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.zkoss.zkplus.databind.TypeConverter;

public class ConverterXmlReader extends DefaultHandler {

	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		if ("converter".equals(name)) {
    		String converterName = attributes.getValue("name");
    		String convertedClassName = attributes.getValue("converted");
    		String converterClassName = attributes.getValue("converter");
    		Class<?> convertedClass = null;
    		Class<? extends TypeConverter> converterClass = null;
    		try {
    			if (convertedClassName != null) {
    				convertedClass = Class.forName(convertedClassName);
    			}
    			if (converterClassName != null) {
    				converterClass = (Class<? extends TypeConverter>)Class.forName(converterClassName);
    			}
    		} catch (ClassNotFoundException e) {
    			throw new RuntimeException(e);
    		}
    		Converters.register(converterName, convertedClass, converterClass);
		}
	}

}
