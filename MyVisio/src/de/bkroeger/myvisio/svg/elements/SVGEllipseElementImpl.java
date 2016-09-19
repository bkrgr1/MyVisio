package de.bkroeger.myvisio.svg.elements;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.logging.Logger;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.svg.SVGAnimatedBoolean;
import org.w3c.dom.svg.SVGAnimatedLength;
import org.w3c.dom.svg.SVGAnimatedString;
import org.w3c.dom.svg.SVGAnimatedTransformList;
import org.w3c.dom.svg.SVGElement;
import org.w3c.dom.svg.SVGEllipseElement;
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

public class SVGEllipseElementImpl extends SVGBasicShapeElementImpl 
	implements SVGEllipseElement, SVGShapeElement {
	
	private static final long serialVersionUID = -8774312298977091633L;

	private static final Logger logger = Logger.getLogger(SVGEllipseElementImpl.class.getName());
	
	private SVGAnimatedTransformList transform;
	
	private SVGAnimatedLength cx;
	
	private SVGAnimatedLength cy;
	
	private SVGAnimatedLength rx;
	
	private SVGAnimatedLength ry;
	
	private Ellipse2D.Float ellipse2d;
	
	public SVGEllipseElementImpl() {
		super();
	}
	
	
	public Object clone() {
		
		SVGEllipseElementImpl clone = new SVGEllipseElementImpl();
		super.copy(clone);
		
		clone.cx = (SVGAnimatedLength) ((SVGAnimatedLengthImpl) this.cx).clone();
		clone.cy = (SVGAnimatedLength) ((SVGAnimatedLengthImpl) this.cy).clone();
		clone.rx = (SVGAnimatedLength) ((SVGAnimatedLengthImpl) this.rx).clone();
		clone.ry = (SVGAnimatedLength) ((SVGAnimatedLengthImpl) this.ry).clone();
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

	
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setId(String id) {
		// TODO Auto-generated method stub

	}

	
	public String getXMLbase() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setXMLbase(String XMLbase) {
		// TODO Auto-generated method stub

	}

	
	public SVGAnimatedBoolean getExternalResourcesRequired() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGAnimatedString getClassName() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public CSSStyleDeclaration getStyle() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public CSSValue getPresentationAttribute(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGAnimatedTransformList getTransformList() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGElement getNearestViewportElement() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGElement getFarthestViewportElement() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGRect getBBox() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGMatrix getCTM() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGMatrix getScreenCTM() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGMatrix getTransformToElement(SVGElement element) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGAnimatedLength getCx() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGAnimatedLength getCy() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGAnimatedLength getRx() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGAnimatedLength getRy() {
		// TODO Auto-generated method stub
		return null;
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
		
//		�transform�
		if (elem.hasAttribute("transform")) {
			this.transform = new SVGAnimatedTransformListImpl(
					new SVGTransformListImpl(elem.getAttribute("transform")));
		}
//		�cx�
		if (elem.hasAttribute("cx")) {
			this.cx = new SVGAnimatedLengthImpl(
					new SVGLengthImpl(elem.getAttribute("cx")));
		}
//		�cy�
		if (elem.hasAttribute("cy")) {
			this.cy = new SVGAnimatedLengthImpl(
					new SVGLengthImpl(elem.getAttribute("cy")));
		}
//		�rx�
		if (elem.hasAttribute("rx")) {
			this.rx = new SVGAnimatedLengthImpl(
					new SVGLengthImpl(elem.getAttribute("rx")));
		}
//		�ry�
		if (elem.hasAttribute("ry")) {
			this.ry = new SVGAnimatedLengthImpl(
					new SVGLengthImpl(elem.getAttribute("ry")));
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
	
	/**
	 * <p>
	 * The arc of an �ellipse� element begins at the "3 o'clock" point on the radius and 
	 * progresses towards the "9 o'clock" point. The starting point and direction of the arc 
	 * are affected by the user space transform in the same manner as the geometry of the element.
	 * </p>
	 * @param g2d
	 */
	public void paint(Graphics2D g2d) {
		
		Point2D.Float center = convertCoordinate(
			new Point2D.Float(
				this.cx.getAnimVal().getValueInSpecifiedUnits(),
				this.cy.getAnimVal().getValueInSpecifiedUnits()
				));
		Point2D.Float corner = convertCoordinate(
				new Point2D.Float(
					this.cx.getAnimVal().getValueInSpecifiedUnits() - this.rx.getAnimVal().getValueInSpecifiedUnits(),
					this.cy.getAnimVal().getValueInSpecifiedUnits() - this.ry.getAnimVal().getValueInSpecifiedUnits()
					));
				
		ellipse2d = new Ellipse2D.Float();
		ellipse2d.setFrameFromCenter(center, corner);

		// Rand zeichnen und Shape f�llen
		drawFillAndStroke(g2d, ellipse2d);
	}
	
	public boolean containsPoint(Point.Float p) {
		if (ellipse2d != null && ellipse2d.contains(p)) return true;
		return false;
	}

	public Point2D.Float convertCoordinate(Point2D.Float p1) {
		
		Point2D.Float p = p1;
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
			return parent.convertCoordinate(p);
		} else {
			return p;
		}
	}

	
	public SVGStringList getRequiredFeatures() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGStringList getRequiredExtensions() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGStringList getSystemLanguage() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public boolean hasExtension(String extension) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public void convertToXml(Document doc, Element parent) {
		
		Element domElem = doc.createElement("circle");
		if (this.id != null)
			domElem.setAttribute("id", this.id);
		if (this.getClassName() != null)
			domElem.setAttribute("class", this.getClassName().toXmlString());
		if (this.transform != null) 
			domElem.setAttribute("transform", this.transform.toXmlString());
		domElem.setAttribute("cx", this.cx.toXmlString());
		domElem.setAttribute("cy", this.cy.toXmlString());
		domElem.setAttribute("rx", this.rx.toXmlString());
		domElem.setAttribute("ry", this.ry.toXmlString());
		if (this.fill != null)
			domElem.setAttribute("fill", this.fill.toXmlString());
		if (this.fillRule != null)
			domElem.setAttribute("fill-rule", this.fillRule.toXmlString());
		if (this.stroke != null)
			domElem.setAttribute("stroke", this.stroke.toXmlString());
		if (this.strokeWidth != null)
			domElem.setAttribute("stroke-width", this.strokeWidth.toXmlString());
		if (this.strokeDasharray != null)
			domElem.setAttribute("stroke-dasharray", this.strokeDasharray.toXmlString());
		if (this.strokeLinecap != null)
			domElem.setAttribute("stroke-linecap", this.strokeLinecap.toXmlString());
		if (this.strokeLinejoin != null)
			domElem.setAttribute("stroke-linejoin", this.strokeLinejoin.toXmlString());
//		if (this.opacity != null)
//			domElem.setAttribute("opacity", this.opacity.toXmlString());
		parent.appendChild(domElem);
		
		if (this.description != null) {
			this.description.convertToXml(doc, domElem);
		}
		
		if (this.title != null) {
			this.title.convertToXml(doc, domElem);
		}
		
		for (int i=0; i<this.getChildCount(); i++) {
			SVGElementImpl child = (SVGElementImpl) this.getChildAt(i);
			child.convertToXml(doc, domElem);
		}
	}
}
