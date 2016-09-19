package de.bkroeger.myvisio.svg.elements;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGTitleElement;

import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * <p>
 * Each container element or graphics element in an SVG drawing can supply a ‘desc’ 
 * and/or a ‘title’ description string where the description is text-only. 
 * When the current SVG document fragment is rendered as SVG on visual media, ‘desc’ 
 * and ‘title’ elements are not rendered as part of the graphics. User agents may, 
 * however, for example, display the ‘title’ element as a tooltip, as the pointing device 
 * moves over particular elements. Alternate presentations are possible, both visual and aural, 
 * which display the ‘desc’ and ‘title’ elements but do not display ‘path’ elements 
 * or other graphics elements. This is readily achieved by using a different (perhaps user) 
 * style sheet. For deep hierarchies, and for following ‘use’ element references, it is 
 * sometimes desirable to allow the user to control how deep they drill down into descriptive text.
 * </p><p>
 * In conforming SVG document fragments, any ‘title’ element should be the first child element 
 * of its parent. Note that those implementations that do use ‘title’ to display a tooltip 
 * often will only do so if the ‘title’ is indeed the first child element of its parent.
 * </p>
 * @author bk
 */
public class SVGTitleElementImpl extends SVGElementImpl implements SVGTitleElement {
	
	private static final long serialVersionUID = -2871190313324811506L;
	
	protected String textContent = "";
	
	public SVGTitleElementImpl() {
		
	}
	
	public SVGTitleElementImpl(String string) {
		this.textContent = string;
	}

	public Object clone() {
		SVGTitleElementImpl clone = new SVGTitleElementImpl();
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
	 */
	protected void parseXml(Element elem) throws TechnicalException {
		super.parseXml(elem);
		
		textContent = elem.getTextContent();
	}
	
	public void convertToXml(Document doc, Element parent) {
		
		Element titleElem = doc.createElement("title");
		titleElem.setTextContent(this.textContent);
		parent.appendChild(titleElem);
	}
}
