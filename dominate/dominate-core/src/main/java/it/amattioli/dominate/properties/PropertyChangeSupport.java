package it.amattioli.dominate.properties;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyChangeSupport {
	private static final Logger logger = LoggerFactory.getLogger(PropertyChangeSupport.class);
	private boolean enabled = true;
	private Object sourceBean;
	private static final String DUMMY = "dummy";
	private WeakHashMap<PropertyChangeListener, String> listeners;
	private Map<String, WeakHashMap<PropertyChangeListener, String>> specificListeners;

	public PropertyChangeSupport(Object sourceBean) {
		this.sourceBean = sourceBean;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void enable() {
		enabled = true;
		logger.debug("PropertyChangeSupport for {} has been enabled",sourceBean.toString());
	}
	
	public void disable() {
		enabled = false;
		logger.debug("PropertyChangeSupport for {} has been disabled",sourceBean.toString());
	}
	
	private WeakHashMap<PropertyChangeListener, String> getGenericListeners() {
        if (listeners == null) {
            listeners = new WeakHashMap<PropertyChangeListener, String>();
        }
        return listeners;
    }

	private WeakHashMap<PropertyChangeListener, String> getSpecificListeners(String result) {
		if (specificListeners == null) {
			specificListeners = new HashMap<String, WeakHashMap<PropertyChangeListener, String>>();
		}
		WeakHashMap<PropertyChangeListener, String> wantedListeners = specificListeners.get(result);
		if (wantedListeners == null) {
			wantedListeners = new WeakHashMap<PropertyChangeListener, String>();
			specificListeners.put(result, wantedListeners);
		}
		return wantedListeners;
	}
	
	private Collection<PropertyChangeListener> getAllSpecificListeners() {
		if (specificListeners == null) {
			specificListeners = new HashMap<String, WeakHashMap<PropertyChangeListener, String>>();
		}
		Collection<PropertyChangeListener> result = new ArrayList<PropertyChangeListener>();
		for (WeakHashMap<PropertyChangeListener, String> curr: specificListeners.values()) {
			result.addAll(curr.keySet());
		}
		return result;
	}

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        getGenericListeners().put(listener, DUMMY);
    }
    
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
    	getSpecificListeners(propertyName).put(listener, DUMMY);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
    	getGenericListeners().remove(listener);
    	if (specificListeners != null) {
			for (WeakHashMap<PropertyChangeListener, String> curr : specificListeners.values()) {
				curr.remove(listener);
			}
    	}
    }

	private void firePropertyChange(PropertyChangeEvent evt, PropertyChangeListener listener) {
		if (isEnabled()) {
			logger.debug("Dispatching PropertyChangeEvent [source={}, property={}] to {}",new Object[]{evt.getSource(),evt.getPropertyName(),listener});
			listener.propertyChange(evt);
		} else {
			logger.debug("PropertyChangeEvent [source={}, property={}] has not been dispatched to {}. Event dispatching is disabled",new Object[]{evt.getSource(),evt.getPropertyName(),listener});
		}
    }

	public void firePropertyChange(PropertyChangeEvent evt, Set<String> firedProperties) {
		if (evt.getOldValue() == null || evt.getNewValue() == null || !evt.getOldValue().equals(evt.getNewValue())) {
			// The genListener collection is duplicated to avoid concurrent modification exceptions
        	Collection<PropertyChangeListener> genListeners = new ArrayList(getGenericListeners().keySet()); 
			for (PropertyChangeListener curr : genListeners) {
        		firePropertyChange(evt, curr);
            }
        	Collection<PropertyChangeListener> others;
        	if (evt.getPropertyName() != null) {
        		others = getSpecificListeners(evt.getPropertyName()).keySet();
        		
        	} else {
        		others = getAllSpecificListeners();
        	}
        	for (PropertyChangeListener curr : others) {
    			firePropertyChange(evt, curr);
            }
        	firedProperties.add(evt.getPropertyName());
        	fireDependencies(evt, firedProperties);
    	}
	}
	
    public void firePropertyChange(PropertyChangeEvent evt) {
    	firePropertyChange(evt, new HashSet<String>());
    }
    
    public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
    	firePropertyChange(new PropertyChangeEvent(sourceBean, propertyName, oldValue, newValue));
    }
    
    private void fireDependencies(PropertyChangeEvent evt, Set<String> firedProperties) {
    	for (String curr: ClassPropertyDependencies.getInstance().getDependencies(sourceBean.getClass(), evt.getPropertyName())) {
    		if (!firedProperties.contains(curr)) {
    			firePropertyChange(new PropertyChangeEvent(sourceBean, curr, null, null), firedProperties);
    		}
    	}
    }
}
