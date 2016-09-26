package de.bkroeger.myvisio.model;

import java.util.UUID;
import java.util.logging.Logger;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGTitleElement;

import de.bkroeger.myvisio.svg.SVGAnimatedLengthImpl;
import de.bkroeger.myvisio.svg.SVGLengthImpl;
import de.bkroeger.myvisio.svg.elements.SVGSVGElementImpl;
import de.bkroeger.myvisio.svg.elements.SVGTitleElementImpl;
import de.bkroeger.myvisio.utility.DINA4;
import de.bkroeger.myvisio.utility.PageDimension;
import de.bkroeger.myvisio.utility.TechnicalException;
import de.bkroeger.myvisio.view.WorksheetView;
import de.bkroeger.myvisio.view.ZoomDialog.ZoomFactor;

/**
 * <p>
 * Ein 'Worksheet' repräsentiert ein Zeichnungsblatt in einem {@link Workbook}.
 * </p><p>
 * Um grösstmögliche Freiheit bei der Benennung der Seiten zu haben, werden diese intern durch
 * eine UUID identifiziert.
 * </p><p>
 * Die Default-Grösse einer Page entspricht einem DINA4-Blatt mit einer Auflösung von
 * 72px/inch.
 * </p><p>
 * Jedes Worksheet enthält eine Liste der verwendeten ShapeSets. Diese werden durch
 * ihre UUID referenziert.
 * </p><p>
 * Soweit Standard-Shapes verwendet werden, werden diese ebenfalls durch ihre UUID 
 * referenziert. Durch Transformationen werden die Position und Größe des Shapes angepasst.
 * </p><p>
 * Non-Standard-Shapes und -ShapeSets können auch inline im Worksheet enthalten sein.
 * </p>
 * @author bk
 */
public class Worksheet extends SVGSVGElementImpl implements Page {
	
	private static final long serialVersionUID = -872125158831656224L;

	private static final Logger logger = Logger.getLogger(SVGSVGElementImpl.class.getName());
//	
//	private SVGRect viewport;
//	
//	private SVGAnimatedTransformList savedTransformList;

	/**
	 * Sichtbare Seitenummer.
	 */
	private String pageNo = "1";
	/**
	 * @return - pageNo
	 */
	public String getPageNo() { return pageNo; }
	/**
	 * @param pageNo
	 */
	public void setPageNo(String pageNo) { this.pageNo = pageNo; }
//	
//	/**
//	 * Die interne Id der Seite.
//	 */
//	private UUID uuid = UUID.randomUUID();
//	/**
//	 * @return - UUID
//	 */
//	public UUID getUuid() { return uuid; }
	
	/**
	 * Die Größe der Seite in Pixel
	 */
	private PageDimension pageDimension = new DINA4();
	/**
	 * @return - pageDimension
	 */
	public PageDimension getPageDimension() { return pageDimension; }
	
	/**
	 * Flag, ob Daten auf der Seite verändert wurden.
	 */
	private boolean dirtyFlag = false;
	/**
	 * @return - das Dirty-Flag
	 */
	public  boolean isDirty() { 
		return this.dirtyFlag; 
	}
	/**
	 * 
	 */
	public void setDirty() {
		if (dirtyFlag == false) {
			this.dirtyFlag = true;
			refreshTitle();
		}
	}
	/**
	 * 
	 */
	public void clearDirty() {
		if (dirtyFlag == true) {
			this.dirtyFlag = false;
			refreshTitle();
		}
	}
	
	/**
	 * Referenz auf das Dokument, zu dem die Seite gehört (Parent).
	 */
	private Workbook visioDoc;
	/**
	 * @return - das Workbook
	 */
	public  Workbook getWorkBook() { return visioDoc; }
	
	/**
	 * SVG-Root-Element
	 */
	private SVGSVGElementImpl svgRoot;
	/**
	 * @return - das SVG-Root
	 */
	public SVGSVGElementImpl getSVGRoot() { return svgRoot; }
	
	/**
	 * 
	 * Verweis auf die Anzeige-Seite.
	 */
	private WorksheetView worksheetView;
	/**
	 * @param canvas
	 */
	public void setCanvas(WorksheetView canvas) {
		worksheetView = canvas;
		svgRoot.setScaleFactor(worksheetView.getScaleFactor());
	}
	
	private int zoomPercent = 100;
	/**
	 * @return - Zoom-Percent
	 */
	public  int getZoomPercent() { return zoomPercent; }
	/**
	 * @param value
	 */
	public  void setZoomPercent(int value) { zoomPercent = value; }
	
	private ZoomFactor zoomFactor = ZoomFactor.PROZ100;
	/**
	 * @return - Zoom-Factor
	 */
	public  ZoomFactor getZoomFactor() { return zoomFactor; }
	/**
	 * @param zoomFactor
	 * @throws TechnicalException
	 */
	public  void setZoomFactor(ZoomFactor zoomFactor) throws TechnicalException { 
		float scaleFactor = 1.0f;
		switch (zoomFactor) {
		case PROZ400:
			zoomPercent = 400;
			scaleFactor = 4.0f;
			break;
		case PROZ200:
			zoomPercent = 200;
			scaleFactor = 2.0f;
			break;
		case PROZ150:
			zoomPercent = 150;
			scaleFactor = 1.5f;
			break;
		case PROZ100:
			zoomPercent = 100;
			scaleFactor = 1.0f;
			break;
		case PROZ75:
			zoomPercent = 75;
			scaleFactor = 0.75f;
			break;
		case PROZ50:
			zoomPercent = 50;
			scaleFactor = 0.5f;
			break;
		case PROZWIDTH:
			zoomPercent = -1;
			throw new TechnicalException("Not yet implemented");
//			break;
		case PROZFULL:
			zoomPercent = -1;
			throw new TechnicalException("Not yet implemented");
//			break;
		case PROZVALUE:
			// Wert ist schon gesetzt
			scaleFactor = zoomPercent / 100;
			break;
		default:
			zoomPercent = 100;
			break;
		}
		worksheetView.setScaleFactor(scaleFactor);
		svgRoot.setScaleFactor(scaleFactor);
	} 
	
