package it.amattioli.workstate.core;

import java.text.*;
import java.util.*;
import java.lang.reflect.*;

import it.amattioli.workstate.exceptions.*;
import it.amattioli.workstate.conversion.ConversionService;

/**
 * A {@link MetaEvent} container.
 * <p>
 * An object of this class allows the management of {@link MetaEvent} instances
 * and the creation of events starting from these.
 * <p>
 * To create events you will need the event name to find the correct
 * {@link MetaEvent} to be used, and the list of the actual parameters.
 * <p>
 * These parameters can be objects compatibles with the class of the formal
 * parameter, or strings. Strings will be converted to the right class using a
 * {@link ConversionService} instance.
 * 
 */
public class EventRepository {
	private Map<String, MetaEvent> events = new HashMap<String, MetaEvent>();
	private ConversionService defaultConversionService;

	/**
	 * Construct a new EventRepository whose default {@link ConversionService}
	 * is passed as a parameter.
	 * 
	 * @param defaultConversionService the default conversion service
	 */
	public EventRepository(ConversionService defaultConversionService) {
		this.defaultConversionService = defaultConversionService;
	}

	/**
	 * Add a {@link MetaEvent} to this repository. The added {@link MetaEvent}
	 * will be referenced later using its tag. If this repository already
	 * conatins a {@link MetaEvent} with the same tag it will be replaced.
	 * 
	 * @param newMetaEvent the meta-event to be added
	 */
	public void addMetaEvent(MetaEvent newMetaEvent) {
		events.put(newMetaEvent.getTag(), newMetaEvent);
	}

	/**
	 * Build a new event given:
	 * <ul>
	 * <li>Its name (tag)
	 * <li>Its actual parameters
	 * <li>A conversion service to transform string parameters to real
	 * parameters
	 * </ul>
	 * In the parameters map the keys are the parameter names while the values
	 * are the parameter values or strings that can be converted to the
	 * parameter values using the conversion service. If a keys has not a
	 * corresponding formal parameter definition in the meta-event the map entry
	 * will be ignored. If no conversion service is passed the default one will
	 * be used.
	 * 
	 * @param name The event name (tag)
	 * @param stringParameters A map containing the event actual parameters. The keys are the
	 *            parameter names while the values are the parameter values or
	 *            strings that can be converted to the parameter values using
	 *            the conversion service
	 * @param customConversionService A conversion service. If null the default one will be used
	 * @return the built event. If the passed event name does not correspond to
	 *         any {@link MetaEvent} contained in this repository null will be
	 *         returned
	 * @throws ClassCastException if a parameter class is not a string and is not compatible
	 *             with the formal parameter definition
	 * @throws WorkflowException if the conversion service is not able to convert a string in
	 *             the real parameter
	 */
	public Event buildEvent(String name, Map<String, Object> stringParameters, ConversionService customConversionService) throws WorkflowException {
		ConversionService conversionService = customConversionService;
		if (conversionService == null) {
			conversionService = defaultConversionService;
		}
		MetaEvent metaEvent = (MetaEvent) events.get(name);
		if (metaEvent == null) {
			return null;
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		for (Map.Entry<String, Object> curr: stringParameters.entrySet()) {
			MetaAttribute param = metaEvent.getParameter((String) curr.getKey());
			if (param != null) {
				// logger.debug("Building event parameter name="+curr.getKey()+" value="+curr.getValue());
				Object paramValue = constructParamValue(param, curr.getValue(), conversionService);
				parameters.put(curr.getKey(), paramValue);
			} else {
				// logger.warn("Ignoring event parameter name="+curr.getKey()+" value="+curr.getValue());
			}
		}
		return metaEvent.newEvent(parameters);
	}

	private Object constructParamValue(MetaAttribute param, Object value,
			ConversionService conversionService) throws WorkflowException {
		Object paramValue;
		if (param.getAttributeClass().isArray()) {
			Object[] values;
			if (!value.getClass().isArray()) {
				values = new Object[] { value };
			} else {
				values = (Object[]) value;
			}
			paramValue = Array.newInstance(param.getAttributeClass()
					.getComponentType(), values.length);
			for (int i = 0; i < values.length; i++) {
				Object currValue = convertParam(param, values[i],
						conversionService);
				Array.set(paramValue, i, currValue);
			}
		} else {
			paramValue = convertParam(param, value, conversionService);
		}
		return paramValue;
	}

	private Object convertParam(MetaAttribute param, Object value, ConversionService conversionService) throws WorkflowException {
		if (value instanceof String) {
			return convertStringParam(param, value, conversionService);
		} else {
			return value;
		}
	}

	private Object convertStringParam(MetaAttribute param, Object value, ConversionService conversionService) throws WorkflowException {
		Object paramValue;
		try {
			Class<?> paramClass = param.getAttributeClass();
			if (paramClass.isArray()) {
				paramClass = paramClass.getComponentType();
			}
			paramValue = conversionService.parse((String) value, paramClass);
		} catch (ParseException e) {
			WorkflowException exc = new WorkflowException("", new KeyedMessage("PARSE_ERROR"));
			exc.addParameter("tag", param.getTag());
			exc.addParameter("type", param.getAttributeClass().toString());
			throw exc;
		}
		return paramValue;
	}

	/**
	 * Build a new event. The same as
	 * {@link #buildEvent(String, Map, ConversionService)} but with default
	 * conversion service.
	 * 
	 */
	public Event buildEvent(String name, Map<String, Object> stringParameters) throws WorkflowException {
		return buildEvent(name, stringParameters, null);
	}

	/**
	 * Check if this repository contains a {@link MetaEvent} with the given tag.
	 * 
	 * @param name the meta-event tag
	 * @return true if this repository contains a meta-event with the given tag,
	 *         false otherwise
	 * 
	 */
	public boolean containsEvent(String name) {
		return events.containsKey(name);
	}

}
