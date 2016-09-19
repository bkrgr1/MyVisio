package de.bkroeger.myvisio.svg.elements;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.util.logging.Logger;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGAnimatedBoolean;
import org.w3c.dom.svg.SVGAnimatedTransformList;
import org.w3c.dom.svg.SVGElement;
import org.w3c.dom.svg.SVGGElement;
import org.w3c.dom.svg.SVGGraphicalElement;
import org.w3c.dom.svg.SVGMatrix;
import org.w3c.dom.svg.SVGRect;
import org.w3c.dom.svg.SVGStringList;
import org.w3c.dom.svg.SVGStructuralElement;
import org.w3c.dom.svg.SVGTextElement;
import org.w3c.dom.svg.SVGTransform;
import org.w3c.dom.svg.SVGTransformList;

import de.bkroeger.myvisio.shapes.IShape;
import de.bkroeger.myvisio.svg.SVGAnimatedTransformListImpl;
import de.bkroeger.myvisio.svg.SVGTransformImpl;
import de.bkroeger.myvisio.svg.SVGTransformListImpl;
import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * <p>
 * The ‘g’ element is a container element for grouping together related graphics elements.
 * </p><p>
 * Grouping constructs, when used in conjunction with the ‘desc’ and ‘title’ elements, 
 * provide information about document structure and semantics. Documents that are rich in structure 
 * may be rendered graphically, as speech, or as braille, and thus promote accessibility.
 * </p><p>
 * A group of elements, as well as individual objects, can be given a name using the ‘id’ attribute. 
 * Named groups are needed for several purposes such as animation and re-usable objects.
 * </p><p>
 * A ‘g’ element can contain other ‘g’ elements nested within it, to an arbitrary depth.
 * </p><p>
 * Any element that is not contained within a ‘g’ is treated (at least conceptually) 
 * as if it were in its own group.
 * </p>
 * @author bk
 */
