package de.bkroeger.myvisio.svg.attributes;

import java.awt.BasicStroke;
import java.io.Serializable;

import org.w3c.dom.DOMException;

/**
 * <p>
 * ‘stroke-linecap’ specifies the shape to be used at the end of open subpaths when they are stroked.
 * </p>
 * @author bk
 */
public class SVGStrokeLinecapAttributeImpl implements Serializable {

	private static final long serialVersionUID = 7216896032539751867L;
	
	protected StrokeLinecapType linecapType;
	public StrokeLinecapType getType() { return linecapType; }
	
	public SVGStrokeLinecapAttributeImpl() {
		linecapType = StrokeLinecapType.BUTT;
	}
	
	public Object clone() {
		
		SVGStrokeLinecapAttributeImpl clone = new SVGStrokeLinecapAttributeImpl();
		clone.linecapType = linecapType;
		return clone;
	}
	
	/**
	 * @param value
	 */
	public void parse(String value) {
		
		if (value.equalsIgnoreCase("butt")) {
			linecapType = StrokeLinecapType.BUTT;
		} else if (value.equalsIgnoreCase("round")) {
			linecapType = StrokeLinecapType.ROUND;
		} else if (value.equalsIgnoreCase("square")) {
			linecapType = StrokeLinecapType.SQUARE;
		} else if (value.equalsIgnoreCase("inherit")) {
			linecapType = StrokeLinecapType.INHERIT;
		} else {
			throw new DOMException(DOMException.SYNTAX_ERR, "Invalid stroke-linecap value: "+value);
		}
	}
	
	public String toXmlString() {
		if (this.linecapType != null) {
			switch (this.linecapType) {
			case BUTT:
				return "butt";
			case ROUND:
				return "round";
			case SQUARE:
				return "square";
			case INHERIT:
				return "inherit";
			default:
				return "";
			}
		} else return "";
	}
	
	public static enum StrokeLinecapType {
		
		BUTT(BasicStroke.CAP_BUTT),
		ROUND(BasicStroke.CAP_ROUND),
		SQUARE(BasicStroke.CAP_SQUARE),
		INHERIT(0);
		
		private int val;
		
		private StrokeLinecapType(int value) {
			this.val = value;
		}
		
		public int getLinecapValue() { return val; }
	}
}
