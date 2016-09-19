package de.bkroeger.myvisio.svg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SVGListElementImpl<T> implements Serializable {
	
	private static final long serialVersionUID = 7202062554788343566L;
	
	protected List<T> internalList;

	public SVGListElementImpl() {
		this.internalList = new ArrayList<T>();
	}
	
	public SVGListElementImpl(SVGListElementImpl<T> other) {
		this.internalList = new ArrayList<T>((int) other.getNumberOfItems());
		for (T item : other.internalList) {
			this.internalList.add(item);
		}
	}

	public long getNumberOfItems() {
		return internalList.size();
	}

	public void clear() {
		internalList.clear();
	}

	public T initialize(T newItem) {
		internalList.clear();
		internalList.add(newItem);
		return newItem;
	}

	public T getItem(long index) {
		return internalList.get((int) index);
	}

	public T insertItemBefore(T newItem, long index) {
		internalList.add((int) index, newItem);
		return newItem;
	}

	public T replaceItem(T newItem, long index) {
		internalList.set((int) index, newItem);
		return newItem;
	}

	public T removeItem(long index) {
		T value = internalList.get((int)index);
		internalList.remove((int)index);
		return value;
	}

	public T appendItem(T newItem) {
		internalList.add(newItem);
		return newItem;
	}

}
