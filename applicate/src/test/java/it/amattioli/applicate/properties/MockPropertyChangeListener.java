package it.amattioli.applicate.properties;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MockPropertyChangeListener implements PropertyChangeListener {
	private int propertyChangeNotified = 0;
	private String propertyName;
	
	public MockPropertyChangeListener(String propertyName) {
		this.propertyName = propertyName;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName() == null || propertyName.equals(evt.getPropertyName())) {
			propertyChangeNotified++;
		}
	}

	public boolean isPropertyChangeNotified() {
		return propertyChangeNotified > 0;
	}
	
	public int getNumPropertyChangeNotified() {
		return propertyChangeNotified;
	}
}
