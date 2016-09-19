package de.bkroeger.myvisio.svg;

import java.io.Serializable;

import org.w3c.dom.svg.SVGAnimatedLength;
import org.w3c.dom.svg.SVGLength;

public class SVGAnimatedLengthImpl implements SVGAnimatedLength, Serializable {
	
	private static final long serialVersionUID = 3358131645774026467L;

	protected SVGLength baseValue;
	
	protected SVGLength animValue;

	public SVGAnimatedLengthImpl(SVGLength baseValue) {
		this.baseValue = baseValue;
		this.animValue = (SVGLength)((SVGLengthImpl)baseValue).clone();
	}
	
	public Object clone() {
		SVGAnimatedLengthImpl clone = new SVGAnimatedLengthImpl((SVGLength) ((SVGLengthImpl)this.baseValue).clone());
		clone.animValue = (SVGLength) ((SVGLengthImpl)this.animValue).clone();
		return clone;
	}
	
	protected void setAnimValue(SVGLength value) {
		this.animValue = value;
	}

	
	public SVGLength getBaseVal() {
		return this.baseValue;
	}

	
	public SVGLength getAnimVal() {
		return this.animValue;
	}

	public String toXmlString() {
		return ((SVGLengthImpl)this.animValue).toXmlString();
	}
}
