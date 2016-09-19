package de.bkroeger.myvisio.svg.attributes;

import java.io.Serializable;

import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * <p>
 * The ‘fill-rule’ property indicates the algorithm which is to be used to determine what parts 
 * of the canvas are included inside the shape. For a simple, non-intersecting path, it is 
 * intuitively clear what region lies "inside"; however, for a more complex path, such as a path 
 * that intersects itself or where one subpath encloses another, the interpretation of "inside" 
 * is not so obvious.<br/>
 * The ‘fill-rule’ property provides two options for how the inside of a shape is determined:
 * <dl>
 * <dt>nonzero</dt>
 * <dd>This rule determines the "insideness" of a point on the canvas by drawing a ray from 
 * that point to infinity in any direction and then examining the places where a segment of the 
 * shape crosses the ray. Starting with a count of zero, add one each time a path segment 
 * crosses the ray from left to right and subtract one each time a path segment crosses the 
 * ray from right to left. After counting the crossings, if the result is zero then the point 
 * is outside the path. Otherwise, it is inside.</dd>
 * <dt>evenodd</dt>
 * <dd>This rule determines the "insideness" of a point on the canvas by drawing a ray from 
 * that point to infinity in any direction and counting the number of path segments from the 
 * given shape that the ray crosses. If this number is odd, the point is inside; if even, 
 * the point is outside.</dd>
 * </dl></p>
 * <pre>
 * ‘fill-rule’
 *		Value: nonzero | evenodd | inherit
 *		Initial: nonzero
 *		Applies to: shapes and text content elements
 *		Inherited: yes
 *		Percentages: N/A
 *		Media: visual
 *		Animatable: yes
 * </pre>
 * @author bk
 */
public class SVGFillRuleAttributeImpl implements Serializable {

	private static final long serialVersionUID = -2973486055130226152L;
	
	private FillRuleType fillRule = null;
	public FillRuleType getFillRule() { return fillRule; }
	
	public SVGFillRuleAttributeImpl(FillRuleType value) {
		this.fillRule = value;
	}
	
	public SVGFillRuleAttributeImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public Object clone() {
		
		SVGFillRuleAttributeImpl clone = new SVGFillRuleAttributeImpl();
		clone.fillRule = fillRule;
		return clone;
	}

	/**
	 * Parser
	 * @param value
	 * @throws TechnicalException
	 */
	public void parse(String value) throws TechnicalException {
		
		this.fillRule = null;
		for (FillRuleType fType : FillRuleType.values()) {
			if (fType.toString().equals(value)) {
				this.fillRule = fType;
			}
		}
		if (this.fillRule == null) {
			throw new TechnicalException("Invalid fill-rule value: "+value);
		}
	}
	
	public String toXmlString() {
		if (this.fillRule != null)
			return this.fillRule.toString();
		else
			return "";
	}
	
	public static enum FillRuleType {
		nonzero,
		evenodd;
	}
}
