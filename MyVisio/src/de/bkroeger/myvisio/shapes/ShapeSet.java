package de.bkroeger.myvisio.shapes;

import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * <p>
 * Ein 'BaseShapeSet' ist eine Menge von BaseShapes (see {@link Shape}).
 * </p><p>
 * Shape-sets werden aus XML-Dateien geladen.
 * </p>
 * 
 * @author bk
 */
public class ShapeSet {
	
	private static Logger logger = Logger.getLogger(ShapeSet.class.getName());
	
	private String name = "";
	public  String getName() { return name; }
	public  void   setName(String value) { name=value; }
	
	private String filename = "";
	public  String getFileName() { return filename; }
	public  void   setFileName(String value) { filename=value; }
	
	private Map<String, IShape> templates = new HashMap<String, IShape>();
	public  Set<String> getTemplateKeys() { return templates.keySet(); }
	public  IShape getTemplate(String name) { return templates.get(name); }
	public  Collection<IShape> getTemplates() { return templates.values(); }
	
	private TemplateSet svgElement;
	public  TemplateSet getSVGElement() { return svgElement; }
	
	private String description;
	public  String getDescription() { return this.description; }
	
	/**
	 * Konstruktor
	 */
	public ShapeSet(String name, String filename) {
		
	}
	
	/**
	 * Liefert die Anzahl Shapes im ShapeSet.
	 * 
	 * @return - Anzahl Shapes
	 */
	public int getShapeCount() {
		return 0;
	}
	
	/**
	 * Zeichnet die SVG-Struktur in den durch die SVGViewSpec definierten Bereich.
	 * 
	 * @param g2d - Graphics-Objekt
	 * @param viewSpec - View-Spezifikation
	 * @param colCnt - Anzahl Spalten
	 */
	public void paint(Graphics2D g2d) {
		
	}

	/**
	 * Lädt das shape-set aus einer XML-Datei.
	 * 
	 * @param filename
	 * @throws TechnicalException
	 */
	public void load(String filename) throws TechnicalException {
		
		this.filename = filename;
		
		boolean dtdValidate = false;
		boolean xsdValidate = false;
		
		try {
			// XML-Datei in DOM-Struktur parsen
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
		    dbf.setValidating(dtdValidate || xsdValidate);
			DocumentBuilder db = dbf.newDocumentBuilder(); 
			InputStream in = this.getClass().getResourceAsStream(filename); 
			if (in == null) {
				this.getClass().getClassLoader();
				in = ClassLoader.getSystemResourceAsStream(filename);
			}
			org.w3c.dom.Document doc = db.parse(in);
			
			// DOM-Struktur in Document-Hierarchie umwandeln
			this.parseXml(doc);
			
		} catch(ParserConfigurationException e) {
			e.printStackTrace();
			logger.severe("Error parsing XML-Document '"+filename+"': "+e.getMessage());
			throw new TechnicalException("Error parsing XML-Document '"+filename+"'", e);
			
		} catch (SAXException e) {
			e.printStackTrace();
			logger.severe("Error processing XML-Document '"+filename+"': "+e.getMessage());
			throw new TechnicalException("Error processing XML-Document '"+filename+"'", e);
			
		} catch (IOException e) {
			e.printStackTrace();
			logger.severe("Error reading XML-Document '"+filename+"': "+e.getMessage());
			throw new TechnicalException("Error reading XML-Document '"+filename+"'", e);
		}
	}

	/**
	 * Liest das <shape-set>-Tag ein.
	 * 
	 * @param doc
	 * @throws TechnicalException
	 */
	private void parseXml(org.w3c.dom.Document doc) throws TechnicalException {
		
		Element docElem = doc.getDocumentElement();
		
		if (docElem.getLocalName().equals("shape-set")) {
			
			// alle Attribute auswerten
			NamedNodeMap attributes = docElem.getAttributes();
			for (int i=0; i<attributes.getLength(); i++) {
				Node node = attributes.item(i);
				if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
					Attr attr = (Attr)node;
					switch (attr.getName()) {
					case "id":
						this.setName(attr.getValue());
						break;
					default:
//						throw new TechnicalException("Invalid attribute in shape-set tag!");
					}
				}
			}
			
			// alle untergeordneten Elemente bearbeiten
			NodeList children = docElem.getChildNodes();
			for (int i=0; i<children.getLength(); i++) {
				Node child = children.item(i);
				
				if (child.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element)child;
					
					switch (elem.getLocalName()) {
					case "svg":
						break;
					default:
						throw new DOMException(DOMException.NOT_SUPPORTED_ERR, 
								"Tag <"+elem.getLocalName()+"> not allowed in <shape-set>!");
					}
					
//				} else if (child.getNodeType() == Node.TEXT_NODE) {
//					Text elem = (Text)child;
//					
//					switch (elem.getLocalName()) {
//					case "desc":
//						this.description = elem.getTextContent();
//						break;
//					default:
//						throw new DOMException(DOMException.NOT_SUPPORTED_ERR, 
//								"Tag <"+elem.getLocalName()+"> not allowed in <shape-set>!");
//					}
					
//				} else {
//					throw new TechnicalException("Invalid node type below shape-set! ");
				}
			}
			
		} else {
			throw new TechnicalException("Invalid XML-Structur: Root element not found.");
		}
	}
}
