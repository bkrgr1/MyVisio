package de.bkroeger.myvisio.svg.elements;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.logging.Logger;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGAnimatedBoolean;
import org.w3c.dom.svg.SVGAnimatedEnumeration;
import org.w3c.dom.svg.SVGAnimatedLength;
import org.w3c.dom.svg.SVGAnimatedLengthList;
import org.w3c.dom.svg.SVGAnimatedNumberList;
import org.w3c.dom.svg.SVGAnimatedTransformList;
import org.w3c.dom.svg.SVGElement;
import org.w3c.dom.svg.SVGLength;
import org.w3c.dom.svg.SVGMatrix;
import org.w3c.dom.svg.SVGPoint;
import org.w3c.dom.svg.SVGRect;
import org.w3c.dom.svg.SVGStringList;
import org.w3c.dom.svg.SVGStructuralElement;
import org.w3c.dom.svg.SVGTextElement;
import org.w3c.dom.svg.SVGTransformList;

import de.bkroeger.myvisio.svg.SVGAnimatedLengthListImpl;
import de.bkroeger.myvisio.svg.SVGAnimatedTransformListImpl;
import de.bkroeger.myvisio.svg.SVGLengthImpl;
import de.bkroeger.myvisio.svg.SVGTransformImpl;
import de.bkroeger.myvisio.svg.SVGTransformListImpl;
import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * @author bk
 */
public class SVGTextElementImpl extends SVGTextPositioningElementImpl 
implements SVGTextElement {
	
	private static final long serialVersionUID = -3522811498845481953L;

	private static final Logger logger = Logger.getLogger(SVGTextElementImpl.class.getName());
	
	private SVGAnimatedTransformList transform;
	
	private SVGAnimatedLengthList x;
	
	private SVGAnimatedLengthList y;
	
	private String text;
	/**
	 * @return - Text
	 */
	public String getText() { return this.text; }

	
	/**
	 * 
	 */
	public SVGTextElementImpl() {
		
		transform = new SVGAnimatedTransformListImpl();
		x = new SVGAnimatedLengthListImpl();
		y = new SVGAnimatedLengthListImpl();
	}
	
	
	public SVGAnimatedLengthList getX() {
		return x;
	}

	
	public SVGAnimatedLengthList getY() {
		return y;
	}

	
	public SVGAnimatedLengthList getDx() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGAnimatedLengthList getDy() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGAnimatedNumberList getRotate() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGAnimatedLength getTextLength() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGAnimatedEnumeration getLengthAdjust() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public long getNumberOfChars() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public float getComputedTextLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public float getSubStringLength(long charnum, long nchars) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public SVGPoint getStartPositionOfChar(long charnum) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGPoint getEndPositionOfChar(long charnum) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGRect getExtentOfChar(long charnum) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public float getRotationOfChar(long charnum) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public long getCharNumAtPosition(SVGPoint point) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public void selectSubString(long charnum, long nchars) {
		// TODO Auto-generated method stub

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
		return transform;
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
			SVGLength xLen = new SVGLengthImpl(elem.getAttribute("x"));
			x.getBaseVal().appendItem(xLen);
		}
//		‘y’
		if (elem.hasAttribute("y")) {
			SVGLength yLen = new SVGLengthImpl(elem.getAttribute("y"));
			y.getBaseVal().appendItem(yLen);
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
					this.description = new SVGDescElementImpl();
					((SVGDescElementImpl) this.description).parseXml(childElem);
					break;
				case "title":
					this.title = new SVGTitleElementImpl();
					((SVGTitleElementImpl) this.title).parseXml(childElem);
					break;
				case "metadata":
					break;
				default:
					throw new DOMException(DOMException.NOT_SUPPORTED_ERR, 
							"Tag <"+elem.getLocalName()+"> not allowed below <rect>!");
				}
			} else if (child.getNodeType() == Node.TEXT_NODE) {
			
				this.text = child.getTextContent();
			}
		}
	}

	/**
	 * @param g2d
	 */
	public void paint(Graphics2D g2d) {
		
		logger.info(String.format("Paint text: x=%f y=%f text='%s'", 
				this.x.getBaseVal().getItem(0).getValue(), 
				this.y.getBaseVal().getItem(0).getValue(), 
				this.getText()));
		
		Point2D.Float p1 = convertCoordinate(
			new Point2D.Float(
				this.x.getBaseVal().getItem(0).getValue(),
				this.y.getBaseVal().getItem(0).getValue()
				));
		
		// Text zeichnen
		g2d.drawString(this.text, p1.x, p1.y);
	}
	
	private Point2D.Float convertCoordinate(Point2D.Float p1) {
		
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
}
