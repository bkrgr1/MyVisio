package de.bkroeger.myvisio.shapes;

import java.awt.Shape;
import java.awt.geom.Point2D.Float;
import java.util.UUID;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGElement;
import org.w3c.dom.svg.SVGRect;
import org.w3c.dom.svg.SVGRectElement;

import de.bkroeger.myvisio.svg.SVGRectImpl;
import de.bkroeger.myvisio.svg.elements.SVGSVGElementImpl;
import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * <p>
 * Ein ShapeElement ist ein spezielles SVG-Element, das ein Shape repräsentiert.
 * </p>
 * @author bk
 */
public class ShapeElement extends SVGSVGElementImpl implements IShape {

	private static final long serialVersionUID = 4910489430622782821L;
	
	/**
	 * Die XML-Attribute und Sub-Element einlesen
	 * 
	 * @param elem - das zu untersuchende XML-Element
	 */
	@Override
	public void parseXml(Element elem) throws TechnicalException {
		// Attribute und Sub-Elemente von SVGSVGElement einlesen
		super.parseXml(elem);
		
		// UUID einlesen
		if (elem.hasAttribute("uuid")) {
			this.uuid = UUID.fromString(elem.getAttribute("uuid"));
		}
	}

	@Override
	public void store(Document doc, Element shapeElem) {
		
		super.convertToXml(doc, shapeElem);
	}

	@Override
	public Shape getBoundingRectangle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOrigin(Float point) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isSelected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setSelected(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void negotiateViewport(SVGElement viewport) {
		if (viewport instanceof SVGRectElement) {
			SVGRectElement rectElem = (SVGRectElement)viewport;
			
			SVGRect rect = new SVGRectImpl();
			rect.setHeight(rectElem.getHeight().getAnimVal().getValue());
			rect.setWidth(rectElem.getWidth().getAnimVal().getValue());
			
			super.negotiateViewport(rect);
//		} else {
//			throw new TechnicalException("Invalid viewport element. Type="+viewport.getClass().getName());
		}
	}
}
