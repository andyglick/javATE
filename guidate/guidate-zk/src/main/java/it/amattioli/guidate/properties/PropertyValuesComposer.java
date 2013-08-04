package it.amattioli.guidate.properties;

import it.amattioli.dominate.properties.PropertyChangeEmitter;
import it.amattioli.dominate.properties.PropertyClass;
import it.amattioli.dominate.properties.PropertyClassRetrieverImpl;
import it.amattioli.dominate.properties.ValuesListerImpl;
import it.amattioli.dominate.validation.Constraint;
import it.amattioli.dominate.validation.DefaultValidator;
import it.amattioli.dominate.validation.Validator;
import it.amattioli.guidate.collections.PropertyValuesModel;
import it.amattioli.guidate.collections.ValueItemRenderer;
import it.amattioli.guidate.containers.BackBeans;
import it.amattioli.guidate.converters.Converters;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ObjectUtils;
import org.hibernate.validator.NotNull;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.util.GenericComposer;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

public class PropertyValuesComposer extends GenericComposer {
	private static final String TYPE_CONVERTER = "it.amattioli.guidate.properties.PropertyValuesComposer.typeConverter";
	private static final String BINDING_UPDATER = "it.amattioli.guidate.properties.PropertyValuesComposer.bindingUpdater";
	private static final String LIST_BINDING_UPDATER = "it.amattioli.guidate.properties.PropertyValuesComposer.listBindingUpdater";
	private String draggableItems = "false";
	
	private String getPropertyName(Component comp) {
		return (new PropertyNameRetriever(comp)).retrieve();
	}
	
	private TypeConverter getConverter(Listbox listbox) {
		TypeConverter converter = (TypeConverter)listbox.getAttribute(TYPE_CONVERTER);
		if (converter == null) {
			Object converterAttribute = listbox.getAttribute("typeConverter");
			converter = Converters.createFrom(converterAttribute);
			listbox.setAttribute(TYPE_CONVERTER, converter);
		}
		return converter;
	}

	private Object getBackBean(Listbox listbox) {
		return BackBeans.findBackBean(listbox);
	}
	
	public void onCreate(Event evt) {
		Listbox listbox = (Listbox)evt.getTarget();
		String propertyName = getPropertyName(listbox);
		initMultiple(listbox);
		listbox.setItemRenderer(new ValueItemRenderer(getConverter(listbox), draggableItems));
		boolean nullValues = !listbox.isMultiple() && !isNotNull(listbox);
		listbox.setModel(new PropertyValuesModel(getBackBean(listbox), propertyName, nullValues));
		registerPropertyChangeListener(listbox);
		Events.sendEvent(new Event(BindingUpdater.ON_BINDING_UPDATE, listbox));
	}
	
	private boolean isNotNull(Listbox listbox) {
		Object bean = getBackBean(listbox);
		Validator validator;
		if (bean instanceof Validator) {
			validator = (Validator)bean;
		} else {
			validator = new DefaultValidator(bean);
		}
		Constraint constraint = validator.getPropertyConstraint(getPropertyName(listbox), NotNull.class.getName());
		return constraint != null;
	}
	
	private void initMultiple(Listbox listbox) {
		PropertyClass pClass = new PropertyClassRetrieverImpl(getBackBean(listbox)).retrievePropertyClass(getPropertyName(listbox));
		if (pClass != null) {
			listbox.setMultiple(pClass.isMultiple());
		}
	}
	
	public void onSelect(SelectEvent event) {
		Listbox listbox = (Listbox)event.getTarget();
		Object value = null;
		if (listbox.isMultiple()) {
			Collection<Object> values = new ArrayList<Object>();
	        for (Listitem item : (Collection<Listitem>)event.getSelectedItems()) {
	        	values.add(item.getValue());
	        }
	        value = values;
		} else {
			value = listbox.getSelectedItem().getValue();
		}
		try {
			PropertyUtils.setProperty(getBackBean(listbox), getPropertyName(listbox), value);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void onBindingUpdate(Event evt) {
		Listbox listbox = (Listbox)evt.getTarget();
		if (listbox.isMultiple()) {
			updateMultipleSelection(listbox);
		} else {
			updateSingleSelection(listbox);
		}
	}

	private void updateSingleSelection(Listbox listbox) {
		try {
			Object backBean = getBackBean(listbox);
			if (backBean != null) {
				Object value = PropertyUtils.getProperty(backBean, getPropertyName(listbox));
				int index = 0;
				for (Object curr:((PropertyValuesModel)listbox.getListModel()).getValues()) {
					if (ObjectUtils.equals(curr, value) && index < listbox.getItemCount()) {
						listbox.setSelectedIndex(index);
					}
					index++;
				}
			}
		} catch(NoSuchMethodException e) {
			//Nothing to do
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void updateMultipleSelection(Listbox listbox) {
		try {
			Collection<Object> selected = (Collection<Object>)PropertyUtils.getProperty(getBackBean(listbox), getPropertyName(listbox));
			if (selected != null) {
				int index = 0;
				for (Object curr:((PropertyValuesModel)listbox.getListModel()).getValues()) {
		        	if (selected.contains(curr)) {
		        		listbox.addItemToSelection(listbox.getItemAtIndex(index));
		        	}
		        	index++;
		        }
			} else {
				listbox.clearSelection();
			}
		} catch(NoSuchMethodException e) {
			//Nothing to do
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void registerPropertyChangeListener(Listbox listbox) {
		Object backBean = getBackBean(listbox);
		if (backBean instanceof PropertyChangeEmitter) {
			PropertyChangeEmitter emitter = (PropertyChangeEmitter)backBean;
			
			BindingUpdater bindingUpdater = new BindingUpdater(getPropertyName(listbox), listbox);
			emitter.addPropertyChangeListener(bindingUpdater);
			listbox.setAttribute(BINDING_UPDATER, bindingUpdater);
			
			BindingUpdater listBindingUpdater = new BindingUpdater(ValuesListerImpl.valuesProperty(getPropertyName(listbox)), listbox);
			emitter.addPropertyChangeListener(listBindingUpdater);
			listbox.setAttribute(LIST_BINDING_UPDATER, listBindingUpdater);
		}
	}
	
}
