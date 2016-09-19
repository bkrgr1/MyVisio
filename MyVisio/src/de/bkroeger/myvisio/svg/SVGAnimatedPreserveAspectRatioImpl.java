package de.bkroeger.myvisio.svg;

import java.io.Serializable;

import org.w3c.dom.svg.SVGAnimatedPreserveAspectRatio;
import org.w3c.dom.svg.SVGPreserveAspectRatio;

public class SVGAnimatedPreserveAspectRatioImpl implements
		SVGAnimatedPreserveAspectRatio, Serializable {
	
	private static final long serialVersionUID = -321061803288058961L;
	
	private SVGPreserveAspectRatio baseVal;
	private SVGPreserveAspectRatio animVal;

	public SVGAnimatedPreserveAspectRatioImpl() {
		this.baseVal = new SVGPreserveAspectRatioImpl();
		this.animVal = new SVGPreserveAspectRatioImpl();
	}

	public SVGAnimatedPreserveAspectRatioImpl(SVGPreserveAspectRatio baseVal) {
		this.baseVal = baseVal;
		this.animVal = baseVal.clone();
	}

	
	public SVGPreserveAspectRatio getBaseVal() {
		return baseVal;
	}

	
	public SVGPreserveAspectRatio getAnimVal() {
		return animVal;
	}

}
