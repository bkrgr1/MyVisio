package de.bkroeger.myvisio.svg;

import java.io.Serializable;

import org.junit.Assert;
import org.w3c.dom.svg.SVGAnimatedLengthList;
import org.w3c.dom.svg.SVGLengthList;


public class SVGAnimatedLengthListImpl implements SVGAnimatedLengthList, Serializable {
	
	private static final long serialVersionUID = -1181680307514645593L;

	SVGLengthList baseVal;
	
	SVGLengthList animVal;
	
	public SVGAnimatedLengthListImpl() {
		this.baseVal = new SVGLengthListImpl();
		this.animVal = new SVGLengthListImpl(baseVal);
	}
	
	public SVGAnimatedLengthListImpl(SVGLengthList baseVal) {
		Assert.assertNotNull(baseVal);
		this.baseVal = baseVal;
		this.animVal = new SVGLengthListImpl(baseVal);
	}
	
	public SVGAnimatedLengthListImpl(SVGLengthList baseVal, SVGLengthList animVal) {
		this.baseVal = baseVal;
		this.animVal = animVal;
	}

	
	public SVGLengthList getBaseVal() {
		return baseVal;
	}

	
	public SVGLengthList getAnimVal() {
		return animVal;
	}

}
