package de.bkroeger.myvisio.svg.attributes;

import java.io.Serializable;

import org.w3c.dom.DOMException;

import java.awt.BasicStroke;

/**
 * <p>
 * ‘stroke-linejoin’ specifies the shape to be used at the corners of paths or basic shapes when they are stroked.
 * </p>
 * @author bk
 */
public class SVGStrokeLinejoinAttributeImpl implements Serializable {

	private static final long serialVersionUID = -5822929538597080148L;
	
	protected StrokeLinejoinType linejoinType;
	public StrokeLinejoinType getType() { return linejoinType; }
	
	public SVGStrokeLinejoinAttributeImpl() {
		linejoinType = StrokeLinejoinType.MITTER;
	}
	
	public Object clone() {
		
		SVGStrokeLinejoinAttributeImpl clone = new SVGStrokeLinejoinAttributeImpl();
		clone.linejoinType = linejoinType;
		return clone;
	}
	
	/**
	 * @param value
	 */
	public void parse(String value) {
		
		if (value.equalsIgnoreCase("mitter")) {
			linejoinType = StrokeLinejoinType.MITTER;
		} else if (value.equalsIgnoreCase("round")) {
			linejoinType = StrokeLinejoinType.ROUND;
		} else if (value.equalsIgnoreCase("bevel")) {
			linejoinType = StrokeLinejoinType.BEVEL;
		} else if (value.equalsIgnoreCase("inherit")) {
			linejoinType = StrokeLinejoinType.INHERIT;
		} else {
			throw new DOMException(DOMException.SYNTAX_ERR, "Invalid stroke-linejoin value: "+value);
		}
	}
	
	public String toXmlString() {
		if (this.linejoinType != null) {
			switch (this.linejoinType) {
			case MITTER:
				return "mitter";
			case ROUND:
				return "round";
			case BEVEL:
				return "bevel";
			case INHERIT:
				return "inherit";
			default:
				return "";
			}
		} else return "";
	}
	
	public static enum StrokeLinejoinType {
		
		MITTER(BasicStroke.JOIN_MITER),
		ROUND(BasicStroke.JOIN_ROUND),
		BEVEL(BasicStroke.JOIN_BEVEL),
		INHERIT(0);
		
		private int val;
		
		private StrokeLinejoinType(int value) {
			this.val = value;
		}
		
		public int getLinejoinValue() { return val; }
	}
}
