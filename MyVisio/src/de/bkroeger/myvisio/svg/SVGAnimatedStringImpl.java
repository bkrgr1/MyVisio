package de.bkroeger.myvisio.svg;

import java.io.Serializable;

import org.w3c.dom.svg.SVGAnimatedString;

public class SVGAnimatedStringImpl implements SVGAnimatedString, Serializable {
	
	private static final long serialVersionUID = 8391230936579989891L;

	private String baseVal;
	
	private String animVal;

	public SVGAnimatedStringImpl() {
		this.baseVal = "";
		this.animVal = "";
	}
	
	public SVGAnimatedStringImpl(String baseVal) {
		this.baseVal = baseVal;
		if (baseVal != null)
			this.animVal = new String(baseVal);
		else
			this.animVal = null;
	}
	
	public Object clone() {
		
		SVGAnimatedStringImpl clone = new SVGAnimatedStringImpl();
		clone.baseVal = baseVal;
		clone.animVal = animVal;
		return null;
	}

	
	public String getBaseVal() {
		return baseVal;
	}

	
	public void setBaseVal(String baseVal) {
		this.baseVal = baseVal;
	}

	
	public String getAnimVal() {
		return animVal;
	}

	/**
	 * Prüft, ob diese String-Liste einen bestimmten String enthält.
	 * @param value - der gesuchte String
	 * @return - True, wenn der Wert gefunden wurde.
	 */
	public boolean containsString(String value) {
		String valueList = this.getAnimVal();
		if (valueList != null) {
			String[] values = valueList.split("\\s+");
			for (String string : values) {
				if (string.equals(value)) {
					return true;
				}
			}
		}
		return false;
	}

	
	public String toXmlString() {
		
		return this.getAnimVal();
	}
}
