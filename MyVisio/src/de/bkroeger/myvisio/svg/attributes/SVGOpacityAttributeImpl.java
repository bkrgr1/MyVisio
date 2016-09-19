package de.bkroeger.myvisio.svg.attributes;

import java.io.Serializable;

import org.w3c.dom.DOMException;

import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * <p>
 * ‘fill-opacity’ specifies the opacity of the painting operation used to paint the 
 * interior the current object.
 * <dl><dt>&lt;opacity-value&gt;</dt>
 * <dd>The opacity of the painting operation used to fill the current object, as a <number>. 
 * Any values outside the range 0.0 (fully transparent) to 1.0 (fully opaque) will be 
 * clamped to this range.</dd>
 * </dl></p>
 * <pre>
 * ‘fill-opacity’
 *       Value: <opacity-value> | inherit
 *       Initial: 1
 *       Applies to: shapes and text content elements
 *       Inherited: yes
 *       Percentages: N/A
 *       Media: visual
 *       Animatable: yes
 * </pre>
 * @author bk
 */
public class SVGOpacityAttributeImpl implements Serializable {

	private static final long serialVersionUID = -674547789201888314L;
	
	private float opacity = 1.0f;
	public  float getOpacity() { return opacity; }
	
	/**
	 * Konstruktor
	 * @param value
	 */
	public SVGOpacityAttributeImpl(float value) {
		opacity = value;
	}
	
	public SVGOpacityAttributeImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public Object clone() {
		
		SVGOpacityAttributeImpl clone = new SVGOpacityAttributeImpl();
		clone.opacity = opacity;
		return clone;
	}

	/**
	 * Parser
	 * @param value
	 * @throws TechnicalException
	 */
	public void parse(String value) throws TechnicalException {
		
		try {
			opacity = Float.parseFloat(value);
			
			if (opacity < 0.0f) {
				throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid opacity value: "+opacity);
			}
			if (opacity > 1.0f) {
				throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid opacity value: "+opacity);
			}
		} catch(NumberFormatException e) {
			throw new TechnicalException("Invalid fill-opacity value: "+value);
		}
	}
	
	public String toXmlString() {
		return String.format("%f", this.opacity);
	}
}
