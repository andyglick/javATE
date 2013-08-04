package it.amattioli.guidate.browsing;

import static it.amattioli.dominate.validation.ValidationResult.ResultType.INVALID;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ResourceBundle;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.Locales;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.OpenEvent;
import org.zkoss.zul.Bandbox;

import it.amattioli.applicate.browsing.EntitySelector;
import it.amattioli.applicate.browsing.EntitySelector.Match;
import it.amattioli.dominate.properties.PropertyClassRetrieverImpl;
import it.amattioli.dominate.validation.ValidationResult;
import it.amattioli.guidate.containers.BackBeans;
import it.amattioli.guidate.properties.PropertyNameRetriever;
import it.amattioli.guidate.properties.TextPropertyComposer;
import it.amattioli.guidate.validators.ValidatingComposer;

public class EntitySelectorComposer extends TextPropertyComposer {
	private static final Logger logger = LoggerFactory.getLogger(EntitySelectorComposer.class);
	private static final String SEARCH_VALUE_PROPERTY = "searchValue";
	private static final String SELECTOR_NAME_ATTRIBUTE = "selectorName";
	private static final String IS_OPEN_ATTRIBUTE = "it.amattioli.guidate.browsing.EntitySelectorComposer.isOpen";
	private static final String SELECTION_LISTENER_ATTRIBUTE = "it.amattioli.guidate.browsing.EntitySelectorComposer.SelectionListener";
	
	public boolean isOpen(Component comp) {
		return (Boolean)comp.getAttribute(IS_OPEN_ATTRIBUTE);
	}
	
	public void setOpen(Component comp, boolean open) {
		comp.setAttribute(IS_OPEN_ATTRIBUTE, open);
	}
	
	public void registerAsValidable(Component comp) throws Exception {
    	if (comp.getParent() != null) {
        	Component container = BackBeans.findContainer(comp.getParent());
    		if (container != null) {
    			Events.sendEvent(new Event(ValidatingComposer.ON_REGISTER_VALIDABLE_ELEMENT, container, comp));
    		}
    	}
    }
	
	public void onCreate(Event evt) throws Exception {
		Component comp = evt.getTarget();
		registerAsValidable(comp);
		setOpen(comp, false);
		EntitySelector<?,?> entitySelector = findEntitySelector(comp);
		if (entitySelector != null) {
			setupComponent(comp, entitySelector);
			super.onCreate(evt);
		}
	}

	private void setupComponent(Component comp, EntitySelector<?, ?> entitySelector) {
		comp.setAttribute(BackBeans.BACK_BEAN_ATTRIBUTE, entitySelector);
		comp.setAttribute(PropertyNameRetriever.PROPERTY_NAME, SEARCH_VALUE_PROPERTY);
		setPropertyClass(String.class);
		setPropertyName(SEARCH_VALUE_PROPERTY);
		final Bandbox bandbox = (Bandbox)comp;
		bandbox.getDropdown().setAttribute(BackBeans.BACK_BEAN_ATTRIBUTE, entitySelector.getEntityBrowser());
		PropertyChangeListener selectionListener = new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if ("selectedObject".equals(evt.getPropertyName()) && isOpen(bandbox)) {
					bandbox.close();
				}
			}

		};
		entitySelector.addPropertyChangeListener(selectionListener);
		comp.setAttribute(SELECTION_LISTENER_ATTRIBUTE, selectionListener);
	}

	private EntitySelector<?,?> findEntitySelector(Component comp) throws IllegalAccessException, InvocationTargetException {
		EntitySelector<?,?> val = null;
		try {
			String propertyName = getBoundPropertyName(comp);
			if (BackBeans.findContainer(comp) == comp) { 
				if (BackBeans.findParentContainer(comp) != null && getBackBean(comp) instanceof EntitySelector<?,?>) {
					Object backBean = getBackBean(comp);
					Object parentBean = BackBeans.findParentBean(comp);
					if (parentBean != null) {
						val = (EntitySelector<?,?>)backBean;
						bindSelector(parentBean, val, propertyName);
					}
				}
			} else if (getBackBean(comp) != null) {
				Object backBean = getBackBean(comp);
				String selectorName = (String)comp.getAttribute(SELECTOR_NAME_ATTRIBUTE);
				if (selectorName != null) {
					val = (EntitySelector<?,?>)PropertyUtils.getProperty(backBean, selectorName);
				} else {
					Class<?> entityClass = (new PropertyClassRetrieverImpl(backBean)).retrievePropertyClass(propertyName).getElementClass();
					val = new EntitySelector(entityClass);
					bindSelector(BackBeans.findBackBean(comp), val, propertyName);
				}
			}
		} catch (NestedNullException e) {
			e.printStackTrace();
			val = null;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			val = null;
		}
		return val;
	}
	
	private String getBoundPropertyName(Component comp) {
		String result = (String)comp.getAttribute("boundPropertyName");
		if (result == null) {
			result = (String)comp.getAttribute(PropertyNameRetriever.PROPERTY_NAME);
			comp.setAttribute("boundPropertyName", result);
		}
		return result;
	}

	private void bindSelector(Object bean, EntitySelector<?, ?> val, String propertyName) {
		if (bean != null) {
			val.bindTo(bean, propertyName);
		}
	}
	
	public void onValidateElement(Event evt) {
		EntitySelector<?, ?> selector = (EntitySelector<?, ?>)evt.getTarget().getAttribute(BackBeans.BACK_BEAN_ATTRIBUTE);
		if (selector != null) {
			if (Match.NONE.equals(selector.getMatch())) {
				String msg = ResourceBundle.getBundle("it/amattioli/guidate/browsing/EntitySelectorMessages", Locales.getCurrent()).getString("entitySelector.MatchNone");
				throw new WrongValueException(evt.getTarget(), msg);
			}
			ValidationResult result = selector.validate();
			if (result != null && INVALID.equals(result.getType())) {
				throw new WrongValueException(evt.getTarget(), result.getMessage());
			}
		}
    }
	
	@Override
	public void onBlur(Event evt) throws Exception {
		super.onBlur(evt);
		openBandboxIfMultipleMatches((Bandbox)evt.getTarget());
		Events.postEvent("onValidateElement", evt.getTarget(), null);
	}

	private void openBandboxIfMultipleMatches(Bandbox bandbox) {
		EntitySelector<?, ?> selector = (EntitySelector<?, ?>)bandbox.getAttribute(BackBeans.BACK_BEAN_ATTRIBUTE);
		if (Match.MULTIPLE.equals(selector.getMatch())) {
			bandbox.open();
			Events.postEvent(new OpenEvent("onOpen", bandbox, true));
		}
	}

	public void onOpen(Event evt) throws Exception {
		setOpen(evt.getTarget(), ((OpenEvent)evt).isOpen());
		Events.sendEvent(new Event("onOpenBandpopup", ((Bandbox)evt.getTarget()).getDropdown()));
	}
}
