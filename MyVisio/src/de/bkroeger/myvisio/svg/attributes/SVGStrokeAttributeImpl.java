package de.bkroeger.myvisio.svg.attributes;

import java.io.Serializable;

import de.bkroeger.myvisio.svg.attributes.SVGFillAttributeImpl.FillAttributeType;
import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * <p>
 * The ‘stroke’ property paints along the outline of the given graphical element.
 * </p><p>
 * A subpath (see Paths) consisting of a single moveto shall not be stroked. Any zero length subpath shall not be
 * stroked if the ‘stroke-linecap’ property has a value of butt but shall be stroked if the ‘stroke-linecap’ property has a
 * value of round or square, producing respectively a circle or a square centered at the given point. Examples of zero
 * length subpaths include 'M 10,10 L 10,10', 'M 20,20 h 0', 'M 30,30 z' and 'M 40,40 c 0,0 0,0 0,0'.
 * </p>
 * @author bk
 */
public class SVGStrokeAttributeImpl implements Serializable {
	
	private static final long serialVersionUID = -3912777392216509865L;
	
	protected FillAttributeType strokeType = FillAttributeType.NONE;
	public FillAttributeType getStrokeType() { return strokeType; }
	
	protected SVGColorAttributeImpl color;
	public SVGColorAttributeImpl getPaint() { return color; }
	
	
	protected SVGFuncIRIImpl iri;
	public  SVGFuncIRIImpl getFuncIRI() { return iri; }

	/**
	 * Default-Konstruktor
	 */
	public SVGStrokeAttributeImpl() {
		
		strokeType = FillAttributeType.NONE;
		color = null;
	}
	
	public Object clone() {
		
		SVGStrokeAttributeImpl clone = new SVGStrokeAttributeImpl();
		clone.color = color != null ? (SVGColorAttributeImpl) color.clone() : null;
		clone.iri = iri != null ? (SVGFuncIRIImpl) iri.clone() : null;
		clone.strokeType = strokeType;
		return clone;
	}

	/**
	 * <p>
	 * XML-Wert analysieren
	 * </p>
	 * @param value
	 * @throws TechnicalException
	 */
	public void parse(String value) throws TechnicalException {
		
		if (value.trim().equalsIgnoreCase("none")) {
			this.strokeType = FillAttributeType.NONE;
		} else if (value.trim().equalsIgnoreCase("currentcolor")) {
			this.strokeType = FillAttributeType.CURRENT_COLOR;
		} else if (value.trim().equalsIgnoreCase("inherit")) {
			this.strokeType = FillAttributeType.INHERIT;
		} else if (value.trim().startsWith("url(")) {
			this.strokeType = FillAttributeType.FUNCIRI;
			this.iri = new SVGFuncIRIImpl();
			this.iri.parseFuncIRI(value.trim());
		} else {
			this.strokeType = FillAttributeType.COLOR;
			this.color = new SVGColorAttributeImpl();
			this.color.parseColor(value.trim());
		}
	}
	
	public String toXmlString() {
		switch (this.strokeType) {
		case NONE:
			return "none";
		case CURRENT_COLOR:
			return "currentcolor";
		case INHERIT:
			return "inherit";
		case FUNCIRI:
			return "url("+this.iri.toXmlString()+")";
		case COLOR:
			return this.color.toXmlString();
		default:
			return "";
		}
	}
}
