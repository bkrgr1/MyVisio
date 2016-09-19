package de.bkroeger.myvisio.svg.elements;

import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGContainerElement;

public class SVGContainerElementImpl extends SVGElementImpl implements SVGContainerElement {

	private static final long serialVersionUID = 8773420952671153832L;

	public SVGContainerElementImpl() {
		super();
	}
	
	public SVGContainerElementImpl(Element elem) {
		super(elem);
	}
}
