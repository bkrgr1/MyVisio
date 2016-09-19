package de.bkroeger.myvisio.svg.elements;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGMetadataElement;
import org.w3c.dom.svg.SVGTitleElement;

import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * <p>
 * </p>
 * @author bk
 */
public class SVGMetadataElementImpl extends SVGElementImpl implements SVGMetadataElement {
	
	private static final long serialVersionUID = -2871190313324811506L;
	
	protected Element w3cElement;
	
	public SVGMetadataElementImpl() {
		
	}
	
	public SVGMetadataElementImpl(Element w3cElement) {
		this.w3cElement = w3cElement;
	}

	public Object clone() {
		SVGMetadataElementImpl clone = new SVGMetadataElementImpl();
		return clone;
	}

	/**
	 * Analysiert das XML-Element.
	 * 
	 * @param elem - das DOM-Element
	 */
	protected void parseXml(Element elem) throws TechnicalException {
		super.parseXml(elem);
		
	}
	
	public void convertToXml(Document doc, Element parent) {
		
		Element titleElem = doc.createElement("metadata");
		parent.appendChild(titleElem);
	}
}
