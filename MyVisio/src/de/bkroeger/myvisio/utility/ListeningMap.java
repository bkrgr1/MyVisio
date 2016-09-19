package de.bkroeger.myvisio.utility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

public class ListeningMap<K, V> extends Observable implements Map<K, V> {

    private final Map<K, V> delegatee;
    
    private List<Observer> observers = new ArrayList<Observer>();
    
    public ListeningMap() {
    	this.delegatee = new HashMap<K, V>();
    }
    
    public ListeningMap(int size) {
    	this.delegatee = new HashMap<K, V>(size);
    }

    public ListeningMap(Map<K, V> delegatee) {
        this.delegatee = delegatee;
    }
    
    public void addObserver(Observer observer) {
    	observers.add(observer);
    }
    
    public void removeObserver(Observer observer) {
    	observers.remove(observer);
    }

	
	public int size() {
		return delegatee.size();
	}

	
	public boolean isEmpty() {
		return delegatee.isEmpty();
	}

	
	public boolean containsKey(Object key) {
		return delegatee.containsKey(key);
	}

	
	public boolean containsValue(Object value) {
		return delegatee.containsValue(value);
	}

	
	public V get(Object key) {
		return delegatee.get(key);
	}

	
	public V put(K key, V value) {
		V v = delegatee.put(key, value);
		for (Observer obs : observers) {
			obs.update(this, (Object)key);
		}
		return v;
	}

	
	public V remove(Object key) {
		return delegatee.remove(key);
	}

	
	public void putAll(Map<? extends K, ? extends V> m) {
		delegatee.putAll(m);
	}

	
	public void clear() {
		delegatee.clear();
	}

	
	public Set<K> keySet() {
		return delegatee.keySet();
	}

	
	public Collection<V> values() {
		return delegatee.values();
	}

	
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return delegatee.entrySet();
	}
}
