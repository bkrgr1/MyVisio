package de.bkroeger.myvisio.svg.attributes;

import java.io.Serializable;

import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGLength;

import de.bkroeger.myvisio.svg.SVGLengthImpl;
import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * <p>
 * This property specifies the width of the stroke on the current object. If a <percentage> is used, the value represents
 * a percentage of the current viewport. (See Units.)
 * </p><p>
 * A zero value causes no stroke to be painted. A negative value is an error (see Error processing).
 * </p>
 * @author bk
 */
public class SVGStrokeWidthAttributeImpl implements Serializable {
	
	private static final long serialVersionUID = -6907148703850735050L;
	
	protected SVGLengthImpl width;
	public SVGLength getWidth() { return width; }
	
	/**
	 * Default-Konstruktor
	 */
	public SVGStrokeWidthAttributeImpl() {
		width = new SVGLengthImpl(1.0f);
	}
	
	public Object clone() {
		
		SVGStrokeWidthAttributeImpl clone = new SVGStrokeWidthAttributeImpl();
		clone.width = width != null ? (SVGLengthImpl) width.clone() : null;
		return clone;
	}

	/**
	 * XML-Wert analysieren.
	 * 
	 * @param value
	 * @throws TechnicalException
	 */
	public void parse(String value) throws TechnicalException {
		
		width = new SVGLengthImpl();
		width.setValueAsString(value);
		if (width.getValueInSpecifiedUnits() < 0.0f) {
			// Fehler
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "stroke-width may not be negative!");
		}
	}
	
	public String toXmlString() {
		if (this.width != null) {
			return String.format("%f", this.width.getValueInSpecifiedUnits());
		} else return "";
	}
}
