package de.bkroeger.myvisio.svg.attributes;

import java.io.Serializable;

import org.w3c.dom.svg.SVGLength;
import org.w3c.dom.svg.SVGLengthList;

import de.bkroeger.myvisio.svg.SVGLengthImpl;
import de.bkroeger.myvisio.svg.SVGLengthListImpl;
import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * <p>
 * ‘stroke-dasharray’ controls the pattern of dashes and gaps used to stroke paths. <dasharray> contains a list of
 * comma and/or white space separated <length>s and <percentage>s that specify the lengths of alternating dashes
 * and gaps. If an odd number of values is provided, then the list of values is repeated to yield an even number of
 * values. Thus, stroke-dasharray: 5,3,2 is equivalent to stroke-dasharray: 5,3,2,5,3,2.
 * </p>
 * @author bk
 */
public class SVGStrokeDasharrayAttributeImpl implements Serializable {
	
	private static final long serialVersionUID = 1251816692925654274L;

	protected StrokeDasharrayType strokeDasharrayType;
	
	protected SVGLengthList strokeDasharray;
	public SVGLengthList getLengthList() { return strokeDasharray; }
	public int size() { 
		return (int) strokeDasharray.getNumberOfItems(); 
	}
	
	public SVGStrokeDasharrayAttributeImpl() {
		
		strokeDasharrayType = StrokeDasharrayType.NONE;
		strokeDasharray = new SVGLengthListImpl();
	}
	
	public Object clone() {
		
		SVGStrokeDasharrayAttributeImpl clone = new SVGStrokeDasharrayAttributeImpl();
		clone.strokeDasharray = strokeDasharray != null ? (SVGLengthList) strokeDasharray.clone() : null;
		clone.strokeDasharrayType = strokeDasharrayType;
		return clone;
	}

	/**
	 * <dl>
	 * <dt>none</dt>
     * <dd>Indicates that no dashing is used. If stroked, the line is drawn solid.</dd>
	 * <dt>dasharray></dt>
	 * <dd>A list of comma and/or white space separated <lengsth>s (which can have a unit identifier) and <percentage>
	 * s. A percentage represents a distance as a percentage of the current viewport (see Units). A negative
	 * value is an error (see Error processing). If the sum of the values is zero, then the stroke is rendered as if a
	 * value of none were specified.</dd>
	 * <dt>inherit</dt>
	 * <dd>Value of the parent element</dd>
     * </dl>
	 * @param value
	 * @throws TechnicalException
	 */
	public void parse(String value) throws TechnicalException {
		
		if (value.equalsIgnoreCase("none")) {
			strokeDasharrayType = StrokeDasharrayType.NONE;
		} else if (value.equalsIgnoreCase("inherit")) {
			strokeDasharrayType = StrokeDasharrayType.INHERIT;
		} else {
			strokeDasharrayType = StrokeDasharrayType.DASHARRAY;
			strokeDasharray = new SVGLengthListImpl();
			String parts[] = value.split("\\s*,\\s*|\\s+");
			for (int i=0; i<parts.length; i++) {
				SVGLength l = new SVGLengthImpl(parts[i]);
				strokeDasharray.appendItem(l);
			}
		}
	}
	
	public String toXmlString() {
		if (this.strokeDasharrayType != null) {
			switch (this.strokeDasharrayType){
			case NONE:
				return "none";
			case INHERIT:
				return "inherit";
			case DASHARRAY:
				StringBuilder sb = new StringBuilder();
				for (int i=0; i<this.strokeDasharray.getNumberOfItems(); i++) {
					if (sb.length() > 0) sb.append(" ");
					sb.append(""+this.strokeDasharray.getItem(i).toXmlString());
				}
				return sb.toString();
			default:
				return "";
			}
		} else return "";
	}
	
	public static enum StrokeDasharrayType {
		
		NONE,
		DASHARRAY,
		INHERIT;
	}
}
