package de.bkroeger.myvisio.svg;

import java.io.Serializable;

import org.w3c.dom.svg.SVGStringList;

/**
 * <p>
 * Liste aus Strings.
 * </p>
 * @author bk
 */
public class SVGStringListImpl extends SVGListElementImpl<String> 
implements SVGStringList, Serializable {

	private static final long serialVersionUID = -3822714539449107898L;

	/**
	 * Default-Konstruktor
	 */
	public SVGStringListImpl() {
		super();
	}

	/**
	 * Konstruktor mit String-Array.
	 * @param values
	 */
	public SVGStringListImpl(String[] values) {
		super();
		for (String value : values) {
			this.appendItem(value);
		}
	}
	
	public SVGStringListImpl clone() {
		
		SVGStringListImpl clone = new SVGStringListImpl();
		for (int i=0; i<this.getNumberOfItems(); i++) {
			clone.appendItem(new String(this.getItem(i)));
		}
		return null;
	}
}
