package de.bkroeger.myvisio.svg.attributes;

import java.io.Serializable;

import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * <p>
 * The ‘fill’ property paints the interior of the given graphical element. 
 * The area to be painted consists of any areas inside the outline of the shape. 
 * To determine the inside of the shape, all subpaths are considered, and the interior
 * is determined according to the rules associated with the current value 
 * of the ‘fill-rule’ property. The zero-width geometric outline of a shape is included in the 
 * area to be painted. 
 * <br/>
 * The fill operation fills open subpaths by performing the fill operation as if an 
 * additional "closepath" command were added to the path to connect the last point of the 
 * subpath with the first point of the subpath. Thus, fill operations 
 * apply to both open subpaths within ‘path’ elements (i.e., subpaths without a 
 * closepath command) and ‘polyline’ elements.
 * </p><pre>
 * ‘fill’
 *    Value: <paint> (See Specifying paint)
 *    Initial: black
 *    Applies to: shapes and text content elements
 *    Inherited: yes
 *    Percentages: N/A
 *    Media: visual
 *    Animatable: yes
 * </pre>
 * @author bk
 */
public class SVGFillAttributeImpl implements Serializable {

	private static final long serialVersionUID = -7171841290258518143L;
	
	private FillAttributeType fillType;
	public  FillAttributeType getFillType() { return fillType; }
	
	private SVGColorAttributeImpl color;
	public  SVGColorAttributeImpl getFillColor() { return color; }
	
	private SVGFuncIRIImpl iri;
	public  SVGFuncIRIImpl getFuncIRI() { return iri; }
	
	/**
	 * Default-Konstruktor
	 */
	public SVGFillAttributeImpl() {
		
		this.fillType = FillAttributeType.NONE;
	}
	
	public SVGFillAttributeImpl(SVGColorAttributeImpl color) {
		
		this.fillType = FillAttributeType.COLOR;
		this.color = color;
	}
	
	public SVGFillAttributeImpl clone() {
		
		SVGFillAttributeImpl clone = new SVGFillAttributeImpl();
		clone.fillType = fillType;
		clone.color = color != null ? (SVGColorAttributeImpl) color.clone() : null;
		clone.iri = iri != null ? (SVGFuncIRIImpl) iri.clone() : null;
		return clone;
	}
	
	public static enum FillAttributeType {
		
		NONE,
		COLOR,
		CURRENT_COLOR,
		FUNCIRI,
		INHERIT;
	}

	/**
	 * <p>
	 * Analysier den Wert des "fill" Attributs.
	 * </p><p>
	 * 
	 * @param value
	 * @return
	 * @throws TechnicalException 
	 */
	public void parse(String value) throws TechnicalException {
		
		if (value.trim().equalsIgnoreCase("none")) {
			this.fillType = FillAttributeType.NONE;
		} else if (value.trim().equalsIgnoreCase("currentcolor")) {
			this.fillType = FillAttributeType.CURRENT_COLOR;
		} else if (value.trim().equalsIgnoreCase("inherit")) {
			this.fillType = FillAttributeType.INHERIT;
		} else if (value.trim().startsWith("url(")) {
			this.fillType = FillAttributeType.FUNCIRI;
			this.iri = new SVGFuncIRIImpl();
			this.iri.parseFuncIRI(value.trim());
		} else {
			this.fillType = FillAttributeType.COLOR;
			this.color = new SVGColorAttributeImpl();
			this.color.parseColor(value.trim());
		}
	}
	
	public String toXmlString() {
		if (this.fillType != null) {
			switch (this.fillType) {
			case NONE:
				return "none";
			case CURRENT_COLOR:
				return "currentcolor";
			case INHERIT:
				return "url("+this.iri.toXmlString()+")";
			case COLOR:
				return this.color.toXmlString();
			default:
				return "";
			}
		} else return "";
	}
}
