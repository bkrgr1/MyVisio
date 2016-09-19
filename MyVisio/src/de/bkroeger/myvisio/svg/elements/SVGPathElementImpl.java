package de.bkroeger.myvisio.svg.elements;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.logging.Logger;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.svg.SVGAnimatedBoolean;
import org.w3c.dom.svg.SVGAnimatedLength;
import org.w3c.dom.svg.SVGAnimatedNumber;
import org.w3c.dom.svg.SVGAnimatedPathData;
import org.w3c.dom.svg.SVGAnimatedString;
import org.w3c.dom.svg.SVGAnimatedTransformList;
import org.w3c.dom.svg.SVGElement;
import org.w3c.dom.svg.SVGMatrix;
import org.w3c.dom.svg.SVGPathElement;
import org.w3c.dom.svg.SVGPathSeg;
import org.w3c.dom.svg.SVGPathSegArcAbs;
import org.w3c.dom.svg.SVGPathSegArcRel;
import org.w3c.dom.svg.SVGPathSegClosePath;
import org.w3c.dom.svg.SVGPathSegCurvetoCubicAbs;
import org.w3c.dom.svg.SVGPathSegCurvetoCubicRel;
import org.w3c.dom.svg.SVGPathSegCurvetoCubicSmoothAbs;
import org.w3c.dom.svg.SVGPathSegCurvetoCubicSmoothRel;
import org.w3c.dom.svg.SVGPathSegCurvetoQuadraticAbs;
import org.w3c.dom.svg.SVGPathSegCurvetoQuadraticRel;
import org.w3c.dom.svg.SVGPathSegCurvetoQuadraticSmoothAbs;
import org.w3c.dom.svg.SVGPathSegCurvetoQuadraticSmoothRel;
import org.w3c.dom.svg.SVGPathSegLinetoAbs;
import org.w3c.dom.svg.SVGPathSegLinetoHorizontalAbs;
import org.w3c.dom.svg.SVGPathSegLinetoHorizontalRel;
import org.w3c.dom.svg.SVGPathSegLinetoRel;
import org.w3c.dom.svg.SVGPathSegLinetoVerticalAbs;
import org.w3c.dom.svg.SVGPathSegLinetoVerticalRel;
import org.w3c.dom.svg.SVGPathSegList;
import org.w3c.dom.svg.SVGPathSegMovetoAbs;
import org.w3c.dom.svg.SVGPathSegMovetoRel;
import org.w3c.dom.svg.SVGPoint;
import org.w3c.dom.svg.SVGRect;
import org.w3c.dom.svg.SVGSVGElement;
import org.w3c.dom.svg.SVGShapeElement;
import org.w3c.dom.svg.SVGStringList;
import org.w3c.dom.svg.SVGStructuralElement;
import org.w3c.dom.svg.SVGTransformList;

