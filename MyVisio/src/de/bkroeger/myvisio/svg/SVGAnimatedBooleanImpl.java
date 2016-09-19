package de.bkroeger.myvisio.svg;

import java.io.Serializable;

import org.w3c.dom.svg.SVGAnimatedBoolean;

public class SVGAnimatedBooleanImpl implements SVGAnimatedBoolean, Serializable {
	
	private static final long serialVersionUID = -4482521671026587886L;

	protected boolean baseValue;
	
	protected boolean animValue;

	public SVGAnimatedBooleanImpl(boolean baseValue) {
		this.baseValue = baseValue;
		this.animValue = baseValue;
	}
	
	protected void setAnimValue(boolean value) {
		this.animValue = value;
	}

	
	public boolean getBaseVal() {
		return this.baseValue;
	}

	
	public boolean getAnimVal() {
		return this.animValue;
	}

	
	public void setBaseVal(boolean baseVal) {
		// TODO Auto-generated method stub
		
	}

}