	/**
	 * <p>
	 * Default-Konstruktor.
	 * </p>
	 * @param visioDoc
	 */
	public Worksheet(Workbook visioDoc) {
		
		// Referenz auf das Dokument sichern
		this.visioDoc = visioDoc;
		
		// ein SVGRoot-Element für diese Seite anlegen
		svgRoot = new SVGSVGElementImpl();
	}
//	
//	/**
//	 * @param viewport 
//	 */
//	public void negotiateViewport(SVGElement viewport) {
//		
//		if (viewport instanceof SVGRectElement && viewport != null) {
//			SVGRectElement rectElem = (SVGRectElement)viewport;
//			
//			SVGRect rect = new SVGRectImpl();
//			rect.setHeight(rectElem.getHeight().getAnimVal().getValue());
//			rect.setWidth(rectElem.getWidth().getAnimVal().getValue());
//			
//			super.negotiateViewport(rect);
////		} else {
////			throw new TechnicalException("Invalid viewport element. Type="+viewport.getClass().getName());
//		}
//	}

	/**
	 * <p>
	 * Liefert den Titel bestehend aus Datei-Title, Seitennummer und Dirty-Indicator.
	 * </p>
	 * @return - generierter Titel
	 */
	public SVGTitleElement getTitle() {
		
		if (this.title == null) {
			this.title = new SVGTitleElementImpl(
				visioDoc.getTitle()+" - "+pageNo + (isDirty() ? "*" : ""));
		}
		return super.getTitle();
	}
	
	/**
	 * Wird aufgerufen, wenn ein geänderter Titel angezeigt werden soll.
	 * Gibt den neuen Titel an die Anzeige weiter.
	 */
	public void refreshTitle() {
		if (this.worksheetView != null) {
			SVGTitleElement title = this.getTitle();
			this.worksheetView.setTitle(title.toString());
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
				case "shape":
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
		
		
		if (elem.hasAttribute("uuid")) {
			String uuidString = elem.getAttribute("uuid");
			this.uuid = UUID.fromString(uuidString);
		}
		
		if (elem.hasAttribute("no")) {
			this.pageNo = elem.getAttribute("no");
		}
		
		if (elem.hasAttribute("width")) {
			Float l = Float.parseFloat(elem.getAttribute("width"));
			setWidth(new SVGAnimatedLengthImpl(new SVGLengthImpl(l)));
		}
		
		if (elem.hasAttribute("height")) {
			Float l = Float.parseFloat(elem.getAttribute("height"));
			setHeight(new SVGAnimatedLengthImpl(new SVGLengthImpl(l)));
		}
		
		if (elem.hasAttribute("zoom")) {
			this.zoomPercent = Integer.parseInt(elem.getAttribute("zoom"));
			float scaleFactor;
			switch (zoomPercent) {
			case 400:
				zoomFactor = ZoomFactor.PROZ400;
				scaleFactor = 4.0f;
				break;
			case 200:
				zoomFactor = ZoomFactor.PROZ200;
				scaleFactor = 2.0f;
				break;
			case 150:
				zoomFactor = ZoomFactor.PROZ150;
				scaleFactor = 1.5f;
				break;
			case 100:
				zoomFactor = ZoomFactor.PROZ100;
				scaleFactor = 1.0f;
				break;
			case 75:
				zoomFactor = ZoomFactor.PROZ75;
				scaleFactor = 0.75f;
				break;
			case 50:
				zoomFactor = ZoomFactor.PROZ50;
				scaleFactor = 0.5f;
				break;
			default:
				zoomFactor = ZoomFactor.PROZVALUE;
				scaleFactor = zoomPercent / 100;
				break;
			}
			if (worksheetView != null) worksheetView.setScaleFactor(scaleFactor);
			if (svgRoot != null) svgRoot.setScaleFactor(scaleFactor);
		}

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
		}
		
		// y
		if (elem.hasAttribute("y")) {
			this.y = new SVGAnimatedLengthImpl(
				new SVGLengthImpl(Float.parseFloat(elem.getAttribute("y"))));
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

	/**
	 * Konvertiert die Daten der Visio-Seite in ein &lt;page&gt;-Tag.
	 * 
	 * @param doc
	 * @param docElem
	 */
	public void convertToXml(Document doc, Element docElem) {
		
		Element pageElem = doc.createElement("page");
		pageElem.setAttribute("id", this.uuid.toString());
		pageElem.setAttribute("no", this.pageNo);
		pageElem.setAttribute("zoom", ""+this.zoomPercent);
		pageElem.setAttribute("dimension", this.pageDimension.toString());
		docElem.appendChild(pageElem);
		
		// SVG-Tag einfügen
		svgRoot.convertToXml(doc, pageElem);
	}
	
	/**
	 * @param book
	 * @return - a new WorkSheet
	 */
	public static Worksheet createDefaultWorksheet(Workbook book) {
		
		Worksheet sheet = new Worksheet(book);
//		sheet.pageDimension = new PageDimension();
		sheet.pageNo = "1";
		sheet.uuid = UUID.randomUUID();
//		sheet.zoomFactor = new ZoomFactor();
		return sheet;
	}
}
