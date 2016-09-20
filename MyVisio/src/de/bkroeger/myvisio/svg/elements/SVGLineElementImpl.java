package de.bkroeger.myvisio.svg.elements;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.logging.Logger;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGAnimatedBoolean;
import org.w3c.dom.svg.SVGAnimatedLength;
import org.w3c.dom.svg.SVGAnimatedTransformList;
import org.w3c.dom.svg.SVGElement;
import org.w3c.dom.svg.SVGLineElement;
import org.w3c.dom.svg.SVGMatrix;
import org.w3c.dom.svg.SVGRect;
import org.w3c.dom.svg.SVGShapeElement;
import org.w3c.dom.svg.SVGStringList;
import org.w3c.dom.svg.SVGStructuralElement;
import org.w3c.dom.svg.SVGTransformList;

import de.bkroeger.myvisio.svg.SVGAnimatedLengthImpl;
import de.bkroeger.myvisio.svg.SVGAnimatedTransformListImpl;
import de.bkroeger.myvisio.svg.SVGLengthImpl;
import de.bkroeger.myvisio.svg.SVGTransformImpl;
import de.bkroeger.myvisio.svg.SVGTransformListImpl;
import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * 
 * @author bk
 */
public class SVGLineElementImpl extends SVGBasicShapeElementImpl 
implements SVGLineElement, SVGShapeElement {

	private static final long serialVersionUID = -2279061010247120773L;

	private static final Logger logger = Logger.getLogger(SVGEllipseElementImpl.class.getName());
	
	private SVGAnimatedTransformList transform;
	
	private SVGAnimatedLength x1;
	
	private SVGAnimatedLength y1;
	
	private SVGAnimatedLength x2;
	
	private SVGAnimatedLength y2;
	
	/**
	 * 
	 */
	public SVGLineElementImpl() {
		super();
	}
	
	
	public Object clone() {
		
		SVGLineElementImpl clone = new SVGLineElementImpl();
		super.copy(clone);
		
		clone.x1 = (SVGAnimatedLength) ((SVGAnimatedLengthImpl) this.x1).clone();
		clone.y2 = (SVGAnimatedLength) ((SVGAnimatedLengthImpl) this.y2).clone();
		clone.x2 = (SVGAnimatedLength) ((SVGAnimatedLengthImpl) this.x2).clone();
		clone.y2 = (SVGAnimatedLength) ((SVGAnimatedLengthImpl) this.y2).clone();
		clone.transform = (SVGAnimatedTransformList) ((SVGAnimatedTransformListImpl) this.transform).clone();
		
		for (int i=0; i<this.getChildCount(); i++) {
			SVGElementImpl userObject = (SVGElementImpl)this.getChildAt(i);
			if (userObject != null) {
				SVGElementImpl clonedObject = (SVGElementImpl) userObject.clone();
				clone.add(clonedObject);
				clonedObject.setParent(clone);
			}
		}
		return clone;
	}

	public SVGAnimatedLength getX1() {
		return this.x1;
	}

	
	public SVGAnimatedLength getY1() {
		return this.y1;
	}

	
	public SVGAnimatedLength getX2() {
		return this.x2;
	}

	
	public SVGAnimatedLength getY2() {
		return this.y2;
	}

	/**
	 * Liest Attribute und Struktur des SVG-Tag ein.
	 * 
	 * @param elem
	 * @throws TechnicalException 
	 */
	public void parseXml(Element elem) throws TechnicalException {
		
		logger.entering(this.getClass().getName(), "parseXml", elem);
		
		parseBaseAttributes(elem);
		parseStylableAttributes(elem);
		parseConditionalProcessingAttributes(elem);
		parseGraphicalEventAttributes(elem);
		parsePresentationAttributes(elem);
		
//		‘transform’
		if (elem.hasAttribute("transform")) {
			this.transform = new SVGAnimatedTransformListImpl(
					new SVGTransformListImpl(elem.getAttribute("transform")));
		}
//		‘cx’
		if (elem.hasAttribute("x1")) {
			this.x1 = new SVGAnimatedLengthImpl(
					new SVGLengthImpl(elem.getAttribute("x1")));
		}
//		‘cy’
		if (elem.hasAttribute("y1")) {
			this.y1 = new SVGAnimatedLengthImpl(
					new SVGLengthImpl(elem.getAttribute("y1")));
		}
//		‘rx’
		if (elem.hasAttribute("x2")) {
			this.x2 = new SVGAnimatedLengthImpl(
					new SVGLengthImpl(elem.getAttribute("x2")));
		}
//		‘ry’
		if (elem.hasAttribute("y2")) {
			this.y2 = new SVGAnimatedLengthImpl(
					new SVGLengthImpl(elem.getAttribute("y2")));
		}
		
		// die Substruktur des DOM-Elements wird hier analysiert
		NodeList children = elem.getChildNodes();
		for (int i=0; i<children.getLength(); i++) {
			Node child = children.item(i);
			
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				Element childElem = (Element)child;
			
				switch (childElem.getLocalName()) {
				// Animation Elements:
				case "animateColor":
					break;
				case "animateMotion":
					break;
				case "animateTransform":
					break;
				case "animate":
					break;
				case "set":
					break;
				// Descriptive Elements:
				case "desc":
				case "title":
				case "metadata":
					parseDescriptiveElements(childElem);
					break;
				default:
					throw new DOMException(DOMException.NOT_SUPPORTED_ERR, 
							"Tag <"+elem.getLocalName()+"> not allowed below <rect>!");
				}
			}
		}
	}

	public void paint(Graphics2D g2d) {
		
		logger.info(String.format("Paint line: x1=%f y1=%f x2=%f y2=%f", 
				this.x1.getBaseVal().getValueInSpecifiedUnits(), 
				this.y1.getBaseVal().getValueInSpecifiedUnits(), 
				this.x2.getBaseVal().getValueInSpecifiedUnits(), 
				this.y2.getBaseVal().getValueInSpecifiedUnits()));

		Point2D.Float p = new Point2D.Float(
				this.x1.getBaseVal().getValueInSpecifiedUnits(),
				this.y1.getBaseVal().getValueInSpecifiedUnits()
				);
		Point2D.Float p1 = convertCoordinate(p);
		p = new Point2D.Float(
				this.x2.getBaseVal().getValueInSpecifiedUnits(),
				this.y2.getBaseVal().getValueInSpecifiedUnits()
				);
		Point2D.Float p2 = convertCoordinate(p);
				
		Line2D line2d = new Line2D.Float(p1, p2);

		// Rand zeichnen und Shape füllen
		drawFillAndStroke(g2d, line2d);
	}


	@Override
	public SVGStringList getRequiredFeatures() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public SVGStringList getRequiredExtensions() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public SVGStringList getSystemLanguage() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean hasExtension(String extension) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public SVGAnimatedBoolean getExternalResourcesRequired() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public SVGAnimatedTransformList getTransformList() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public SVGElement getNearestViewportElement() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public SVGElement getFarthestViewportElement() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public SVGRect getBBox() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public SVGMatrix getCTM() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public SVGMatrix getScreenCTM() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public SVGMatrix getTransformToElement(SVGElement element) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Point2D.Float convertCoordinate(Point2D.Float p1) {
		
		Point2D.Float p = (Point2D.Float)p1.clone();
		if (this.transform != null && this.transform.getAnimVal() != null) {
			SVGTransformList transformList = this.transform.getAnimVal();
			for (long i=transformList.getNumberOfItems()-1; i>=0; i--) {
				SVGTransformImpl transformImpl = (SVGTransformImpl) transformList.getItem(i);
				p = transformImpl.transform(p);
			}
		}
		
		SVGElementImpl parentNode = (SVGElementImpl) this.getParent();
		if (parentNode != null) {
			SVGStructuralElement parent = (SVGStructuralElement) parentNode;
			p = parent.convertCoordinate(p);
		}
		return p;
	}
}
