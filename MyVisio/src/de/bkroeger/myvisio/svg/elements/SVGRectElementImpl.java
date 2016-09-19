package de.bkroeger.myvisio.svg.elements;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.logging.Logger;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGAnimatedBoolean;
import org.w3c.dom.svg.SVGAnimatedLength;
import org.w3c.dom.svg.SVGAnimatedTransformList;
import org.w3c.dom.svg.SVGElement;
import org.w3c.dom.svg.SVGMatrix;
import org.w3c.dom.svg.SVGRect;
import org.w3c.dom.svg.SVGRectElement;
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
 * <p>
 * The ‘rect’ element defines a rectangle which is axis-aligned with the current 
 * user coordinate system. Rounded rectangles can be achieved by setting appropriate 
 * values for attributes ‘rx’ and ‘ry’.
 * </p>
 * @author bk
 */
public class SVGRectElementImpl extends SVGBasicShapeElementImpl 
implements SVGRectElement, SVGShapeElement {
	
	private static final long serialVersionUID = 8673408808589092936L;

	private static final Logger logger = Logger.getLogger(SVGRectElementImpl.class.getName());
	
	private SVGAnimatedTransformList transform;
	
	private SVGAnimatedLength x;
	
	private SVGAnimatedLength y;
	
	private SVGAnimatedLength width;
	
	private SVGAnimatedLength height;
	
	private SVGAnimatedLength rx;
	
	private SVGAnimatedLength ry;
	
	private Path2D.Float path2d;
	
	/**
	 * Default-Konstruktor
	 */
	public SVGRectElementImpl() {
		super();
	}
	
	/** 
	 * Konstruktor.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public SVGRectElementImpl(float x, float y, float width, float height) {
		
		this.x = new SVGAnimatedLengthImpl(new SVGLengthImpl(x));
		this.y = new SVGAnimatedLengthImpl(new SVGLengthImpl(y));
		this.width = new SVGAnimatedLengthImpl(new SVGLengthImpl(width));
		this.height = new SVGAnimatedLengthImpl(new SVGLengthImpl(height));
	}
	
	
	public Object clone() {
		
		SVGRectElementImpl clone = new SVGRectElementImpl();
		super.copy(clone);
		
		clone.rx = (SVGAnimatedLength) ((SVGAnimatedLengthImpl) this.rx).clone();
		clone.ry = (SVGAnimatedLength) ((SVGAnimatedLengthImpl) this.ry).clone();
		clone.x  = (SVGAnimatedLength) ((SVGAnimatedLengthImpl) this.x).clone();
		clone.y  = (SVGAnimatedLength) ((SVGAnimatedLengthImpl) this.y).clone();
		clone.height = (SVGAnimatedLength) ((SVGAnimatedLengthImpl) this.height).clone();
		clone.width  = (SVGAnimatedLength) ((SVGAnimatedLengthImpl) this.width).clone();
		clone.transform = (SVGAnimatedTransformList) ((SVGAnimatedTransformListImpl) this.transform).clone();
		
		for (int i=0; i<this.getChildCount(); i++) {
			SVGElementImpl userObject = (SVGElementImpl)this.getChildAt(i);
			SVGElementImpl clonedObject = (SVGElementImpl) userObject.clone();
			clone.add(clonedObject);
			clonedObject.setParent(clone);
		}
		return clone;
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
//		‘x’
		if (elem.hasAttribute("x")) {
			this.x = new SVGAnimatedLengthImpl(
					new SVGLengthImpl(elem.getAttribute("x")));
		}
//		‘y’
		if (elem.hasAttribute("y")) {
			this.y = new SVGAnimatedLengthImpl(
					new SVGLengthImpl(elem.getAttribute("y")));
		}
//		‘width’
		if (elem.hasAttribute("width")) {
			this.width = new SVGAnimatedLengthImpl(
					new SVGLengthImpl(elem.getAttribute("width")));
			if (this.width.getBaseVal().getValueInSpecifiedUnits() < 0.0f) {
				throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Negative width not allowed!");
			}
		} else {
			this.width = new SVGAnimatedLengthImpl(
					new SVGLengthImpl(0.0f));
		}
//		‘height’
		if (elem.hasAttribute("height")) {
			this.height = new SVGAnimatedLengthImpl(
					new SVGLengthImpl(elem.getAttribute("height")));
			if (this.height.getBaseVal().getValueInSpecifiedUnits() < 0.0f) {
				throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Negative height not allowed!");
			}
		} else {
			this.height = new SVGAnimatedLengthImpl(
					new SVGLengthImpl(0.0f));
		}
//		‘rx’
		if (elem.hasAttribute("rx")) {
			this.rx = new SVGAnimatedLengthImpl(
					new SVGLengthImpl(elem.getAttribute("rx")));
		}
//		‘ry’
		if (elem.hasAttribute("ry")) {
			this.ry = new SVGAnimatedLengthImpl(
					new SVGLengthImpl(elem.getAttribute("ry")));
		}
		
		if (elem.hasAttribute("rx") == false && elem.hasAttribute("ry") == false) {
			this.rx = new SVGAnimatedLengthImpl(new SVGLengthImpl(0.0f));
			this.ry = new SVGAnimatedLengthImpl(new SVGLengthImpl(0.0f));
		}
		
		if (elem.hasAttribute("rx") == false && elem.hasAttribute("ry") == true) {
			this.rx = this.ry;
		}
		
		if (elem.hasAttribute("rx") == true && elem.hasAttribute("ry") == false) {
			this.ry = this.rx;
		}
		
		if (this.rx.getBaseVal().getValueInSpecifiedUnits() > (this.width.getBaseVal().getValueInSpecifiedUnits() / 2)) {
			this.rx.getBaseVal().setValueInSpecifiedUnits(this.width.getBaseVal().getValueInSpecifiedUnits() / 2);
		}
		
		if (this.ry.getBaseVal().getValueInSpecifiedUnits() > (this.height.getBaseVal().getValueInSpecifiedUnits() / 2)) {
			this.ry.getBaseVal().setValueInSpecifiedUnits(this.height.getBaseVal().getValueInSpecifiedUnits() / 2);
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
	 * Mathematically, a ‘rect’ element can be mapped to an equivalent ‘path’ element as follows: 
	 * (Note: all coordinate and length values are first converted into user space coordinates 
	 * according to Units.) <ul>
	 * <li>perform an absolute moveto operation to location (x+rx,y), 
	 * where x is the value of the ‘rect’ element's ‘x’ attribute converted to user space, 
	 * rx is the effective value of the ‘rx’ attribute converted to user space and y is 
	 * the value of the ‘y’ attribute converted to user space
	 * <li>perform an absolute horizontal lineto operation to location (x+width-rx,y), 
	 * where width is the ‘rect’ element's ‘width’ attribute converted to user space
	 * <li>perform an absolute elliptical arc operation to coordinate (x+width,y+ry), 
	 * where the effective values for the ‘rx’ and ‘ry’ attributes on the ‘rect’ element 
	 * converted to user space are used as the rx and ry attributes on the elliptical arc 
	 * command, respectively, the x-axis-rotation is set to zero, the large-arc-flag is set to zero, 
	 * and the sweep-flag is set to one
	 * <li>perform a absolute vertical lineto to location (x+width,y+height-ry), 
	 * where height is the ‘rect’ element's ‘height’ attribute converted to user space
	 * <li>perform an absolute elliptical arc operation to coordinate (x+width-rx,y+height)
	 * <li>perform an absolute horizontal lineto to location (x+rx,y+height)
	 * <li>perform an absolute elliptical arc operation to coordinate (x,y+height-ry)
	 * <li>perform an absolute absolute vertical lineto to location (x,y+ry)
	 * <li>perform an absolute elliptical arc operation to coordinate (x+rx,y)
	 * </ul></p>
	 * @param g2d
	 */
	public void paint(Graphics2D g2d) {
		
		logger.info(String.format("Paint rect: x=%f y=%f width=%f height=%f", 
				this.x.getBaseVal().getValueInSpecifiedUnits(), 
				this.y.getBaseVal().getValueInSpecifiedUnits(), 
				this.width.getBaseVal().getValueInSpecifiedUnits(), 
				this.height.getBaseVal().getValueInSpecifiedUnits()));
		
		Point2D.Float p1 = convertCoordinate(
			new Point2D.Float(
				this.x.getBaseVal().getValueInSpecifiedUnits(),
				this.y.getBaseVal().getValueInSpecifiedUnits()
				));
		Point2D.Float p2 = convertCoordinate(
			new Point2D.Float(
				this.x.getBaseVal().getValueInSpecifiedUnits() + this.width.getBaseVal().getValueInSpecifiedUnits(),
				this.y.getBaseVal().getValueInSpecifiedUnits()
				));
		Point2D.Float p3 = convertCoordinate(
			new Point2D.Float(
				this.x.getBaseVal().getValueInSpecifiedUnits() + this.width.getBaseVal().getValueInSpecifiedUnits(),
				this.y.getBaseVal().getValueInSpecifiedUnits() + this.height.getBaseVal().getValueInSpecifiedUnits()
				));
		Point2D.Float p4 = convertCoordinate(
			new Point2D.Float(
				this.x.getBaseVal().getValueInSpecifiedUnits(),
				this.y.getBaseVal().getValueInSpecifiedUnits() + this.height.getBaseVal().getValueInSpecifiedUnits()
				));
		
		logger.info(String.format("Paint rect (converted): p1:x=%f/y=%f p2:x=%f/y=%f p3:x=%f/y=%f p4:x=%f/y=%f", 
				p1.x, p1.y, p2.x, p2.y,
				p3.x, p3.y, p4.x, p4.y));
		
		// Shape erstellen
		path2d = new Path2D.Float();
		path2d.moveTo(p1.x, p1.y);
		path2d.lineTo(p2.x, p2.y);
		path2d.lineTo(p3.x, p3.y);
		path2d.lineTo(p4.x, p4.y);
		path2d.closePath();
		
		// Rand zeichnen und Shape füllen
		drawFillAndStroke(g2d, path2d);
	}
	
	
	public boolean containsPoint(Point.Float p) {
		boolean ptfound = false;
		if (path2d != null && path2d.contains(p)) ptfound = true;
		logger.info("Rect contains point="+p.toString()+": "+ptfound);
		return ptfound;
	}
	
	public Point2D.Float convertCoordinate(Point2D.Float p1) {
		
		Point2D.Float p = (Point2D.Float)p1.clone();
		if (this.transform != null && this.transform.getAnimVal() != null) {
			SVGTransformList transformList = this.transform.getAnimVal();
			for (long i=transformList.getNumberOfItems()-1; i>=0; i--) {
				SVGTransformImpl transformImpl = (SVGTransformImpl) transformList.getItem(i);
				Point2D.Float q = p;
				p = transformImpl.transform(p);
				logger.info(String.format("SVGRectElementImpl::convertCoordinate From=%f/%f To=%f/%f",
						q.x, q,y, p.x, p.y));
			}
		}
		
		SVGElementImpl parentNode = (SVGElementImpl) this.getParent();
		if (parentNode != null) {
			SVGStructuralElement parent = (SVGStructuralElement) parentNode;
			p = parent.convertCoordinate(p);
		}
		return p;
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

	
	public SVGAnimatedBoolean getExternalResourcesRequired() {
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

	
	public SVGAnimatedLength getRx() {
		return rx;
	}

	
	public SVGAnimatedLength getRy() {
		return ry;
	}

	
	public SVGAnimatedLength getX() {
		return x;
	}

	
	public SVGAnimatedLength getY() {
		return y;
	}

	
	public SVGAnimatedLength getWidth() {
		return width;
	}

	
	public SVGAnimatedLength getHeight() {
		return height;
	}
	
	
	public void convertToXml(Document doc, Element parent) {
		
		Element domElem = doc.createElement("circle");
		if (this.id != null)
			domElem.setAttribute("id", this.id);
		if (this.getClassName() != null)
			domElem.setAttribute("class", this.getClassName().toXmlString());
		if (this.transform != null) 
			domElem.setAttribute("transform", this.transform.toXmlString());
		domElem.setAttribute("x", this.x.toXmlString());
		domElem.setAttribute("y", this.y.toXmlString());
		if (this.rx != null)
			domElem.setAttribute("rx", this.rx.toXmlString());
		if (this.ry != null)
			domElem.setAttribute("ry", this.ry.toXmlString());
		domElem.setAttribute("with", this.width.toXmlString());
		domElem.setAttribute("height", this.height.toXmlString());
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
