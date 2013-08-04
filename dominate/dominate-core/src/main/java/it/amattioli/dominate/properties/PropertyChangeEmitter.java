package it.amattioli.dominate.properties;

import java.beans.PropertyChangeListener;

public interface PropertyChangeEmitter {
	
	public void addPropertyChangeListener(PropertyChangeListener listener);
    
    public void removePropertyChangeListener(PropertyChangeListener listener);
	
}
