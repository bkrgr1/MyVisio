package de.bkroeger.myvisio.svg;

import java.io.Serializable;

import org.w3c.dom.svg.SVGLength;
import org.w3c.dom.svg.SVGLengthList;

public class SVGLengthListImpl extends SVGListElementImpl<SVGLength> 
implements SVGLengthList, Serializable {

	private static final long serialVersionUID = 7370263856978447564L;

	/**
	 * Default-Konstruktor.
	 */
	public SVGLengthListImpl() {
		super();
	}
	
	public Object clone() {
		
		SVGLengthListImpl list = new SVGLengthListImpl();
		for (int i=0; i<this.getNumberOfItems(); i++) {
			list.appendItem((SVGLength)((SVGLengthImpl)this.getItem(i)).clone());
		}
		return list;
	}
	
	/**
	 * Konstruktor für Length_Liste mit Length-Liste-Wert.
	 * @param other - eine andere Length-Liste
	 */
	@SuppressWarnings("unchecked")
	public SVGLengthListImpl(SVGLengthList other) {
		super((SVGListElementImpl<SVGLength>) other);
	}
}
