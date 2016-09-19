package de.bkroeger.myvisio.svg.elements;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import javax.swing.tree.DefaultMutableTreeNode;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.svg.SVGAnimatedString;
import org.w3c.dom.svg.SVGDescElement;
import org.w3c.dom.svg.SVGElement;
import org.w3c.dom.svg.SVGLangSpace;
import org.w3c.dom.svg.SVGMetadataElement;
import org.w3c.dom.svg.SVGSVGElement;
import org.w3c.dom.svg.SVGStringList;
import org.w3c.dom.svg.SVGStylable;
import org.w3c.dom.svg.SVGTitleElement;

import de.bkroeger.myvisio.svg.SVGAnimatedStringImpl;
import de.bkroeger.myvisio.svg.SVGStringListImpl;
import de.bkroeger.myvisio.svg.attributes.SVGFillAttributeImpl;
import de.bkroeger.myvisio.svg.attributes.SVGFillRuleAttributeImpl;
import de.bkroeger.myvisio.svg.attributes.SVGOpacityAttributeImpl;
import de.bkroeger.myvisio.svg.attributes.SVGStrokeAttributeImpl;
import de.bkroeger.myvisio.svg.attributes.SVGStrokeDasharrayAttributeImpl;
import de.bkroeger.myvisio.svg.attributes.SVGStrokeLinecapAttributeImpl;
import de.bkroeger.myvisio.svg.attributes.SVGStrokeLinejoinAttributeImpl;
import de.bkroeger.myvisio.svg.attributes.SVGStrokeWidthAttributeImpl;
import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * <p>
 * Dies ist die Implementierung des Interface {@link SVGElement} und damit die
 * Basisklasse für alle SVG-Elemente.
 * </p><p>
 * Zur Abbildung der SVG-Hierarchie erweitert die Klasse die Klasse DefaultMutableTreeNode.
 * Damit können Parent-Child-Beziehungen in beiden Richtungen durchsucht werden.
 * </p>
 * @author bk
 */
