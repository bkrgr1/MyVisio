package de.bkroeger.myvisio.svg.elements;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDescElement;

import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * Each container element or graphics element in an SVG drawing can supply a ‘desc’ and/or a ‘title’ 
 * description string where the description is text-only.
 * 
 * @author bk
 */
public class SVGDescElementImpl extends SVGElementImpl implements SVGDescElement {
	
	private static final long serialVersionUID = -817548403246202561L;
	
	protected String textContent = "";
	/**
	 * @return - den Text-Content
	 */
	public String getText() {
		return textContent;
	}
	/**
	 * @param value
	 */
	public void setText(String value) {
		textContent = value;
	}
	
	/**
	 * Default-Konstruktor
	 */
	public SVGDescElementImpl() {
		
	}
	
	public Object clone() {
		SVGDescElementImpl clone = new SVGDescElementImpl();
		clone.textContent = new String(textContent);
		return clone;
	}

	@Override
	public String toString() {
		return this.textContent;
	}

	/**
	 * Analysiert das XML-Element.
	 * 
	 * @param elem - das DOM-Element
	 * @throws TechnicalException 
	 */
	protected void parseXml(Element elem) throws TechnicalException {
		super.parseXml(elem);
		
		textContent = elem.getTextContent();
	}
	
	public void convertToXml(Document doc, Element parent) {
		
		Element descElem = doc.createElement("desc");
		descElem.setTextContent(this.textContent);
		parent.appendChild(descElem);
	}
}