public class SVGGElementImpl extends SVGStructuralElementImpl 
	implements SVGGElement, SVGStructuralElement, IShape {
	
	private static final long serialVersionUID = -1716471584844692821L;

	private static final Logger logger = Logger.getLogger(SVGGElementImpl.class.getName());
	
	private static final String SHAPE_IDENTIFIER = "shape";
	
	protected SVGMatrix transformToElement;
	
	protected SVGElement nearestViewportElement;
	
	protected SVGElement farthestViewportElement;
	
	protected SVGRect bBox;
	
	protected SVGMatrix ctm;
	
	protected SVGMatrix screenCTM;
	
	protected boolean isSelectedFlag = false;
	
	public boolean isSelected() { return isSelectedFlag; }
	
	public void setSelected(boolean value) { isSelectedFlag = value; }

	/**
	 * Default-Konstruktor
	 */
	public SVGGElementImpl() {
		super();
	}
	
	/**
	 * <p>
	 * Prüft, ob dieses G-Element ein Shape repräsentiert.
	 * </p><p>
	 * Dies ist gegeben, wenn das G-Element u.a. die Klasse "shape" enthält.
	 * </p>
	 * @return - wenn dieses G-Element die Classe "Shape" hat
	 */
	public boolean isShape() {
		
		if (this.getClassName() != null && this.getClassName().getAnimVal() != null) {
			String classes = this.getClassName().getAnimVal();
			if (classes != null && !classes.isEmpty()) {
				String[] parts = classes.split("\\s*,\\s*|\\s+");
				for (String part : parts) {
					if (part.equals(SHAPE_IDENTIFIER)) return true;
				}
			}
		}
		return false;
	}

	
	public SVGAnimatedTransformList getTransformList() {
		return this.transformList;
	}
	public void setTransformList(SVGAnimatedTransformList transform) {
		this.transformList = transform;
	}

	
	public SVGElement getNearestViewportElement() {
		return nearestViewportElement;
	}

	
	public SVGElement getFarthestViewportElement() {
		return farthestViewportElement;
	}

	
	public SVGRect getBBox() {
		return bBox;
	}

	
	public SVGMatrix getCTM() {
		return ctm;
	}

	
	public SVGMatrix getScreenCTM() {
		return screenCTM;
	}

	
	public SVGMatrix getTransformToElement(SVGElement element) {
		return transformToElement;
	}

	/**
	 * Liest Attribute und Struktur des SVG-Tag ein.
	 * 
	 * @param elem
	 * @throws TechnicalException 
	 */
	
	public void parseXml(Element elem) throws TechnicalException {
		
		logger.entering(SVGGElementImpl.class.getName(), "parseXml", elem);
		
		parseBaseAttributes(elem);
		parseStylableAttributes(elem);
		parseConditionalProcessingAttributes(elem);
		parseGraphicalEventAttributes(elem);
		parsePresentationAttributes(elem);
		
//		‘transform’
		if (elem.hasAttribute("transform")) {
			this.transformList = new SVGAnimatedTransformListImpl(
					new SVGTransformListImpl(elem.getAttribute("transform")));
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
				case "animateMotion":
				case "animateTransform":
				case "animate":
				case "set":
					parseAnimationElements(childElem);
					break;
				// Descriptive Elements:
				case "desc":
				case "title":
				case "metadata":
					parseDescriptiveElements(childElem);
					break;
				// Shape Elements:
				case "path":
				case "rect":
				case "circle":
				case "ellipse":
				case "line":
				case "polyline":
				case "polygon":
					parseShapeElements(childElem);
					break;
				// Structural Elements:
				case "defs":
				case "svg":
				case "g":
				case "box":
				case "symbol":
				case "use":
					parseStructuralElements(childElem);
					break;
				// Gradient Elements:
				case "linearGradient":
				case "radialGradient":
					parseGradientElements(childElem);
					break;
				// sonstige Elements:
				case "a":
					break;
				case "altGlyphDef":
					break;
				case "clipPath":
					break;
				case "color-profile":
					break;
				case "cursor":
					break;
				case "filter":
					break;
				case "font":
					break;
				case "font-face":
					break;
				case "foreignObject":
					break;
				case "image":
					break;
				case "marker":
					break;
				case "mask":
					break;
				case "pattern":
					break;
				case "script":
					break;
				case "style":
					break;
				case "switch":
					break;
				case "text":
					parseTextElements(childElem);
					break;
				case "view":
					break;
				default:
					throw new DOMException(DOMException.NOT_SUPPORTED_ERR, 
							"Tag <"+childElem.getLocalName()+"> not allowed below <g>!");
				}
			}
		}
	}
	
	/**
	 * Zeichnet das graphische Element oder die Struktur der graphischen Elemente.
	 * 
	 * @param g2d - Graphics-Device
	 */
	public void paint(Graphics2D g2d) {
		
		// alle untergeordneten Elemente zeichnen
		for (int i=0; i<this.getChildCount(); i++) {
			SVGElementImpl childNode = (SVGElementImpl) this.getChildAt(i);
			
			// ist das ein graphisches Element?
			if (childNode instanceof SVGGraphicalElement) {
				
				// das graphische Element zeichnen
				SVGGraphicalElementImpl graph = (SVGGraphicalElementImpl) childNode;
				graph.paint(g2d);
				
			// oder ist es ein Struktur-Element?
			} else if (childNode instanceof SVGStructuralElement) {
				
				// dann die Elemente der Struktur zeichnen
				SVGStructuralElement struct = (SVGStructuralElement) childNode;
				struct.paint(g2d);
				
			// oder ist es ein Text-Element?
			} else if (childNode instanceof SVGTextElementImpl) {
				
				// dann den Text zeichnen
				SVGTextElementImpl textElem = (SVGTextElementImpl) childNode;
				textElem.paint(g2d);
			}
		}
	}
	
	/**
	 * @param p1 - zu konvertierender Punkt
	 * @return - konvertierter Punkt
	 */
	public Point2D.Float convertCoordinate(Point2D.Float p1) {
		
		Point2D.Float p = (Point2D.Float)p1.clone();
		
		if (this.transformList != null && this.transformList.getAnimVal() != null) {
			SVGTransformList transformList = this.transformList.getAnimVal();
			for (long i=transformList.getNumberOfItems()-1; i>=0; i--) {
				SVGTransformImpl transformImpl = (SVGTransformImpl) transformList.getItem(i);
				logger.info("SVGTransform="+transformImpl.toString());
				Point2D.Float q = p;
				p = transformImpl.transform(p);
				logger.info(String.format("SVGGElementImpl::convertCoordinate From=%f/%f To=%f/%f",
						q.x, q.y, p.x, p.y));
			}
		}
		
//		SVGNodeImpl node = this.getNode();
		SVGElementImpl parentNode = (SVGElementImpl) this.getParent();
		if (parentNode != null) {
			SVGStructuralElement parent = (SVGStructuralElement) parentNode;
			p =  parent.convertCoordinate(p);
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

	
	public void store(Document doc, Element shapeElem) {
		// TODO Auto-generated method stub
		
	}

	
	public Shape getBoundingRectangle() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * <p>
	 * Erzeugt eine tiefe Kopie des G-Elementes.
	 * </p>
	 * @return - die Kopie des Elementes
	 */
	
	public Object clone() {
		
		SVGGElementImpl clone = new SVGGElementImpl();
		super.copy(clone);
		
		// transform-Attribute nicht kopieren, es wird am Ziel neu definiert
		// TODO: kann man das so pauschal sagen?
		
		for (int i=0; i<this.getChildCount(); i++) {
			SVGElementImpl userObject = (SVGElementImpl)this.getChildAt(i);
			SVGElementImpl clonedObject = (SVGElementImpl) userObject.clone();
			clone.add(clonedObject);
			clonedObject.setParent(clone);
		}
		return clone;
	}
	
	/**
	 * <p>
	 * Setzt die Position des Shapes.
	 * Hierzu wird zur Shape-Gruppe eine Translate-Transformation hinzugefügt
	 * bzw. ersetzt.
	 * </p>
	 * @param point - die Position im Canvas
	 */
		// IShape
	public void setOrigin(Point2D.Float point) {
		
		SVGTransformImpl translateTransform = new SVGTransformImpl();
		translateTransform.setTranslate(point.x, point.y);
		
		if (this.transformList == null) {
			SVGTransformListImpl baseList = new SVGTransformListImpl();
			this.transformList = new SVGAnimatedTransformListImpl(baseList);
		}
		((SVGTransformListImpl)this.transformList.getBaseVal())
			.replaceTransform(SVGTransform.SVG_TRANSFORM_TRANSLATE, translateTransform);
	}
	
	/**
	 * Schreibt die SVG-Struktur in das XML-Dokument.
	 * 
	 * @param doc - das XML-Dokument
	 * @param parent - das Parent-Element
	 */
	
	public void convertToXml(Document doc, Element parent) {
		
		Element domElem = doc.createElement("g");
		if (this.id != null && !this.id.isEmpty())
			domElem.setAttribute("id", this.id);
		if (this.getClassName() != null)
			domElem.setAttribute("class", this.getClassName().toXmlString());
		if (this.transformList != null && this.transformList.getAnimVal().getNumberOfItems() > 0) 
			domElem.setAttribute("transform", this.transformList.toXmlString());
		parent.appendChild(domElem);
		
		for (int i=0; i<this.getChildCount(); i++) {
			SVGElementImpl child = (SVGElementImpl) this.getChildAt(i);
			child.convertToXml(doc, domElem);
		}
	}
	
	@Override
	public void negotiateViewport(SVGElement viewport) {
		this.viewportElement = viewport;
	}
}
