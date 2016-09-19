package de.bkroeger.myvisio.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class AbstractModel {
	
	private List<PropertyChangeListener> listeners = new ArrayList<PropertyChangeListener>();

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.listeners.add(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.listeners.remove(listener);
	}
	
	public void firePropertyChange(String attribute, Object oldValue, Object newValue) {
		for (PropertyChangeListener listener : listeners) {
			PropertyChangeEvent evt = new PropertyChangeEvent(this, attribute, oldValue, newValue);
			listener.propertyChange(evt);
		}
	}
}
