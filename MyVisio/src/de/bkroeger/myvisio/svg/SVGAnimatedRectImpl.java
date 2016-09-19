package de.bkroeger.myvisio.svg;

import java.io.Serializable;

import org.w3c.dom.svg.SVGAnimatedRect;
import org.w3c.dom.svg.SVGRect;

public class SVGAnimatedRectImpl implements SVGAnimatedRect, Serializable {
	
	private static final long serialVersionUID = -462372207168143178L;

	private SVGRect baseVal;
	
	private SVGRect animVal;

	public SVGAnimatedRectImpl() {
		baseVal = new SVGRectImpl();
		animVal = new SVGRectImpl();
	}

	public SVGAnimatedRectImpl(SVGRectImpl baseVal) {
		this.baseVal = baseVal;
		this.animVal = (SVGRect) baseVal.clone();
	}

	
	public SVGRect getBaseVal() {
		return baseVal;
	}

	
	public SVGRect getAnimVal() {
		return animVal;
	}

}
