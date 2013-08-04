package it.amattioli.guidate.properties;

import it.amattioli.dominate.validation.Constraint;
import it.amattioli.dominate.validation.DefaultValidator;
import it.amattioli.dominate.validation.Validator;
import it.amattioli.guidate.containers.BackBeans;
import static it.amattioli.guidate.validators.ValidatingComposer.ON_REGISTER_VALIDABLE_ELEMENT;
import it.amattioli.guidate.validators.PropertyValidator;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.validator.Length;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.impl.InputElement;

/**
 * A composer that binds the "value" attribute of an Input component
 * to the property of a back-bean in a two-ways mode.
 * Not only the component attribute is updated every time the property 
 * of the back-bean changes but the back-bean property value is updated
 * when the Input component loose focus. 
 * 
 * @author andrea
 *
 */
public class InputPropertyComposer extends PropertyComposer {
	
	public InputPropertyComposer() {
	}
	
	public InputPropertyComposer(String propertyName) {
		setPropertyName(propertyName);
	}
	
	protected void setParameterValidator(Component comp) {
		PropertyValidator validator = new PropertyValidator(getPropertyName(), getPropertyClass());
		((InputElement)comp).setConstraint(validator);
		Component container = BackBeans.findContainer(comp);
		if (container != null) {
			Events.sendEvent(new Event(ON_REGISTER_VALIDABLE_ELEMENT, container, comp));
		}
	}
	
	public void onCreate(Event evt) throws Exception {
		Component comp = evt.getTarget();
		// can't be in doAfterCompose because it cannot retrieve the backbean there
		registerPropertyChangeListener(comp);
		readConstraints(comp);
		// can't be in doAfterCompose because Events.sendEvent must be executed 
		// inside an event listener
		setParameterValidator(comp);
		Events.sendEvent(new Event(BindingUpdater.ON_BINDING_UPDATE, comp));
	}
	
	public void onBlur(Event evt) throws Exception {
		Component comp = evt.getTarget();
		if (!((InputElement)comp).isReadonly()) {
			Object val = PropertyUtils.getProperty(comp, "value");
			if (getConverter(comp) != null) {
				val = getConverter(comp).coerceToBean(val, comp);
			}
			PropertyUtils.setProperty(getBackBean(comp), getCompletePropertyName(comp), val);
		}
	}

	public void onValidateElement(Event evt) {
		InputElement input = (InputElement)evt.getTarget();
		input.getText();
		input.clearErrorMessage(true);
		input.getText();
	}
	
	@Override
	protected String getComponentValueAttribute(Component comp) {
		return "rawValue";
	}
	
	private void readConstraints(Component comp) {
		Object bean = getBackBean(comp);
		Validator validator;
		if (bean instanceof Validator) {
			validator = (Validator)bean;
		} else {
			validator = new DefaultValidator(bean);
		}
		Constraint constraint = validator.getPropertyConstraint(getPropertyName(), Length.class.getName());
		if (constraint != null) {
			int maxLength = (Integer)constraint.getParameter("max");
			((InputElement)comp).setMaxlength(maxLength);
		}
	}
	/*
	private void readBackBeanAnnotations(Component comp) {
		Length a = Annotations.retrieve(getBackBean(comp), getPropertyName(), Length.class);
		if (a != null) {
			int maxLength = a.max();
			((InputElement)comp).setMaxlength(maxLength);
		}
	}
	*/

}
