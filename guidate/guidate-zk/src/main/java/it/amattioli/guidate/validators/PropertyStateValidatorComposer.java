package it.amattioli.guidate.validators;

import it.amattioli.dominate.validation.DefaultValidator;
import it.amattioli.dominate.validation.ValidationResult;
import it.amattioli.dominate.validation.Validator;
import it.amattioli.guidate.containers.BackBeans;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericComposer;

import static it.amattioli.dominate.validation.ValidationResult.ResultType.INVALID;
import static it.amattioli.guidate.validators.ValidatingComposer.ON_REGISTER_VALIDABLE_ELEMENT;

public class PropertyStateValidatorComposer extends GenericComposer {
	private static final Logger logger = LoggerFactory.getLogger(PropertyStateValidatorComposer.class);

	public void onCreate(Event evt) throws Exception {
    	Component comp = evt.getTarget();
    	if (comp.getParent() != null) {
        	Component container = BackBeans.findContainer(comp);
    		if (container != null) {
    			Events.sendEvent(new Event(ON_REGISTER_VALIDABLE_ELEMENT, container, comp));
    		}
    	}
    }

	public void onValidateElement(Event evt) {
		String propertyName = (String)evt.getTarget().getAttribute("propertyName");
		Object bean = BackBeans.findTargetBackBean(evt);
		Validator validator;
		if (bean instanceof Validator) {
			validator = (Validator)bean;
		} else {
			validator = new DefaultValidator(bean);
		}
		ValidationResult result = null;
		try {
			Object value = PropertyUtils.getProperty(bean, propertyName);
			result = validator.validateProperty(propertyName, value);
		} catch(Exception e) {
			logger.error("Errore",e);
		}
		if (result != null && INVALID.equals(result.getType())) {
			throw new WrongValueException(evt.getTarget(), result.getMessage());
		}
	}
}