import de.bkroeger.myvisio.svg.SVGAnimatedLengthImpl;
import de.bkroeger.myvisio.svg.SVGAnimatedTransformListImpl;
import de.bkroeger.myvisio.svg.SVGLengthImpl;
import de.bkroeger.myvisio.svg.SVGTransformImpl;
import de.bkroeger.myvisio.svg.SVGTransformListImpl;
import de.bkroeger.myvisio.svg.attributes.SVGPathSegArcAbsImpl;
import de.bkroeger.myvisio.svg.attributes.SVGPathSegArcRelImpl;
import de.bkroeger.myvisio.svg.attributes.SVGPathSegClosePathImpl;
import de.bkroeger.myvisio.svg.attributes.SVGPathSegCurvetoCubicAbsImpl;
import de.bkroeger.myvisio.svg.attributes.SVGPathSegCurvetoCubicRelImpl;
import de.bkroeger.myvisio.svg.attributes.SVGPathSegCurvetoCubicSmoothAbsImpl;
import de.bkroeger.myvisio.svg.attributes.SVGPathSegCurvetoCubicSmoothRelImpl;
import de.bkroeger.myvisio.svg.attributes.SVGPathSegCurvetoQuadraticAbsImpl;
import de.bkroeger.myvisio.svg.attributes.SVGPathSegCurvetoQuadraticRelImpl;
import de.bkroeger.myvisio.svg.attributes.SVGPathSegCurvetoQuadraticSmoothAbsImpl;
import de.bkroeger.myvisio.svg.attributes.SVGPathSegCurvetoQuadraticSmoothRelImpl;
import de.bkroeger.myvisio.svg.attributes.SVGPathSegLinetoAbsImpl;
import de.bkroeger.myvisio.svg.attributes.SVGPathSegLinetoHorizontalAbsImpl;
import de.bkroeger.myvisio.svg.attributes.SVGPathSegLinetoHorizontalRelImpl;
import de.bkroeger.myvisio.svg.attributes.SVGPathSegLinetoRelImpl;
import de.bkroeger.myvisio.svg.attributes.SVGPathSegLinetoVerticalAbsImpl;
import de.bkroeger.myvisio.svg.attributes.SVGPathSegLinetoVerticalRelImpl;
import de.bkroeger.myvisio.svg.attributes.SVGPathSegMovetoAbsImpl;
import de.bkroeger.myvisio.svg.attributes.SVGPathSegMovetoRelImpl;
import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * <p>
 * Paths represent the outline of a shape which can be filled, stroked, used as a clipping path, 
 * or any combination of the three. (See Filling, Stroking and Paint Servers and Clipping, 
 * Masking and Compositing.)
 * </p><p>
 * A path is described using the concept of a current point. In an analogy with drawing on paper, 
 * the current point can be thought of as the location of the pen. The position of the pen can be 
 * changed, and the outline of a shape (open or closed) can be traced by dragging the pen 
 * in either straight lines or curves.
 * </p><p>
 * Paths represent the geometry of the outline of an object, defined in terms of moveto 
 * (set a new current point), lineto (draw a straight line), curveto (draw a curve using a 
 * cubic Bézier), arc (elliptical or circular arc) and closepath (close the current shape 
 * by drawing a line to the last moveto) elements. Compound paths (i.e., a path with multiple
 * subpaths) are possible to allow effects such as "donut holes" in objects.
 * </p>
 * @author bk
 */
