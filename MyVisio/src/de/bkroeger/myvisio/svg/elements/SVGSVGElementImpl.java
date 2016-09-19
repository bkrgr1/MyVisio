package de.bkroeger.myvisio.svg.elements;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.events.Event;
import org.w3c.dom.stylesheets.StyleSheetList;
import org.w3c.dom.svg.SVGAngle;
import org.w3c.dom.svg.SVGAnimatedBoolean;
import org.w3c.dom.svg.SVGAnimatedLength;
import org.w3c.dom.svg.SVGAnimatedPreserveAspectRatio;
import org.w3c.dom.svg.SVGAnimatedRect;
import org.w3c.dom.svg.SVGAnimatedString;
import org.w3c.dom.svg.SVGAnimatedTransformList;
import org.w3c.dom.svg.SVGElement;
import org.w3c.dom.svg.SVGGElement;
import org.w3c.dom.svg.SVGLength;
import org.w3c.dom.svg.SVGMatrix;
import org.w3c.dom.svg.SVGNumber;
import org.w3c.dom.svg.SVGPoint;
import org.w3c.dom.svg.SVGRect;
import org.w3c.dom.svg.SVGSVGElement;
import org.w3c.dom.svg.SVGStringList;
import org.w3c.dom.svg.SVGTransform;
import org.w3c.dom.svg.SVGTransformList;
import org.w3c.dom.svg.SVGViewSpec;
import org.w3c.dom.views.DocumentView;

import de.bkroeger.myvisio.shapes.IShape;
import de.bkroeger.myvisio.shapes.TemplateSet;
import de.bkroeger.myvisio.svg.SVGAnimatedLengthImpl;
import de.bkroeger.myvisio.svg.SVGAnimatedStringImpl;
import de.bkroeger.myvisio.svg.SVGAnimatedTransformListImpl;
import de.bkroeger.myvisio.svg.SVGLengthImpl;
import de.bkroeger.myvisio.svg.SVGTransformImpl;
import de.bkroeger.myvisio.svg.SVGTransformListImpl;
import de.bkroeger.myvisio.svg.attributes.SVGColorAttributeImpl;
import de.bkroeger.myvisio.utility.TechnicalException;
import de.bkroeger.myvisio.utility.Traversator;

/**
 * <p>
 * The XML document sub-tree which starts with an ‘svg’ element. An SVG document
 * fragment can consist of a stand-alone SVG document, or a fragment of a parent XML document enclosed by an
 * ‘svg’ element. 
 * </p><p>
 * Authors should always provide a ‘title’ child element to the outermost svg element 
 * within a stand-alone SVG document. The ‘title’ child element to an ‘svg’ element 
 * serves the purposes of identifying the content of the given SVG document fragment. 
 * Since users often consult documents out of context, authors should provide context-rich
 * titles. Thus, instead of a title such as "Introduction", which doesn't provide much 
 * contextual background, authors should supply a title such as "Introduction to 
 * Medieval Bee-Keeping" instead. For reasons of accessibility, user agents should always 
 * make the content of the ‘title’ child element to the outermost svg element available to users.
 * The mechanism for doing so depends on the user agent (e.g., as a caption, spoken).
 * </p>
 * @author bk
 */
public class SVGSVGElementImpl extends SVGStructuralElementImpl implements SVGSVGElement {
	
	private static final String SHAPE_CLASS = "shape";

	private static final long serialVersionUID = 8424958969319824879L;

	private static final Logger logger = Logger.getLogger(SVGSVGElementImpl.class.getName());

	private static final String XML_LANG_URI = "http://www.w3.org/XML/1998/namespace";
	
	protected SVGAnimatedLength x;
	
	protected SVGAnimatedLength y;
	
	protected SVGAnimatedLength width;
	
	protected SVGAnimatedLength height;
	
	private SVGRect viewport;
	
	private SVGColorAttributeImpl currentFillColor;
	
	private SVGAnimatedTransformList savedTransformList;
	
	/**
	 * @return - Farbe
	 */
	public  SVGColorAttributeImpl getCurrentFillColor() { return currentFillColor; }
	/**
	 * @param value
	 */
	public  void setCurrentFillColor(SVGColorAttributeImpl value) { currentFillColor = value; }
	
