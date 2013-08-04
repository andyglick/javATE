package it.amattioli.guidate.properties;

import it.amattioli.dominate.properties.PropertyChangeEmitter;
import it.amattioli.guidate.containers.BackBeans;
import it.amattioli.guidate.converters.Converters;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.apache.commons.beanutils.PropertyUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericComposer;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.impl.InputElement;

public abstract class ShowPropertyComposer extends GenericComposer {
	private static final String ON_BINDING_UPDATE = "onBindingUpdate";
	private String propertyName;
	private Class<?> propertyClass;
	private TypeConverter converter;
	private InputElement component;
	private PropertyChangeListener propertyChangeListener;
	
	public ShowPropertyComposer() {
	}
	
	public ShowPropertyComposer(String propertyName) {
		this.propertyName = propertyName;
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		this.component = (InputElement)comp;
		if (propertyName == null) {
			this.propertyName = component.getId();
		}
		registerPropertyChangeListener();
	}

	private void registerPropertyChangeListener() {
		if (getBackBean() instanceof PropertyChangeEmitter) {
			PropertyChangeEmitter emitter = (PropertyChangeEmitter)getBackBean();
			propertyChangeListener = new PropertyChangeListener() {

				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					if (propertyName.equals(evt.getPropertyName())) {
						Events.sendEvent(new Event(ON_BINDING_UPDATE, component));
					}
				}
				
			};
			emitter.addPropertyChangeListener(propertyChangeListener);
		}
	}

	public Class<?> getPropertyClass() {
		return propertyClass;
	}
	
	public void setPropertyClass(Class<?> propertyClass) {
		this.propertyClass = propertyClass;
	}

	public Object getBackBean() {
		return BackBeans.findBackBean(component);
	}
	
	public TypeConverter getConverter() {
		if (converter == null) {
			Object converterAttribute = component.getAttribute("typeConverter");
			converter = Converters.createFrom(converterAttribute);
		}
		return converter;
	}
	
	public void setConverter(TypeConverter converter) {
		this.converter = converter;
	}
	
	public void onBindingUpdate(Event evt) throws Exception {
		Object val = PropertyUtils.getProperty(getBackBean(),propertyName);
		val = getConverter().coerceToUi(val, component);
		PropertyUtils.setProperty(component, "value", val);
	}
	
	public void onCreate(Event evt) throws Exception {
		Events.sendEvent(new Event(ON_BINDING_UPDATE, component));
	}

}
