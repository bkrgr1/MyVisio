package de.bkroeger.myvisio.svg;

import java.io.Serializable;

import org.junit.Assert;
import org.w3c.dom.svg.SVGAnimatedTransformList;
import org.w3c.dom.svg.SVGTransformList;


/**
 * @author bk
 */
public class SVGAnimatedTransformListImpl implements SVGAnimatedTransformList, Serializable {
	
	private static final long serialVersionUID = 15549190750369361L;

	SVGTransformList baseVal;
	
	SVGTransformList animVal;
	
	/**
	 * 
	 */
	public SVGAnimatedTransformListImpl() {
		this.baseVal = new SVGTransformListImpl();
	}
	
	/**
	 * @param baseVal
	 */
	public SVGAnimatedTransformListImpl(SVGTransformList baseVal) {
		Assert.assertNotNull(baseVal);
		this.baseVal = baseVal;
	}
	
	/**
	 * @param baseVal
	 * @param animVal
	 */
	public SVGAnimatedTransformListImpl(SVGTransformList baseVal, SVGTransformList animVal) {
		this.baseVal = baseVal;
		this.animVal = animVal;
	}
	
	public Object clone() {
		SVGAnimatedTransformListImpl clone = new SVGAnimatedTransformListImpl();
		clone.baseVal = (SVGTransformList) ((SVGTransformListImpl)this.baseVal).clone();
		if (this.animVal != null)
			clone.animVal = (SVGTransformList) ((SVGTransformListImpl)this.animVal).clone();
		else
			clone.animVal = null;
		return clone;
	}

	
	public SVGTransformList getBaseVal() {
		return baseVal;
	}

	
	public SVGTransformList getAnimVal() {
		if (animVal != null)
			return animVal;
		else
			return baseVal;
	}

	
	public String toXmlString() {
		return this.getAnimVal().toXmlString();
	}
}
