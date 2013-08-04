package it.amattioli.guidate.properties;

import it.amattioli.dominate.properties.PropertyChangeEmitter;
import it.amattioli.guidate.containers.BackBeans;
import it.amattioli.guidate.converters.Converters;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericComposer;
import org.zkoss.zkplus.databind.TypeConverter;

/**
 * A composer that is able to bind a component attribute to the property of
 * the back-bean so that every time the property of the back-bean changes
 * the component attribute is updated. To obtain this effect the back-bean
 * must send a PropertyChangeEvent every time the property value changes.<p>
 * 
 * The name of the property of the back-bean can be provided in two ways:
 * <ul>
 *     <li>Using the {@link #setPropertyName(String)} method
 *     <li>Using custom-attributes as specified by {@link PropertyNameRetriever}
 * <ul>
 * 
 * Subclasses must define the {@link #getComponentValueAttribute(Component)}
 * method to provide the name of the attribute of the component that is bound. 
 *  
 * @author andrea
 *
 */
public abstract class PropertyComposer extends GenericComposer {
	private static final String BINDING_UPDATER_ATTRIBUTE = "it.amattioli.guidate.properties.PropertyComposer.bindingUpdater";
	private String propertyName;
	private Class<?> propertyClass;
	private TypeConverter converter;

	public PropertyComposer() {
		super();
	}
	
	protected abstract String getComponentValueAttribute(Component comp);

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		if (propertyName == null) {
			this.propertyName = (new PropertyNameRetriever(comp)).retrieve();
		}
	}

	protected void registerPropertyChangeListener(Component comp) {
		Object backBean = getBackBean(comp);
		if (backBean instanceof PropertyChangeEmitter) {
			PropertyChangeEmitter emitter = (PropertyChangeEmitter)backBean;
			BindingUpdater bindingUpdater = new BindingUpdater(getPropertyName(), comp);
			emitter.addPropertyChangeListener(bindingUpdater);
			comp.setAttribute(BINDING_UPDATER_ATTRIBUTE + getComponentValueAttribute(comp), bindingUpdater);
		}
	}

	protected Class<?> getPropertyClass() {
		return propertyClass;
	}

	protected void setPropertyClass(Class<?> propertyClass) {
		this.propertyClass = propertyClass;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Object getBackBean(Component comp) {
		return BackBeans.findBackBean(comp);
	}

	protected TypeConverter getConverter(Component comp) {
		TypeConverter converter = Converters.getFromComponent(comp);
		if (converter == null) {
			if (this.converter == null) {
				Object converterAttribute = comp.getAttribute("typeConverter");
				converter = Converters.createFrom(converterAttribute);
			} else {
				converter = this.converter;
			}
			Converters.setToComponent(comp, converter);
		}
		return converter;
	}

	protected void setConverter(TypeConverter converter) {
		this.converter = converter;
	}
	
	protected String getCompletePropertyName(Component comp) {
		String result = getPropertyName();
		Integer propertyIndex = PropertyNameRetriever.findPropertyIndex(comp);
		if (propertyIndex != null) {
			result = result + "[" + propertyIndex + "]";
		}
		return result;
	}

	public void onBindingUpdate(Event evt) throws Exception {
		Component comp = evt.getTarget();
		Object val = getPropertyValue(comp);
		val = getConverter(comp).coerceToUi(val, comp);
		setComponentValue(comp, val);
	}

	protected void setComponentValue(Component comp, Object val) throws Exception {
		PropertyUtils.setProperty(comp, getComponentValueAttribute(comp), val);
	}
	
	protected Object getPropertyValue(Component comp) throws Exception {
		try {
			Object backBean = getBackBean(comp);
			if (backBean != null) {
				return PropertyUtils.getProperty(backBean, getCompletePropertyName(comp));
			}
			return null;
		} catch (NestedNullException e) {
			return null;
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	public void onCreate(Event evt) throws Exception {
		Component comp = evt.getTarget();
		// can't be in doAfterCompose because it cannot retrieve the backbean there
		registerPropertyChangeListener(comp);
		Events.sendEvent(new Event(BindingUpdater.ON_BINDING_UPDATE, comp));
	}

}