	private SVGColorAttributeImpl currentStrokeColor;
	/**
	 * @return - Farbe
	 */
	public  SVGColorAttributeImpl getCurrentStrokeColor() { return currentStrokeColor; }
	/**
	 * @param value
	 */
	public  void setCurrentStrokeColor(SVGColorAttributeImpl value) { currentStrokeColor = value; }
	
	private List<IShape> shapeList = new ArrayList<IShape>();

	/**
	 * Konstruktor.
	 */
	public SVGSVGElementImpl() {
		super();
	}
	
	/**
	 * Konstruktor mit HTML-DOM-Element.
	 * @param elem
	 */
	public SVGSVGElementImpl(Element elem) {
		super(elem);
	}
	
	/**
	 * <p>
	 * Liefert die Anzahl der {@link SVGGElement | Group-Elemente} mit der Klasse "shape".
	 * </p>
	 * @return - Anzahl Shapes
	 */
	public int getShapeCount() {
		int cnt = 0;
		for (int i=0; i<this.getChildCount(); i++) {
			SVGElementImpl childNode = (SVGElementImpl) this.getChildAt(i);
			
			// ist es ein Group-Element
			if (childNode instanceof SVGGElement) {
				SVGGElement gElem = (SVGGElement)childNode;
				
				if (gElem.getClassName() != null) {
					// die Class-Werte durchsuchen
					SVGAnimatedStringImpl classList = (SVGAnimatedStringImpl) gElem.getClassName();
					if (classList.containsString(SHAPE_CLASS)) cnt++;
				}
			}
		}
		return cnt;
	}
	
	/**
	 * Passt die Grösse des SVG-Elementes an die Grösse
	 * des Viewports an. Hieraus ergibt sich ein horizontaler und vertikaler 
	 * Umrechnungsfaktor für dimensionslose Units.
	 * 
	 * @param viewport - die View-Spezifikation des Template-Bereiches
	 */
	public void negotiateViewport(SVGRect viewport) {
		
		logger.entering(TemplateSet.class.getName(), "negotiateViewport", 
			new Object[] { viewport });
		
		if (savedTransformList == null) {
			savedTransformList = (SVGAnimatedTransformList) ((SVGAnimatedTransformListImpl) this.transformList).clone();
		} else {
			this.transformList = (SVGAnimatedTransformList) ((SVGAnimatedTransformListImpl)savedTransformList).clone();
		}
		
		this.viewport = viewport;
		
		// Beginnt der Viewport nicht auf den Koordinaten (0,0)?
		if (this.viewport.getX() != 0.0f || this.viewport.getY() != 0.0f) {
			
			// Translate-Transformation erstellen
			SVGTransform translateTransform = new SVGTransformImpl(SVGTransform.SVG_TRANSFORM_TRANSLATE);
			translateTransform.setTranslate(
				this.viewport.getX(), this.viewport.getY());
			this.transformList.getAnimVal().insertItemBefore(translateTransform, 0);
		}
		
		// Scale-Transformation berechnen:
		// das Verhältnis der verfübaren Breite zur Breite des SVG-Elementes
		// und das entsprechende Verhältnis der Höhe.
		// Wenn ein Wert im Viewport mit -1 angegeben ist, wird der Wert aus
		// dem SVG-Element unverändert übernommen.
		SVGTransform scaleTransform = new SVGTransformImpl(SVGTransform.SVG_TRANSFORM_SCALE);
		scaleTransform.setScale(
			this.viewport.getWidth() == -1.0f ?
				1.0f :
				this.viewport.getWidth() / this.width.getAnimVal().getValue(),
			this.viewport.getHeight() == -1.0f ?
				1.0f : 
				this.viewport.getHeight() / this.width.getAnimVal().getValue());
		this.transformList.getAnimVal().insertItemBefore(scaleTransform, 0);
	}

	/**
	 * <p>
	 * Zeichnet rekursiv die Elemente der SVG-Struktur.
	 * </p><p>
	 * Zur Glättung der Kanten wird Antialiasing aktiviert.
	 * </p>
	 * @param g2d - das Graphics-Objekt
	 */
	public void paint(Graphics2D g2d) {
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		
		for (int i=0; i<this.getChildCount(); i++) {
			
			if (this.getChildAt(i) instanceof SVGStructuralElementImpl) {
				SVGStructuralElementImpl childNode = (SVGStructuralElementImpl) this.getChildAt(i);
				childNode.paint(g2d);
				
			} else if (this.getChildAt(i) instanceof SVGTextElementImpl) {
				SVGTextElementImpl textNode = (SVGTextElementImpl) this.getChildAt(i);
				textNode.paint(g2d);
			}
		}
	}

