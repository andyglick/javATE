package it.amattioli.guidate.converters;

import it.amattioli.dominate.Described;
import it.amattioli.dominate.Entity;
import it.amattioli.dominate.LocalDescribed;
import it.amattioli.encapsulate.money.Money;
import it.amattioli.encapsulate.percent.Percent;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

public class Converters {
	private static final String CONVERTER_ATTRIBUTE = "it.amattioli.guidate.converters.Converters.componentConverter";
	private static Map<String, Class<? extends TypeConverter>> converterClasses = new HashMap<String, Class<? extends TypeConverter>>();
	private static Map<Class<?>, Class<? extends TypeConverter>> classConverters = new HashMap<Class<?>, Class<? extends TypeConverter>>();
	
	public static void register(String name, Class<?> convertedClass, Class<? extends TypeConverter> converterClass) {
		if (name != null) {
			converterClasses.put(name, converterClass);
		}
		if (convertedClass != null) {
			classConverters.put(convertedClass, converterClass);
		}
	}
	
	static {
		register("enum",           Enum.class,            EnumConverter.class);
		register("entity",         Entity.class,          EntityConverter.class);
		register("day",            Date.class,            DayConverter.class);
		register("int",            Integer.class,         IntConverter.class);
		register(null,             int.class,             IntConverter.class);
		register(null,             Long.class,            IntConverter.class);
		register(null,             long.class,            IntConverter.class);
		register(null,             String.class,          NullConverter.class);
		register("yesno",          Boolean.class,         YesNoConverter.class);
		register(null,             boolean.class,         YesNoConverter.class);
		register("decimal",        BigDecimal.class,      DecimalConverter.class);
		register("percent",        Percent.class,         PercentConverter.class);
		register("money",          Money.class,           MoneyConverter.class);
		register("locale",         Locale.class,          LocaleConverter.class);
		register("described",      Described.class,       DescribedConverter.class);
		register("localDescribed", LocalDescribed.class,  DescribedConverter.class);
	}
	
	/**
	 * Register all the converters contained in an XML configuration file.<p>
	 * The provided file should contain a &lt;converter&gt; element for each
	 * converter that you want to register. The element attributes must be:
	 * <ul>
	 * 	<li> name - 
	 *  <li> converted -
	 *  <li> converter -
	 * </ul>
	 * These attributes are the same as the parameters of the 
	 * {@link #register(String, Class, Class)} method.
	 * 
	 * @param is the input stream from which the XML must be read
	 */
	public static void register(InputStream is) {
		try {
    		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
    		ConverterXmlReader reader = new ConverterXmlReader();
    		parser.parse(is, reader);
		} catch(SAXException e) {
			throw new RuntimeException(e);
		} catch(ParserConfigurationException e) {
			throw new RuntimeException(e);
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void register(String resourceName) {
		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
		register(input);
	}

	private Converters() {
		
	}
	
	/**
	 * Creates a TypeConverter from an Object.<p> 
	 * If the object is already a TypeConverter, the object itself is returned.<p> 
	 * If the object is a string it can be:
	 * <ul>
	 *     <li>the name of a class that implements TypeConverter</li>
	 *     <li>one of: "enum", "entity", "day", "int" to create one of the pre-defined converters
	 * </ul> 
	 * If the object is null or an empty string a AutoConverter is returned.<p>
	 * 
	 * @param converter
	 * @return
	 */
	public static TypeConverter createFrom(Object converter) {
		if (converter == null) {
			return new AutoConverter();
		} else if (converter instanceof TypeConverter) {
			return (TypeConverter)converter;
		} else if (converter instanceof String) {
			Class<? extends TypeConverter> converterClass = null; 
			if (converterClasses.containsKey(converter)) {
				converterClass = converterClasses.get(converter);
			} else {
				try {
					converterClass = (Class<? extends TypeConverter>)Class.forName((String)converter);
				} catch (ClassNotFoundException e) {
					// TODO handle exception
					throw new RuntimeException(e);
				}
			}
			return instantiateConverterClass(converterClass);
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	public static TypeConverter createFor(Class<?> propertyClass) {
		for (Class<?> curr: classConverters.keySet()) {
			if (curr.isAssignableFrom(propertyClass)) {
				return instantiateConverterClass(classConverters.get(curr));
			}
		}
		return new NullConverter();
	}

	private static TypeConverter instantiateConverterClass(Class<? extends TypeConverter> converterClass) {
		try {
			return (TypeConverter)converterClass.newInstance();
		} catch (InstantiationException e) {
			// TODO handle exception
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			// TODO handle exception
			throw new RuntimeException(e);
		}
	}
	
	public static void setToComponent(Component comp, TypeConverter converter) {
		comp.setAttribute(CONVERTER_ATTRIBUTE, converter);
	}
	
	public static TypeConverter getFromComponent(Component comp) {
		return (TypeConverter)comp.getAttribute(CONVERTER_ATTRIBUTE);
	}
	
}
