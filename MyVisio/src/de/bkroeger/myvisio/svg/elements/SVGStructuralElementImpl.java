package de.bkroeger.myvisio.svg.elements;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGAnimatedTransformList;
import org.w3c.dom.svg.SVGStructuralElement;

import de.bkroeger.myvisio.svg.SVGAnimatedTransformListImpl;
import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * <p>
 * The structural elements are those which define the primary structure of an SVG document.
 * Specifically, the following elements are structural elements: ‘defs’, ‘g’, ‘svg’, 
 * ‘symbol’ and ‘use’.
 * </p>
 * @author bk
 */
public class SVGStructuralElementImpl extends SVGContainerElementImpl 
implements SVGStructuralElement {
	
	private static final long serialVersionUID = -6602958178676163705L;
	
	protected SVGAnimatedTransformList transformList;
	
	public void setTransformList(SVGAnimatedTransformList transformList) { 
		this.transformList = transformList; }
	
	public SVGAnimatedTransformList getTransformList() { return this.transformList; }

	public SVGStructuralElementImpl() {
		super();
		
		this.transformList = new SVGAnimatedTransformListImpl();
	}
	
	public SVGStructuralElementImpl(Element elem) {
		super(elem);
		
		this.transformList = new SVGAnimatedTransformListImpl();
	}

	
	public Object clone() {
		return null;
	}
	
	protected void parseSVGNode(Element childElem) throws TechnicalException {
		SVGSVGElementImpl svgElem = new SVGSVGElementImpl();
		this.add(svgElem);	
		svgElem.setParent(this);
		svgElem.parseXml(childElem);
	}
	
	protected void parseGroupNode(Element childElem) throws TechnicalException {
		
		SVGGElementImpl gElem = new SVGGElementImpl();
		this.add(gElem);	
		gElem.setParent(this);
		gElem.parseXml(childElem);
	}
	
	
	public void paint(Graphics2D g2d) {
		// no action
	}

	
	public Point2D.Float convertCoordinate(Point2D.Float p1) {
		return null;
	}
}
