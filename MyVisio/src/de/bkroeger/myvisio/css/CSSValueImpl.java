package de.bkroeger.myvisio.css;

import org.w3c.dom.DOMException;
import org.w3c.dom.css.CSSValue;

public class CSSValueImpl implements CSSValue {
	
	protected String cssText;
	
	protected short cssValueType;
	
	public CSSValueImpl(String cssText, short cssValueType) {
		this.cssText = cssText;
		this.cssValueType = cssValueType;
	}

	
	public String getCssText() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setCssText(String cssText) throws DOMException {
		// TODO Auto-generated method stub

	}

	
	public short getCssValueType() {
		// TODO Auto-generated method stub
		return 0;
	}

}