	/**
	 * Liest Attribute und Struktur des SVG-Tag ein.
	 * 
	 * @param elem
	 * @throws TechnicalException 
	 */
	public void parseXml(Element elem) throws TechnicalException {
		
		logger.entering(SVGSVGElementImpl.class.getName(), "parseXml", elem);
		
		// Attribute auswerten
		parseAttributes(elem);
				
		// die Substruktur des DOM-Elements wird hier analysiert
		parseChildNodes(elem);
	}
	
	private void parseChildNodes(Element elem) throws TechnicalException {
		
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
					break;
					
				// untergeordnetes SVG-Element
				case "svg":
					parseSVGNode(childElem);
					break;
					
				// untergeordnetes G-Element
				// Gruppen mit class="shape" gelten als Shapes
				case "g":
//				case "shape":
					parseGroupNode(childElem);
					break;
					
				case "symbol":
					break;
				case "use":
					break;
					
				// Gradient Elements:
				case "linearGradient":
					break;
				case "radialGradient":
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
							"Tag <"+childElem.getLocalName()+"> not allowed below <svg>!");
				}
			}
		}
	}
	
	private void parseAttributes(Element elem) throws TechnicalException {
		
		// Conditional Processing Attributes auswerten
		parseConditionalProcessingAttributes(elem);
		// Graphical Event Attributes auswerten
		parseGraphicalEventAttributes(elem);
		// Presentation Attributes auswerten
		parsePresentationAttributes(elem);
		
		// horizontale Position
		if (elem.hasAttribute("x")) {
			this.x = new SVGAnimatedLengthImpl(
				new SVGLengthImpl(Float.parseFloat(elem.getAttribute("x"))));
		} else {
			this.x = new SVGAnimatedLengthImpl(
				new SVGLengthImpl(0.0f));
		}
		
		// vertikale Position
		if (elem.hasAttribute("y")) {
			this.y = new SVGAnimatedLengthImpl(
				new SVGLengthImpl(Float.parseFloat(elem.getAttribute("y"))));
		} else {
			this.y = new SVGAnimatedLengthImpl(
				new SVGLengthImpl(0.0f));
		}
		
		// width
		if (elem.hasAttribute("width"))
			this.width = new SVGAnimatedLengthImpl(
				new SVGLengthImpl(Float.parseFloat(elem.getAttribute("width"))));
		
		// height
		if (elem.hasAttribute("height"))
			this.height = new SVGAnimatedLengthImpl(
				new SVGLengthImpl(Float.parseFloat(elem.getAttribute("height"))));
	}

	
	public SVGStringList getRequiredFeatures() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGStringList getRequiredExtensions() {
		return this.requiredExtensions;
	}

	
	public SVGStringList getSystemLanguage() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public boolean hasExtension(String extension) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public String getXMLlang() {
		return this.xmlLang;
	}

	
	public void setXMLlang(String XMLlang) {
		this.xmlLang = XMLlang;
	}

	
	public String getXMLspace() {
		return this.xmlSpace;
	}

	
	public void setXMLspace(String XMLspace) {
		if (domElement.hasAttributeNS(XML_LANG_URI, "space")) {
			domElement.setAttributeNS(XML_LANG_URI, "space", XMLspace);
		}
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

	
	public SVGAnimatedRect getViewBox() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGAnimatedPreserveAspectRatio getPreserveAspectRatio() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public short getZoomAndPan() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public void setZoomAndPan(short zoomAndPan) {
		// TODO Auto-generated method stub
		
	}

	
	public Event createEvent(String eventType) throws DOMException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public CSSStyleDeclaration getComputedStyle(Element elt, String pseudoElt) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public DocumentView getDocument() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public CSSStyleDeclaration getOverrideStyle(Element elt, String pseudoElt) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public StyleSheetList getStyleSheets() {
		// TODO Auto-generated method stub
		return null;
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

	
	public String getContentScriptType() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setContentScriptType(String contentScriptType) {
		// TODO Auto-generated method stub
		
	}

	
	public String getContentStyleType() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setContentStyleType(String contentStyleType) {
		// TODO Auto-generated method stub
		
	}

	
	public SVGRect getViewport() {
		return viewport;
	}

	
	public float getPixelUnitToMillimeterX() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public float getPixelUnitToMillimeterY() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public float getScreenPixelToMillimeterX() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public float getScreenPixelToMillimeterY() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public boolean getUseCurrentView() {
		// TODO Auto-generated method stub
		return false;
	}

	
	public SVGViewSpec getCurrentView() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public float getCurrentScale() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public void setCurrentScale(float currentScale) {
		// TODO Auto-generated method stub
		
	}

	
	public SVGPoint getCurrentTranslate() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public long suspendRedraw(long maxWaitMilliseconds) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public void unsuspendRedraw(long suspendHandleID) {
		// TODO Auto-generated method stub
		
	}

	
	public void unsuspendRedrawAll() {
		// TODO Auto-generated method stub
		
	}

	
	public void forceRedraw() {
		// TODO Auto-generated method stub
		
	}

	
	public void pauseAnimations() {
		// TODO Auto-generated method stub
		
	}

	
	public void unpauseAnimations() {
		// TODO Auto-generated method stub
		
	}

	
	public boolean animationsPaused() {
		// TODO Auto-generated method stub
		return false;
	}

	
	public float getCurrentTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public void setCurrentTime(float seconds) {
		// TODO Auto-generated method stub
		
	}

	
	public NodeList getIntersectionList(SVGRect rect,
			SVGElement referenceElement) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public NodeList getEnclosureList(SVGRect rect, SVGElement referenceElement) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public boolean checkIntersection(SVGElement element, SVGRect rect) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean checkEnclosure(SVGElement element, SVGRect rect) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public void deselectAll() {
		// TODO Auto-generated method stub
		
	}

	
	public SVGNumber createSVGNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGLength createSVGLength() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGAngle createSVGAngle() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGPoint createSVGPoint() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGMatrix createSVGMatrix() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGRect createSVGRect() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGTransform createSVGTransform() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGTransform createSVGTransformFromMatrix(SVGMatrix matrix) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Element getElementById(String elementId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Point2D.Float convertCoordinate(Point2D.Float p1) {
		
		Point2D.Float p = p1;
		if (this.transformList != null && this.transformList.getAnimVal() != null) {
			SVGTransformList transformList = this.transformList.getAnimVal();
			for (long i=transformList.getNumberOfItems()-1; i>=0; i--) {
				SVGTransformImpl transformImpl = (SVGTransformImpl) transformList.getItem(i);
				Point2D.Float q = p1;
				p = transformImpl.transform(p);
				logger.info(String.format("SVGSVGElementImpl::convertCoordinate From=%f/%f To=%f/%f",
						q.x, q.y, p.x, p.y));
			}
		}
		
		if (this.getParent() instanceof SVGStructuralElementImpl) {
			SVGStructuralElementImpl parent = (SVGStructuralElementImpl) this.getParent();
			if (parent != null) {
				p = parent.convertCoordinate(p);
			}
		} 
		return p;
	}
	
	/**
	 * <p>
	 * Liefert eine Liste der Shapes in der SVG-Substruktur.
	 * </p>
	 * @return - Liste von IShape-Objekten
	 */
	public List<IShape> getShapeList() {
		
		List<IShape> shapes = new ArrayList<IShape>();
		traverseNode(this, new ShapesTraversator(shapes));
		return shapes;
	}
	
	/**
	 * <p>
	 * Traversiert die Children dieser Node.
	 * </p>
	 * @param node
	 * @param shapes
	 */
	private void traverseNode(SVGElementImpl node, Traversator traversator) {
		
		traversator.doPerNode(node);
		
		for (int i=0; i<node.getChildCount(); i++) {
			SVGElementImpl child = (SVGElementImpl) node.getChildAt(i);
			traverseNode(child, traversator);
		}
	}
	
	/**
	 * Prüft, ob es in der untergeordneten Baum-Struktur ein Shape gibt, das den Punkt enthält.
	 * @param p
	 * @return - ein Shape
	 */
	public IShape containsPoint(Point.Float p) {
		
		PointTraversator ptraversator = new PointTraversator(p);
		traverseNode(this, ptraversator);
		return ptraversator.getPointedShape();
	}
	
	private class PointTraversator implements Traversator {
		
		private Point.Float p;
		
		private IShape pointedShape = null;
		public IShape getPointedShape() { return pointedShape; }
		
		public PointTraversator(Point.Float p) {
			this.p = p;
		}

		
		public void doPerNode(SVGElementImpl node) {
			
			// ist der angegebene Punkt im Shape enthalten
			if (node instanceof SVGShapeElementImpl) {
				SVGShapeElementImpl shapeElem = (SVGShapeElementImpl) node;
				if (shapeElem.containsPoint(p)) {
					
					// das übergeordnete G-Element zur Verfügung stellen
					while (node != null) {
						if (node instanceof SVGGElementImpl) {
							SVGGElementImpl gElem = (SVGGElementImpl)node;
							if (gElem.getClassName() != null) {
								SVGAnimatedStringImpl stringList = (SVGAnimatedStringImpl) gElem.getClassName();
								if (stringList.containsString(SHAPE_CLASS))
									pointedShape = (IShape)node;
								}
							}
						if (node.getParent() instanceof TemplateSet) break;
						node = (SVGElementImpl) node.getParent();
					}
				}
			}
		}
	}
	
	
	
	/**
	 * Lokale Traversator-Implementierung.
	 * @author bk
	 */
	private class ShapesTraversator implements Traversator {
		
		List<IShape> shapes;
		
		public ShapesTraversator(List<IShape> list) {
			this.shapes = list;
		}

		
		public void doPerNode(SVGElementImpl node) {
			if (node instanceof SVGGElementImpl) {
				SVGGElementImpl gElem = (SVGGElementImpl)node;
				if (gElem.isShape()) {
					shapes.add((IShape)node);
				}
			}
		}
	}

	/**
	 * Schreibt die SVG-Struktur in das XML-Dokument.
	 * 
	 * @param doc - das XML-Dokument
	 * @param parent - das Parent-Element
	 */
	
	public void convertToXml(Document doc, Element parent) {
		
		Element domElem = doc.createElement("svg");
		if (this.id != null && ! this.id.isEmpty())
			domElem.setAttribute("id", this.id);
		if (this.getClassName() != null)
			domElem.setAttribute("class", this.getClassName().toXmlString());
		if (this.transformList != null && this.transformList.getAnimVal().getNumberOfItems() > 0) 
			domElem.setAttribute("transform", this.transformList.toXmlString());
		if (this.x != null)
			domElem.setAttribute("x", this.x.toXmlString());
		if (this.y != null)
			domElem.setAttribute("y", this.y.toXmlString());
		if (this.width != null)
			domElem.setAttribute("width", this.width.toXmlString());
		if (this.height != null)
			domElem.setAttribute("height", this.height.toXmlString());
		parent.appendChild(domElem);
		
		for (int i=0; i<this.getChildCount(); i++) {
			SVGElementImpl child = (SVGElementImpl) this.getChildAt(i);
			child.convertToXml(doc, domElem);
		}
	}
	
	/**
	 * Fügt ein Shape zur SVG-Struktur hinzu.
	 * 
	 * @param shape
	 * @throws TechnicalException 
	 */
	public void addShape(IShape shape) throws TechnicalException {

		int indent = 0;
		((SVGElementImpl)shape).verifySvgHierarchy(logger, indent);
		
		if (shape instanceof SVGGElementImpl) {
			
			SVGGElementImpl gElem = (SVGGElementImpl) shape;
			this.add(gElem);	
			gElem.setParent(this);
			
			shapeList.add(shape);
		
		} else {
			throw new TechnicalException("Unable to add a shape of type '"+shape.getClass().getName()+"'.");
		}
	}
	
	/**
	 * Löscht ein Shape aus der SVG-Struktur.
	 * 
	 * @param shape
	 */
	public void removeShape(IShape shape) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Setzt den Skalierungsfactor in das SVG-Element.
	 * 
	 * @param scaleFactor
	 */
	public void setScaleFactor(float scaleFactor) {
		
		SVGTransformImpl scaleTransform = new SVGTransformImpl();
		scaleTransform.setScale(scaleFactor, scaleFactor);
		
		if (this.transformList == null) {
			SVGTransformListImpl baseList = new SVGTransformListImpl();
			this.transformList = new SVGAnimatedTransformListImpl(baseList);
		}
		((SVGTransformListImpl)this.transformList.getBaseVal())
			.replaceTransform(SVGTransform.SVG_TRANSFORM_SCALE, scaleTransform);
	}
}