public class SVGPathElementImpl extends SVGBasicShapeElementImpl 
implements SVGPathElement, SVGShapeElement {
	
	private static final long serialVersionUID = 4645461952304250169L;

	private static final Logger logger = Logger.getLogger(SVGPathElementImpl.class.getName());
	
	private SVGAnimatedTransformList transform;
	
	@SuppressWarnings("unused")
	private SVGAnimatedLength x;
	
	@SuppressWarnings("unused")
	private SVGAnimatedLength y;
	
	private SVGAnimatedLength width;
	
	private SVGAnimatedLength height;
	
	private SVGAnimatedPathData pathData = new SVGAnimatedPathDataImpl();
	
	/**
	 * Default-Konstruktor
	 */
	public SVGPathElementImpl() {
		super();
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

	
	public SVGSVGElement getOwnerSVGElement() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGElement getViewportElement() {
		// TODO Auto-generated method stub
		return null;
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

	
	public String getXMLlang() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setXMLlang(String XMLlang) {
		// TODO Auto-generated method stub

	}

	
	public String getXMLspace() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setXMLspace(String XMLspace) {
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

	
	public SVGPathSegList getPathSegList() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGPathSegList getNormalizedPathSegList() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGPathSegList getAnimatedPathSegList() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGPathSegList getAnimatedNormalizedPathSegList() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGAnimatedNumber getPathLength() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public float getTotalLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public SVGPoint getPointAtLength(float distance) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public long getPathSegAtLength(float distance) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public SVGPathSegClosePath createSVGPathSegClosePath() {

		SVGPathSegClosePath pathSeg = new SVGPathSegClosePathImpl();
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	
	public SVGPathSegMovetoAbs createSVGPathSegMovetoAbs(float x, float y) {

		SVGPathSegMovetoAbs pathSeg = new SVGPathSegMovetoAbsImpl(x, y);
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	
	public SVGPathSegMovetoRel createSVGPathSegMovetoRel(float x, float y) {

		SVGPathSegMovetoRel pathSeg = new SVGPathSegMovetoRelImpl(x, y);
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	
	public SVGPathSegLinetoAbs createSVGPathSegLinetoAbs(float x, float y) {

		SVGPathSegLinetoAbs pathSeg = new SVGPathSegLinetoAbsImpl(x, y);
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	
	public SVGPathSegLinetoRel createSVGPathSegLinetoRel(float x, float y) {

		SVGPathSegLinetoRel pathSeg = new SVGPathSegLinetoRelImpl(x, y);
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	
	public SVGPathSegCurvetoCubicAbs createSVGPathSegCurvetoCubicAbs(float x,
			float y, float x1, float y1, float x2, float y2) {

		SVGPathSegCurvetoCubicAbs pathSeg = new SVGPathSegCurvetoCubicAbsImpl(x, y,
				x1, y1, x2, y2);
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	
	public SVGPathSegCurvetoCubicRel createSVGPathSegCurvetoCubicRel(float x,
			float y, float x1, float y1, float x2, float y2) {

		SVGPathSegCurvetoCubicRel pathSeg = new SVGPathSegCurvetoCubicRelImpl(x, y,
				x1, y1, x2, y2);
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	
	public SVGPathSegCurvetoQuadraticAbs createSVGPathSegCurvetoQuadraticAbs(
			float x, float y, float x1, float y1) {

		SVGPathSegCurvetoQuadraticAbs pathSeg = new SVGPathSegCurvetoQuadraticAbsImpl(x, y,
				x1, y1);
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	
	public SVGPathSegCurvetoQuadraticRel createSVGPathSegCurvetoQuadraticRel(
			float x, float y, float x1, float y1) {

		SVGPathSegCurvetoQuadraticRel pathSeg = new SVGPathSegCurvetoQuadraticRelImpl(x, y,
				x1, y1);
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	
	public SVGPathSegArcAbs createSVGPathSegArcAbs(float x, float y, float r1,
			float r2, float angle, boolean largeArcFlag, boolean sweepFlag) {

		SVGPathSegArcAbs pathSeg = new SVGPathSegArcAbsImpl(x, y,
				r1, r2, angle, largeArcFlag, sweepFlag);
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	
	public SVGPathSegArcRel createSVGPathSegArcRel(float x, float y, float r1,
			float r2, float angle, boolean largeArcFlag, boolean sweepFlag) {

		SVGPathSegArcRel pathSeg = new SVGPathSegArcRelImpl(x, y,
				r1, r2, angle, largeArcFlag, sweepFlag);
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	
	public SVGPathSegLinetoHorizontalAbs createSVGPathSegLinetoHorizontalAbs(
			float x) {

		SVGPathSegLinetoHorizontalAbs pathSeg = new SVGPathSegLinetoHorizontalAbsImpl(x);
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	
	public SVGPathSegLinetoHorizontalRel createSVGPathSegLinetoHorizontalRel(
			float x) {

		SVGPathSegLinetoHorizontalRel pathSeg = new SVGPathSegLinetoHorizontalRelImpl(x);
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	
	public SVGPathSegLinetoVerticalAbs createSVGPathSegLinetoVerticalAbs(float y) {

		SVGPathSegLinetoVerticalAbs pathSeg = new SVGPathSegLinetoVerticalAbsImpl(y);
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	
	public SVGPathSegLinetoVerticalRel createSVGPathSegLinetoVerticalRel(float y) {

		SVGPathSegLinetoVerticalRel pathSeg = new SVGPathSegLinetoVerticalRelImpl(y);
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	
	public SVGPathSegCurvetoCubicSmoothAbs createSVGPathSegCurvetoCubicSmoothAbs(
			float x, float y, float x2, float y2) {

		SVGPathSegCurvetoCubicSmoothAbs pathSeg = new SVGPathSegCurvetoCubicSmoothAbsImpl(x, y,
				x2, y2);
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	
	public SVGPathSegCurvetoCubicSmoothRel createSVGPathSegCurvetoCubicSmoothRel(
			float x, float y, float x2, float y2) {

		SVGPathSegCurvetoCubicSmoothRel pathSeg = new SVGPathSegCurvetoCubicSmoothRelImpl(x, y,
				x2, y2);
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	
	public SVGPathSegCurvetoQuadraticSmoothAbs createSVGPathSegCurvetoQuadraticSmoothAbs(
			float x, float y) {

		SVGPathSegCurvetoQuadraticSmoothAbs pathSeg = new SVGPathSegCurvetoQuadraticSmoothAbsImpl(x, y);
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
	}

	
	public SVGPathSegCurvetoQuadraticSmoothRel createSVGPathSegCurvetoQuadraticSmoothRel(
			float x, float y) {

		SVGPathSegCurvetoQuadraticSmoothRel pathSeg = new SVGPathSegCurvetoQuadraticSmoothRelImpl(x, y);
		pathData.getPathSegList().appendItem(pathSeg);
		return pathSeg;
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
		
//		‘d’ - die Path-Segmente
		if (elem.hasAttribute("d")) {
			
			String path = elem.getAttribute("d");
			// Pfad zerlegen
			String[] parts = path.split("\\s+");
			
			for (int i=0; i<parts.length; i++) {
				
				i = interpretDValue(parts, i);
			}
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


	private int interpretDValue(String[] parts, int i) {
		
		String part = parts[i];
		float x, y;
		float x1, y1;
		float x2, y2;
		float angle;
		boolean f1;
		boolean f2;
		switch (part) {
		case "A":
			x = convertToFloat(parts[i+1]);
			y = convertToFloat(parts[i+2]);
			x1 = convertToFloat(parts[i+3]);
			y1 = convertToFloat(parts[i+4]);
			angle = convertToFloat(parts[i+5]);
			f1 = convertToBoolean(parts[i+6]);
			f2 = convertToBoolean(parts[i+7]);
			createSVGPathSegArcAbs(x, y, x1, y1, angle, f1, f2);
			i += 7;
			break;
		case "a":
			x = convertToFloat(parts[i+1]);
			y = convertToFloat(parts[i+2]);
			x1 = convertToFloat(parts[i+3]);
			y1 = convertToFloat(parts[i+4]);
			angle = convertToFloat(parts[i+5]);
			f1 = convertToBoolean(parts[i+6]);
			f2 = convertToBoolean(parts[i+7]);
			createSVGPathSegArcRel(x, y, x1, y1, angle, f1, f2);
			i += 7;
			break;
		case "C":
			x = convertToFloat(parts[i+1]);
			y = convertToFloat(parts[i+2]);
			x1 = convertToFloat(parts[i+3]);
			y1 = convertToFloat(parts[i+4]);
			x2 = convertToFloat(parts[i+5]);
			y2 = convertToFloat(parts[i+6]);
			createSVGPathSegCurvetoCubicAbs(x, y, x1, y1, x2, y2);
			i += 6;
			break;
		case "c":
			x = convertToFloat(parts[i+1]);
			y = convertToFloat(parts[i+2]);
			x1 = convertToFloat(parts[i+3]);
			y1 = convertToFloat(parts[i+4]);
			x2 = convertToFloat(parts[i+5]);
			y2 = convertToFloat(parts[i+6]);
			createSVGPathSegCurvetoCubicRel(x, y, x1, y1, x2, y2);
			i += 6;
			break;
		case "H":
			x = convertToFloat(parts[i+1]);
			createSVGPathSegLinetoHorizontalAbs(x);
			i += 1;
			break;
		case "h":
			x = convertToFloat(parts[i+1]);
			createSVGPathSegLinetoHorizontalRel(x);
			i += 1;
			break;
		case "L":
			x = convertToFloat(parts[i+1]);
			y = convertToFloat(parts[i+2]);
			createSVGPathSegLinetoAbs(x, y);
			i += 2;
			break;
		case "l":
			x = convertToFloat(parts[i+1]);
			y = convertToFloat(parts[i+2]);
			createSVGPathSegLinetoRel(x, y);
			i += 2;
			break;
		case "M":
			x = convertToFloat(parts[i+1]);
			y = convertToFloat(parts[i+2]);
			createSVGPathSegMovetoAbs(x, y);
			i += 2;
			break;
		case "m":
			x = convertToFloat(parts[i+1]);
			y = convertToFloat(parts[i+2]);
			createSVGPathSegMovetoRel(x, y);
			i += 2;
			break;
		case "Q":
			x = convertToFloat(parts[i+1]);
			y = convertToFloat(parts[i+2]);
			x1 = convertToFloat(parts[i+3]);
			y1 = convertToFloat(parts[i+4]);
			createSVGPathSegCurvetoQuadraticAbs(x, y, x1, y1);
			i += 4;
			break;
		case "q":
			x = convertToFloat(parts[i+1]);
			y = convertToFloat(parts[i+2]);
			x1 = convertToFloat(parts[i+3]);
			y1 = convertToFloat(parts[i+4]);
			createSVGPathSegCurvetoQuadraticRel(x, y, x1, y1);
			i += 4;
			break;
		case "S":
			x = convertToFloat(parts[i+1]);
			y = convertToFloat(parts[i+2]);
			x1 = convertToFloat(parts[i+3]);
			y1 = convertToFloat(parts[i+4]);
			createSVGPathSegCurvetoCubicSmoothAbs(x, y, x1, y1);
			i += 4;
			break;
		case "s":
			x = convertToFloat(parts[i+1]);
			y = convertToFloat(parts[i+2]);
			x1 = convertToFloat(parts[i+3]);
			y1 = convertToFloat(parts[i+4]);
			createSVGPathSegCurvetoCubicSmoothRel(x, y, x1, y1);
			i += 4;
			break;
		case "T":
			x = convertToFloat(parts[i+1]);
			y = convertToFloat(parts[i+2]);
			createSVGPathSegCurvetoQuadraticSmoothAbs(x, y);
			i += 4;
			break;
		case "t":
			x = convertToFloat(parts[i+1]);
			y = convertToFloat(parts[i+2]);
			createSVGPathSegCurvetoQuadraticSmoothRel(x, y);
			i += 4;
			break;
		case "V":
			y = convertToFloat(parts[i+1]);
			createSVGPathSegLinetoVerticalAbs(y);
			i += 1;
			break;
		case "v":
			y = convertToFloat(parts[i+1]);
			createSVGPathSegLinetoVerticalRel(y);
			i += 1;
			break;
		case "z":
			createSVGPathSegClosePath();
			break;
		default:
			break;
		}
		return i;
	}
	
	private float convertToFloat(String value) {
		try {
			float f = Float.parseFloat(value);
			return f;
		} catch(NumberFormatException e) {
			return 0.0f;
		}
	}
	
	private boolean convertToBoolean(String value) {
		try {
			boolean b = Boolean.parseBoolean(value);
			return b;
		} catch(Exception e) {
			return false;
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
		
		logger.info("Paint path. # segments="+pathData.getPathSegList().getNumberOfItems());
		
		Path2D.Float path2d = null;
		SVGPathSeg prevSeg = null;
		
		// alle Segmente des Pfads zeichnen
		for (long i=0; i<pathData.getPathSegList().getNumberOfItems(); i++) {
			
			// das Path-Segment an der angegebenen Position lesen
			SVGPathSeg pathSeg = pathData.getPathSegList().getItem(i);
			// das Path-Segment zeichnen
			path2d = pathSeg.paint(path2d, this, g2d, prevSeg);
			
			prevSeg = pathSeg;
		}
		
		if (path2d != null) {
			drawFillAndStroke(g2d, path2d);
			path2d = null;
		}
	}
	
	/**
	 * @param p1
	 * @return - converted Point
	 */
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
	
	public boolean containsPoint(Point.Float p) {
		return false;
	}
}