public class SVGElementImpl extends DefaultMutableTreeNode 
	implements SVGElement, SVGLangSpace, SVGStylable {
	
	private static final long serialVersionUID = 7522860630000967210L;

	private static final String XML_BASE_URI = "http://www.w3.org/XML/1998/namespace";
	
	/**
	 * Verweis auf das HTML-DOM-Element.
	 */
	protected Element domElement;

	/**
	 * The nearest ancestor ‘svg’ element. Null if the given element is the outermost svg element.
	 */
	protected SVGSVGElement ownerSVGElement;
	
	public SVGSVGElement getOwnerSVGElement() {
		return ownerSVGElement;
	}

	/**
	 * The element which established the current viewport. Often, the nearest 
	 * ancestor ‘svg’ element. Null if the given element is the outermost svg element.
	 */
	protected SVGElement viewportElement;
	
	public SVGElement getViewportElement() {
		
		// wenn es kein lokales Viewport-Element gibt,...
		if (this.viewportElement == null) {
			
			// die Hierarchie nach dem outermost SVG-Element durchsuchen
			SVGElementImpl node = this;
			while (node.getParent() != null) {
				node = (SVGElementImpl) node.getParent();
				if (node.viewportElement != null) {
					return (SVGElement)node;
				}
			}
			return null;
		} else {
			// sonst das vorhandene Viewport-Element verwenden
			return viewportElement;
		}
	}

	protected String id = UUID.randomUUID().toString();
	public String getId() { return this.id; }
	public void setId(String id) { 
		if (id == null || id.isEmpty()) {
			throw new IllegalArgumentException("Id missing!");
		}
		this.id = id; 
	}
	
	protected UUID uuid;
	/**
	 * @return - die UUID des Elementes
	 */
	public UUID getUUID() { return uuid; }
	
	protected String xmlBase = "";
	
	protected String xmlLang = "";
	
	protected String xmlSpace = "";
	
	protected SVGStringList requiredExtensions = new SVGStringListImpl();
	
	protected Map<String, Boolean> requiredFeatures = new HashMap<String, Boolean>();
	
	protected Boolean externalResourcesRequired = false;
	
	protected SVGStringList systemLanguage = new SVGStringListImpl();
	
	protected SVGAnimatedString animatedClassName = new SVGAnimatedStringImpl();
	
	protected String styleList = "";

	protected String onfocusin = "";
	protected String onfocusout = "";
	protected String onactivate = "";
	protected String onclick = "";
	protected String onmousedown = "";
	protected String onmouseup = "";
	protected String onmouseover = "";
	protected String onmousemove = "";
	protected String onmouseout = "";
	
// Font properties:
	protected String font = ""; //"font");
	protected String font_family = ""; //"font_family");
	protected String font_size = ""; //"font_size");
	protected String font_size_adjust = ""; //"font_size_adjust");
	protected String font_stretch = ""; //"font_stretch");
	protected String font_style = ""; //"font_style");
	protected String font_variant = ""; //"font_variant");
	protected String font_weight = ""; //"font_weight");
// Text properties:
	protected String direction = ""; //"direction");
	protected String letter_spacing = ""; //"letter_spacing");
	protected String text_decoration = ""; //"text_decoration");
	protected String unicode_bidi = ""; //"unicode_bidi");
	protected String word_spacing = ""; //"word_spacing");
// Other properties for visual media:
	protected String clip = ""; //"clip");
	protected String color = ""; //"color");
	protected String cursor = ""; //("cursor");
	protected String display = ""; //("display");
	protected String overflow = ""; //("overflow");
	protected String visibility = ""; //("visibility");
// Clipping, Masking and Compositing properties:
	protected String clipPath = ""; //("clip-path");
	protected String clipRule = ""; //("clip-rule");
	protected String mask = ""; //("mask");
	protected String opacity = ""; //("opacity");
// Filter Effects properties:
	protected String enableBackground = ""; //("enable-background");
	protected String filter = ""; //("filter");
	protected String floodColor = ""; //("flood-color");
	protected String floodOpacity = ""; //("flood-opacity");
	protected String lightingColor = ""; //("lighting-color");
// Gradient properties:
	protected String stopColor = ""; //("stop-color");
	protected String stopOpacity = ""; //("stop-opacity");
// Interactivity properties:
	protected String pointerEvents = ""; //("pointer-events");
// Color and Painting properties:
	protected String colorInterpolation = ""; //("color-interpolation");
	protected String colorInterpolationFilters = ""; //("color-interpolation-filters");
	protected String colorProfile = ""; //("color-profile");
	protected String colorRendering = ""; //("color-rendering");
	protected SVGFillAttributeImpl fill; //("fill");
	protected SVGOpacityAttributeImpl fillOpacity; //("fill-opacity");
	protected SVGFillRuleAttributeImpl fillRule; //("fill-rule");
	protected String imageRendering = ""; //("image-rendering");
	protected String marker = ""; //("marker");
	protected String markerEnd = ""; //("marker-end");
	protected String markerMid = ""; //("marker-mid");
	protected String markerStart = ""; //("marker-start");
	protected String shapeRendering = ""; //("shape-rendering");
	protected SVGStrokeAttributeImpl stroke; //("stroke");
	protected SVGStrokeDasharrayAttributeImpl strokeDasharray; //("stroke-dasharray");
	protected String strokeDashoffset = ""; //("stroke-dashoffset");
	protected SVGStrokeLinecapAttributeImpl strokeLinecap; //("stroke-linecap");
	protected SVGStrokeLinejoinAttributeImpl strokeLinejoin; //("stroke-linejoin");
	protected String strokeMiterlimit = ""; //("stroke-miterlimit");
	protected SVGOpacityAttributeImpl strokeOpacity; //("stroke-opacity");
	protected SVGStrokeWidthAttributeImpl strokeWidth; //("stroke-width");
	protected String textRendering = ""; //("text-rendering");
// Text properties:
	protected String alignmentBaseline = ""; //("alignment-baseline");
	protected String baselineShift = ""; //("baseline-shift");
	protected String dominantBaseline = ""; //("dominant-baseline");
	protected String glyphOrientationHorizontal = ""; //("glyph-orientation-horizontal");
	protected String glyphOrientationVertical = ""; //("glyph-orientation-vertical");
	protected String kerning = ""; //("kerning");
	protected String textAnchor = ""; //("text-anchor");
	protected String writingMode = ""; //("writing-mode");
	
	protected SVGDescElement description;
	/**
	 * @return - (optional) die Beschreibung des Elements
	 */
	public SVGDescElement getDescription() { return description; }
	
	protected SVGTitleElement title;
	/**
	 * @return - (optional) der Titel des Elements
	 */
	public SVGTitleElement getTitle() { return title; }
	
	protected SVGMetadataElement metadata;
	/**
	 * @return - (optional) die Metadaten des Elements als W3C-Element
	 */
	public SVGMetadataElement getMetadata() { return this.metadata; }

	/**
	 * Default-Konstruktor
	 */
	public SVGElementImpl() {
		super();
		init();
	}
	
	/**
	 * Konstruktor mit HTML-DOM-Element.
	 * 
	 * @param elem
	 */
	public SVGElementImpl(Element elem) {
		super();
		init();
		this.domElement = elem;
	}
	
	public Object clone() {
		return null;
	}
	
	/**
	 * @param clone
	 */
	public void copy(SVGElementImpl clone) {
		clone.alignmentBaseline = new String(alignmentBaseline);
		clone.animatedClassName = (SVGAnimatedString) animatedClassName.clone();
		clone.baselineShift = new String(baselineShift);
		clone.clip = new String(clip);
		clone.clipPath = new String(clipPath);
		clone.color = new String(color);
		clone.colorInterpolation = new String(colorInterpolation);
		clone.colorProfile = new String(colorProfile);
		clone.colorRendering = new String(colorRendering);
		clone.cursor = new String(cursor);
		clone.description = description != null ? (SVGDescElement) description.clone() : null;
		clone.direction = new String(direction);
		clone.display = new String(display);
		clone.domElement = null;
		clone.dominantBaseline = new String(dominantBaseline);
		clone.enableBackground = new String(enableBackground);
		clone.externalResourcesRequired = new Boolean(externalResourcesRequired);
		clone.fill = fill.clone();
		clone.fillOpacity = fillOpacity != null ? (SVGOpacityAttributeImpl) fillOpacity.clone() : null;
		clone.fillRule = fillRule != null ? (SVGFillRuleAttributeImpl) fillRule.clone() : null;
		clone.filter = new String(filter);
		clone.floodColor = new String(floodColor);
		clone.floodOpacity = new String(floodOpacity);
		clone.font = new String(font);
		clone.font_family = new String(font_family);
		clone.font_size = new String(font_size);
		clone.font_size_adjust = new String(font_size_adjust);
		clone.font_stretch = new String(font_stretch);
		clone.font_style = new String(font_style);
		
		clone.font_variant = new String(font_variant);
		clone.font_weight = new String(font_weight);
		clone.glyphOrientationHorizontal = new String(glyphOrientationHorizontal);
		clone.glyphOrientationVertical = new String(glyphOrientationVertical);
//		Nicht kopieren! Id wird neu vergeben
//		clone.id = new String(id);
		clone.imageRendering = new String(imageRendering);
		clone.kerning = new String(kerning);
		clone.letter_spacing = new String(letter_spacing);
		clone.lightingColor = new String(lightingColor);
		clone.marker = new String(marker);
		clone.markerEnd = new String(markerEnd);
		clone.markerMid = new String(markerMid);
		clone.markerStart = new String(markerStart);
		clone.mask = new String(mask);
		clone.onactivate = new String(onactivate);
		clone.onclick = new String(onclick);
		clone.onfocusin = new String(onfocusin);
		clone.onmousedown = new String(onmousedown);
		clone.onmousemove = new String(onmousemove);
		clone.onmouseout = new String(onmouseout);
		clone.onmouseover = new String(onmouseover);
		clone.onmouseup = new String(onmouseup);
		clone.opacity = new String(opacity);
		clone.overflow = new String(overflow);
		clone.ownerSVGElement = null;
		clone.pointerEvents = new String(pointerEvents);
		clone.requiredExtensions = requiredExtensions != null ? requiredExtensions.clone() : null;
		clone.requiredFeatures = new HashMap<String, Boolean>();
		for (String name : requiredFeatures.keySet()) {
			clone.requiredFeatures.put(name, requiredFeatures.get(name));
		}
		clone.shapeRendering = new String(shapeRendering);
		clone.stopColor = new String(stopColor);
		clone.stopOpacity = new String(stopOpacity);
		clone.stroke = stroke != null ? (SVGStrokeAttributeImpl) stroke.clone() : null;
		clone.strokeDasharray = strokeDasharray != null ? (SVGStrokeDasharrayAttributeImpl) strokeDasharray.clone() : null;
		clone.strokeDashoffset = new String(strokeDashoffset);
		clone.strokeLinecap = strokeLinecap != null ? (SVGStrokeLinecapAttributeImpl) strokeLinecap.clone() : null;
		clone.strokeLinejoin = strokeLinejoin != null ? (SVGStrokeLinejoinAttributeImpl) strokeLinejoin.clone() : null;
		clone.strokeMiterlimit = new String(strokeMiterlimit);
		clone.strokeOpacity = strokeOpacity != null ? (SVGOpacityAttributeImpl) strokeOpacity.clone() : null;
		clone.strokeWidth = strokeWidth != null ? (SVGStrokeWidthAttributeImpl) strokeWidth.clone() : null;
		clone.styleList = new String(styleList);
		clone.systemLanguage = systemLanguage.clone();
		clone.text_decoration = new String(text_decoration);
		clone.textAnchor = new String(textAnchor);
		clone.textRendering = new String(textRendering);
		clone.title = title != null ? (SVGTitleElement) title.clone() : null;
		clone.unicode_bidi = new String(unicode_bidi);
		clone.visibility = new String(visibility);
		clone.word_spacing = new String(word_spacing);
		clone.writingMode = new String(writingMode);
		clone.xmlBase = new String(xmlBase);
		clone.xmlLang = new String(xmlLang);
		clone.xmlSpace = new String(xmlSpace);
	}
	
	/**
	 * Initialisierung
	 */
	private void init() {
		requiredFeatures = new HashMap<String, Boolean>();
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#SVG", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#SVGDOM", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#SVG-static", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#SVGDOM-static", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#SVG-animation", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#SVGDOM-animation", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#SVG-dynamic", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#SVGDOM-dynamic", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#CoreAttribute", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#Structure", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#BasicStructure", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#ContainerAttribute", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#ConditionalProcessing", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#Image", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#Style", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#ViewportAttribute", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#Shape", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#Text", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#BasicText", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#PaintAttribute", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#BasicPaintAttribute", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#OpacityAttribute", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#GraphicsAttribute", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#BasicGraphicsAttribute", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#Marker", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#ColorProfile", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#Gradient", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#Pattern", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#Clip", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#BasicClip", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#Mask", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#Filter", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#BasicFilter", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#DocumentEventsAttribute", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#GraphicalEventsAttribute", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#AnimationEventsAttribute", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#Cursor", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#Hyperlinking", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#XlinkAttribute", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#ExternalResourcesRequired", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#View", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#Script", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#Animation", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#Font", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#BasicFont", true);
		requiredFeatures.put("http://www.w3.org/TR/SVG11/feature#Extensibility", true);
		
		fill = new SVGFillAttributeImpl();
		fillOpacity = new SVGOpacityAttributeImpl();
		fillRule = new SVGFillRuleAttributeImpl();
		
		stroke = new SVGStrokeAttributeImpl();
		strokeWidth = new SVGStrokeWidthAttributeImpl();
		strokeLinecap = new SVGStrokeLinecapAttributeImpl();
		strokeLinejoin = new SVGStrokeLinejoinAttributeImpl();
		strokeDasharray = new SVGStrokeDasharrayAttributeImpl();
	}

	

	
	public String getXMLbase() {
		return this.xmlBase;
	}

	
	public void setXMLbase(String XMLbase) {
		this.xmlBase = XMLbase;
	}
	
	/**
	 * A conditional processing attribute is one that controls whether or not the element
	 * on which it appears is processed. Most elements, but not all, may have 
	 * conditional processing attributes specified on them. See Conditional processing for details. 
	 * The conditional processing attributes defined in SVG 1.1 are ‘requiredExtensions’, 
	 * ‘requiredFeatures’ and ‘systemLanguage’.
	 * 
	 * @param elem 
	 */
	public void parseConditionalProcessingAttributes(Element elem) {
		
		if (elem.hasAttribute("requiredExtensions")) {
			String[] values = elem.getAttribute("requiredExtensions").split("s+");
			this.requiredExtensions = new SVGStringListImpl(values);
		}
		
		if (elem.hasAttribute("requiredFeatures")) {
			String valueString = elem.getAttribute("requiredFeatures");
			String[] values = valueString.split("s+");
			for (String value : values) {
				Boolean result = requiredFeatures.get(value);
				if (result == null) {
					throw new DOMException(DOMException.SYNTAX_ERR, 
							"Invalid syntax of required extension: "+value);
				}
			}
			this.requiredExtensions = new SVGStringListImpl(values);
		}
		
		if (elem.hasAttribute("systemLanguage")) {
			String[] values = elem.getAttribute("systemLanguage").split(",");
			this.systemLanguage = new SVGStringListImpl(values);
		}
	}
	
	/**
	 * A graphical event attribute is an event attribute that specifies script to run 
	 * for a particular user interaction event.
	 * 
	 * @param elem 
	 */
	public void parseGraphicalEventAttributes(Element elem) {
		
		onfocusin = elem.getAttribute("onfocusin");
		onfocusout = elem.getAttribute("onfocusout");
		onactivate = elem.getAttribute("onactivate");
		onclick = elem.getAttribute("onclick");
		onmousedown = elem.getAttribute("onmousedown");
		onmouseup = elem.getAttribute("onmouseup");
		onmouseover = elem.getAttribute("onmouseover");
		onmousemove = elem.getAttribute("onmousemove");
		onmouseout = elem.getAttribute("onmouseout");
	}
	
	/**
	 * An XML attribute on an SVG element which specifies a value for a given property for
	 * that element. Note that although any property may be specified on any element, 
	 * not all properties will apply to (affect the rendering of) a given element.
	 * @throws TechnicalException 
	 */
	protected void parsePresentationAttributes(Element elem) throws TechnicalException {
		
		//Font properties:
			this.font = elem.getAttribute("font");
			this.font_family = elem.getAttribute("font-family");
			this.font_size = elem.getAttribute("font-size");
			this.font_size_adjust = elem.getAttribute("font-size-adjust");
			this.font_stretch = elem.getAttribute("font-stretch");
			this.font_style = elem.getAttribute("font-style");
			this.font_variant = elem.getAttribute("font-variant");
			this.font_weight = elem.getAttribute("font-weight");
		//Text properties:
			this.direction = elem.getAttribute("direction");
			this.letter_spacing = elem.getAttribute("letter-spacing");
			this.text_decoration = elem.getAttribute("text-decoration");
			this.unicode_bidi = elem.getAttribute("unicode-bidi");
			this.word_spacing = elem.getAttribute("word-spacing");
		//Other properties for visual media:
			this.clip = elem.getAttribute("clip");
			this.color = elem.getAttribute("color");
			this.cursor = elem.getAttribute("cursor");
			this.display = elem.getAttribute("display");
			this.overflow = elem.getAttribute("overflow");
			this.visibility = elem.getAttribute("visibility");
		// Clipping, Masking and Compositing properties:
			this.clipPath = elem.getAttribute("clip-path");
			this.clipRule = elem.getAttribute("clip-rule");
			this.mask = elem.getAttribute("mask");
			this.opacity = elem.getAttribute("opacity");
		// Filter Effects properties:
			this.enableBackground = elem.getAttribute("enable-background");
			this.filter = elem.getAttribute("filter");
			this.floodColor = elem.getAttribute("flood-color");
			this.floodOpacity = elem.getAttribute("flood-opacity");
			this.lightingColor = elem.getAttribute("lighting-color");
		// Gradient properties:
			this.stopColor = elem.getAttribute("stop-color");
			this.stopOpacity = elem.getAttribute("stop-opacity");
		// Interactivity properties:
			this.pointerEvents = elem.getAttribute("pointer-events");
		// Color and Painting properties:
			this.colorInterpolation = elem.getAttribute("color-interpolation");
			this.colorInterpolationFilters = elem.getAttribute("color-interpolation-filters");
			this.colorProfile = elem.getAttribute("color-profile");
			this.colorRendering = elem.getAttribute("color-rendering");
			this.fill = new SVGFillAttributeImpl();
			if (elem.hasAttribute("fill"))
				this.fill.parse(elem.getAttribute("fill"));
			if (elem.hasAttribute("fill-opacity"))
				this.fillOpacity.parse(elem.getAttribute("fill-opacity"));
			if (elem.hasAttribute("fill-rule"))
				this.fillRule.parse(elem.getAttribute("fill-rule"));
			this.imageRendering = elem.getAttribute("image-rendering");
			this.marker = elem.getAttribute("marker");
			this.markerEnd = elem.getAttribute("marker-end");
			this.markerMid = elem.getAttribute("marker-mid");
			this.markerStart = elem.getAttribute("marker-start");
			this.shapeRendering = elem.getAttribute("shape-rendering");
			if (elem.hasAttribute("stroke"))
				this.stroke.parse(elem.getAttribute("stroke"));
			if (elem.hasAttribute("stroke-dasharray"))
				this.strokeDasharray.parse(elem.getAttribute("stroke-dasharray"));
			this.strokeDashoffset = elem.getAttribute("stroke-dashoffset");
			if (elem.hasAttribute("stroke-linecap"))
				this.strokeLinecap.parse(elem.getAttribute("stroke-linecap"));
			if (elem.hasAttribute("stroke-linejoin"))
				this.strokeLinejoin.parse(elem.getAttribute("stroke-linejoin"));
			this.strokeMiterlimit = elem.getAttribute("stroke-miterlimit");
			if (elem.hasAttribute("stroke-opacity"))
				this.strokeOpacity.parse(elem.getAttribute("stroke-opacity"));
			if (elem.hasAttribute("stroke-width"))
				this.strokeWidth.parse(elem.getAttribute("stroke-width"));
			this.textRendering = elem.getAttribute("text-rendering");
		// Text properties:
			this.alignmentBaseline = elem.getAttribute("alignment-baseline");
			this.baselineShift = elem.getAttribute("baseline-shift");
			this.dominantBaseline = elem.getAttribute("dominant-baseline");
			this.glyphOrientationHorizontal = elem.getAttribute("glyph-orientation-horizontal");
			this.glyphOrientationVertical = elem.getAttribute("glyph-orientation-vertical");
			this.kerning = elem.getAttribute("kerning");
			this.textAnchor = elem.getAttribute("text-anchor");
			this.writingMode = elem.getAttribute("writing-mode");
	}

	protected String printXmlStructure(Element elem) {
		StringBuilder sb = new StringBuilder();
		sb.append("<"+elem.getTagName()+" ");
		printXmlAttributes(sb, elem);
		sb.append(">\n");
		printXmlSubstructure(sb, elem);
		sb.append("</"+elem.getTagName()+">\n");
		return sb.toString();
	}
	
	private void printXmlAttributes(StringBuilder sb, Element elem) {
		NamedNodeMap attrMap = elem.getAttributes();
		for (int i=0; i<attrMap.getLength(); i++) {
			Node node = attrMap.item(i);
			if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
				Attr attr = (Attr)node;
				sb.append(attr.getLocalName()+"=\""+attr.getValue()+"\" ");
			}
		}
	}
	
	private void printXmlSubstructure(StringBuilder sb, Element elem) {
		NodeList children = elem.getChildNodes();
		for (int i=0; i<children.getLength(); i++) {
			Node node = children.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element child = (Element)node;
				sb.append(printXmlStructure(child));
			}
		}
	}
	
	/**
	 * <p>
	 * Analysiert die Standard-Attribute des DOM-Elements.
	 * </p><p>
	 * The core attributes are those attributes that can be specified on any SVG element. 
	 * See Common attributes. The core attributes are ‘id’, ‘xml:base’, ‘xml:lang’ and ‘xml:space’.
	 * </p>
	 * @param elem
	 * @throws TechnicalException 
	 */
	protected void parseXml(Element elem) throws TechnicalException {
		
		parseBaseAttributes(elem);
		parseStylableAttributes(elem);
	}
	
	/**
	 * Analysiert das &lt;desc&gt>-Tag.
	 * 
	 * @param childElem
	 * @throws TechnicalException
	 */
	protected void parseDescNode(Element childElem) throws TechnicalException {
		
		SVGDescElementImpl descElem = new SVGDescElementImpl();
		this.add(descElem);	
		descElem.setParent(this);
		descElem.parseXml(childElem);
		this.description = descElem;
	}
	
	/**
	 * Analysiert die deskriptiven Tags.
	 * @param childElem
	 * @throws TechnicalException
	 */
	protected void parseDescriptiveElements(Element childElem) throws TechnicalException {
		
		switch (childElem.getLocalName()) {
		case "desc":
			parseDescNode(childElem);
			break;
		case "title":
			parseTitleNode(childElem);
			break;
		case "metadata":
			parseMetadataNode(childElem);
			break;
		default:
			break;
		}
	}
	
	protected void parseAnimationElements(Element childElem) throws TechnicalException {
		
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
		default:
			break;
		}
	}
	
	protected void parseShapeElements(Element childElem)
			throws TechnicalException {

		switch (childElem.getLocalName()) {
		case "path":
			// <path>-Element erstellen
			SVGPathElementImpl pathElem = new SVGPathElementImpl();
			this.add(pathElem);
			pathElem.setParent(this);
			pathElem.parseXml(childElem);
			break;
		case "rect":
			// <rect>-Element erstellen
			SVGRectElementImpl rectElem = new SVGRectElementImpl();
			this.add(rectElem);
			rectElem.setParent(this);
			rectElem.parseXml(childElem);
			break;
		case "circle":
			// <circle>-Element erstellen
			SVGCircleElementImpl circleElem = new SVGCircleElementImpl();
			this.add(circleElem);
			circleElem.setParent(this);
			circleElem.parseXml(childElem);
			break;
		case "ellipse":
			// <rect>-Element erstellen
			SVGEllipseElementImpl ellipseElem = new SVGEllipseElementImpl();
			this.add(ellipseElem);
			ellipseElem.setParent(this);
			ellipseElem.parseXml(childElem);
			break;
		case "line":
			break;
		case "polyline":
			break;
		case "polygon":
			break;
		default:
			break;
		}
	}
	
	protected void parseStructuralElements(Element childElem)
			throws TechnicalException {

		switch (childElem.getLocalName()) {
		case "defs":
			break;
		case "svg":
			break;
		case "g":
		case "box":		
			// TODO: Wofür ist das <box>-Tag?
			// <rect>-Element erstellen
			SVGGElementImpl gElem = new SVGGElementImpl();
			this.add(gElem);
			gElem.setParent(this);

			// Attribute und Struktur des SVG-Tag einlesen
			gElem.parseXml(childElem);
			break;
		case "symbol":
			break;
		case "use":
			break;
		default:
			break;
		}
	}
	
	
	protected void parseGradientElements(Element childElem)
			throws TechnicalException {

		switch (childElem.getLocalName()) {
		case "linearGradient":
			break;
		case "radialGradient":
			break;
		default:
			break;
		}
	}
	
	protected void parseTextElements(Element childElem)
			throws TechnicalException {

		switch (childElem.getLocalName()) {
		case "text":		
			// <text>-Element erstellen
			SVGTextElementImpl textElem = new SVGTextElementImpl();
			this.add(textElem);
			textElem.setParent(this);

			// Attribute und Struktur des SVG-Tag einlesen
			textElem.parseXml(childElem);
			break;
		default:
			break;
		}
	}

	
	/**
	 * Analysiert das &lt;title&gt>-Tag.
	 * 
	 * @param childElem
	 * @throws TechnicalException
	 */
	protected void parseTitleNode(Element childElem) throws TechnicalException {
		
		SVGTitleElementImpl titleElem = new SVGTitleElementImpl();
		this.add(titleElem);	
		titleElem.setParent(this);
		titleElem.parseXml(childElem);
		this.title = titleElem;
	}
	
	/**
	 * Analysiert das &lt;metadata&gt>-Tag.
	 * 
	 * @param childElem
	 * @throws TechnicalException
	 */
	private void parseMetadataNode(Element childElem) throws TechnicalException {
		
		SVGMetadataElementImpl metadataElem = new SVGMetadataElementImpl(childElem);
		this.add(metadataElem);	
		metadataElem.setParent(this);
		this.metadata = metadataElem;
	}
	
	/**
	 * <p>
	 * Analysiert das Element und sucht nach verschiedenen Standard-Attributen.
	 * </p>
	 * @param elem - das W3C-DOM-Element
	 */
	protected void parseBaseAttributes(Element elem) {
		
		if (elem.hasAttribute("id")) {
			this.id  = elem.getAttribute("id");
		}
		
		if (elem.hasAttribute("uuid")) {
			String uuidString = elem.getAttribute("uuid");
			this.uuid = UUID.fromString(uuidString);
		} else {
			this.uuid = UUID.randomUUID();
		}
		
		if (elem.hasAttributeNS(XML_BASE_URI, "base")) {
			this.xmlBase = elem.getAttributeNS(XML_BASE_URI, "base");
		}		
		
		if (elem.hasAttributeNS(XML_BASE_URI, "lang")) {
			this.xmlLang = elem.getAttributeNS(XML_BASE_URI, "lang");
		}
		
		if (elem.hasAttributeNS(XML_BASE_URI, "space")) {
			this.xmlSpace = elem.getAttributeNS(XML_BASE_URI, "space");
		}
	}
	
	protected void parseStylableAttributes(Element elem) {
		
//		‘class’ - Liste mit CSS-Class-Namen
		if (elem.hasAttribute("class")) {
			
			String classes = elem.getAttribute("class");
			animatedClassName = new SVGAnimatedStringImpl(classes);
		}
//		‘style’
		if (elem.hasAttribute("style")) {
			this.styleList = elem.getAttribute("style");
		}
//		‘externalResourcesRequired’
		if (elem.hasAttribute("externalResourcesRequired")) {
			this.externalResourcesRequired = 
				Boolean.parseBoolean(elem.getAttribute("externalResourcesRequired"));
		} else {
			this.externalResourcesRequired = false;
		}
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

	
	public SVGAnimatedString getClassName() {
		return animatedClassName;
	}

	
	public CSSStyleDeclaration getStyle() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public CSSValue getPresentationAttribute(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings("javadoc")
	public void verifySvgHierarchy(Logger logger, int indent) {
	
//		StringBuilder indentation = new StringBuilder("-");
//		for (int i=0; i<indent; i++) indentation.append("-");
//		String msg = "SVG:"+indentation.toString()+this.getClass().getName();
//		logger.info(msg);
//		
//		indent += 1;
//		if (this.getChildCount() > 0) {
//			for (int j=0; j<this.getChildCount(); j++) {
//				SVGElementImpl node = (SVGElementImpl) this.getChildAt(j);
//				if (node.getParent() == null) node.setParent(this);
//				((SVGElementImpl)node).verifySvgHierarchy(logger, indent);
//			}
//		}
	}

	/**
	 * Konvertiert das jeweilige SVG-Element in ein XML-Tag.
	 * @param doc
	 * @param parent
	 */
	public void convertToXml(Document doc, Element parent) {
		
	}
}
