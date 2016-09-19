package de.bkroeger.myvisio.svg;

import java.io.Serializable;

import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGPathSeg;
import org.w3c.dom.svg.SVGPathSegList;

/**
 * 
 * @author bk
 */
public class SVGPathSegListImpl extends SVGListElementImpl<SVGPathSeg> 
implements SVGPathSegList, Serializable {

	private static final long serialVersionUID = 6306714109307374910L;

	@Override
	public long getNumberOfItems() {
		
		return this.internalList.size();
	}

	@Override
	public void clear() {

		this.internalList.clear();
	}

	/**
	 * Clears all existing current items from the list and re-initializes the list 
	 * to hold the single item specified by the parameter. If the inserted item is already 
	 * in a list, it is removed from its previous list before it is inserted 
	 * into this list. The inserted item is the item itself and not a copy.
	 */
	@Override
	public SVGPathSeg initialize(SVGPathSeg newItem) {

		this.internalList.clear();
		this.internalList.add(newItem);
		return newItem;
	}

	/**
	 * Returns the specified item from the list. The returned item is the item itself 
	 * and not a copy. Any changes made to the item are immediately reflected in the list.
	 */
	@Override
	public SVGPathSeg getItem(long index) {
		
		int i = (int) index;
		if (i < 0 || i >= this.internalList.size()) {
			throw new DOMException(DOMException.INDEX_SIZE_ERR, "Invalid index!");
		}
		return this.internalList.get(i);
	}

	/**
	 * Inserts a new item into the list at the specified position. The first item is number 0. 
	 * If newItem is already in a list, it is removed from its previous list before it is inserted into this list. 
	 * The inserted item is the item itself and not a copy. If the item is already in this list, 
	 * note that the index of the item to insert before is before the removal of the item.
	 */
	@Override
	public SVGPathSeg insertItemBefore(SVGPathSeg newItem, long index) {
		
		if (index < 0 || index >= this.internalList.size()) {
			throw new DOMException(DOMException.INDEX_SIZE_ERR, "Invalid index!");
		}

		int x = this.internalList.indexOf(newItem);
		if (x >= 0) {
			this.internalList.remove(x);
		}
		if (x <= index) index--;
		this.internalList.add(x, newItem);
		return newItem;
	}

	/**
	 * Replaces an existing item in the list with a new item. If newItem is already in a list, 
	 * it is removed from its previous list before it is inserted into this list. 
	 * The inserted item is the item itself and not a copy. 
	 * If the item is already in this list, note that the index of the item to replace 
	 * is before the removal of the item.
	 */
	@Override
	public SVGPathSeg replaceItem(SVGPathSeg newItem, long index) {

		if (index < 0 || index >= this.internalList.size()) {
			throw new DOMException(DOMException.INDEX_SIZE_ERR, "Invalid index!");
		}
		this.internalList.set((int) index, newItem);
		return newItem;
	}

	/**
	 * Removes an existing item from the list.
	 */
	@Override
	public SVGPathSeg removeItem(long index) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Inserts a new item at the end of the list. If newItem is already in a list, 
	 * it is removed from its previous list before it is inserted into this list. 
	 * The inserted item is the item itself and not a copy.
	 */
	@Override
	public SVGPathSeg appendItem(SVGPathSeg newItem) {

		int x = this.internalList.indexOf(newItem);
		if (x >= 0) this.internalList.remove(x);
		
		this.internalList.add(newItem);
		return newItem;
	}

}
