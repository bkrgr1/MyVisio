package de.bkroeger.myvisio.data;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import java.util.logging.Logger;

import de.bkroeger.myvisio.shapes.TemplateSet;
import de.bkroeger.myvisio.svg.elements.SVGSVGElementImpl;
import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * <p>
 * Ein TemplateFile enthält ein {@link TemplateSet | Set von Templates} 
 * zu einem bestimmten Thema. Es wird nicht über diese Anwendung erstellt oder gepflegt,
 * sondern von der Anwendung nur gelesen.
 * </p><p>
 * Ein TemplateFile ist eine XML-Datei mit einem &lt;SVG&gt;-Tag und dessen Unterstruktur.
 * Alle TemplateFiles sind als Resource in der Anwendung enthalten.
 * </p>
 * @author bk
 *
 */
public class TemplateFile {
	
	private static final boolean XSD_VALIDATE_OPTION = false;
	private static final boolean DTD_VALIDATE_OPTION = false;

	Logger logger = Logger.getLogger(TemplateFile.class.getName());

	private String filename = "";
	public  String getFileName() { return filename; }
	public  void   setFileName(String value) { filename=value; }
	
	/**
	 * Konstruktor mit Filename.
	 * 
	 * @param filename
	 * @throws TechnicalException 
	 */
	public TemplateFile(String filename, TemplateSet templateSet) throws TechnicalException {
		
		this.filename = filename;
		
		Document xmlDocument = load();
		parseXml(xmlDocument, templateSet);
	}

	/**
	 * <p>
	 * Lädt das shape-set aus einer XML-Datei.
	 * </p>
	 * @throws TechnicalException - bei allen Fehlern
	 */
	private Document load() throws TechnicalException {
		
		Document xmlDocument = null;
		try {
			// XML-Datei in DOM-Struktur parsen
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
		    dbf.setValidating(DTD_VALIDATE_OPTION || XSD_VALIDATE_OPTION);
			DocumentBuilder db = dbf.newDocumentBuilder(); 
			
			InputStream in = this.getClass().getResourceAsStream(filename); 
			if (in == null) {
				this.getClass().getClassLoader();
				in = ClassLoader.getSystemResourceAsStream(filename);
				if (in == null) {
					throw new TechnicalException("Resource '"+filename+"' not found!");
				}
			}
			xmlDocument = db.parse(in);
			return xmlDocument;
			
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
	 * <p>
	 * Interpretiert die XML-Struktur des <shape-set>-Tag.
	 * </p>
	 * @param doc
	 * @throws TechnicalException
	 */
	private void parseXml(org.w3c.dom.Document doc, TemplateSet templateSet) 
			throws TechnicalException {
		
		Element domElement = doc.getDocumentElement();
		
		if (domElement.getLocalName().equals("shape-set")) {
			
			// Attribute des Shape-Set auswerten
			parseAttributes(domElement, templateSet);
			
			// alle untergeordneten Elemente parsen
			parseChildNodes(domElement, templateSet);
			
		} else {
			throw new TechnicalException("Invalid XML-Structur: Root element not found.");
		}
	}
	
	/**
	 * Analysiert die XML-Child-Notes.
	 * 
	 * @throws TechnicalException
	 */
	private void parseChildNodes(Element domElement, TemplateSet templateSet) throws TechnicalException {
		
		NodeList children = domElement.getChildNodes();
		for (int i=0; i<children.getLength(); i++) {
			Node child = children.item(i);
			
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				Element elem = (Element)child;
				
				switch (elem.getLocalName()) {
				case "svg":
					parseSVGSVGElement(elem, templateSet);
					break;
					
				default:
					throw new DOMException(DOMException.NOT_SUPPORTED_ERR, 
						"Tag <"+elem.getLocalName()+"> not allowed in <shape-set>!");
				}
				
			} else if (child.getNodeType() == Node.TEXT_NODE) {
				Text elem = (Text)child;
				
				if (elem.getLocalName() != null) {
					switch (elem.getLocalName()) {
					case "desc":
						templateSet.setDescription(elem.getTextContent());
						break;
						
					default:
						throw new DOMException(DOMException.NOT_SUPPORTED_ERR, 
							"Tag <"+elem.getLocalName()+"> not allowed in <shape-set>!");
					}
				}
			} else {
				throw new TechnicalException("Invalid node type below shape-set! ");
			}
		}
	}
	
	/**
	 * Analysiert die XML-Attribute des TemplateSet.
	 * 
	 * @throws TechnicalException
	 */
	private void parseAttributes(Element domElement, TemplateSet templateSet) throws TechnicalException {
		NamedNodeMap attributes = domElement.getAttributes();
		for (int i=0; i<attributes.getLength(); i++) {
			Node node = attributes.item(i);
			if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
				Attr attr = (Attr)node;
				switch (attr.getName()) {
				case "id":
					templateSet.setId(attr.getValue());
					break;
					
				case "name":
					templateSet.setName(attr.getValue());
					break;
					
				case "xmlns":
					// TODO: was ist damit zu tun?
					break;
					
				default:
					throw new TechnicalException("Invalid attribute in shape-set tag: "+attr.getName());
				}
			}
		}
	}
	
	/**
	 * Analysiert ein Child-SVG-Element.
	 * 
	 * @param elem
	 * @throws TechnicalException
	 */
	private void parseSVGSVGElement(Element elem, TemplateSet templateSet) 
			throws TechnicalException {
		
		SVGSVGElementImpl svgElement = new SVGSVGElementImpl(elem);
		templateSet.add(svgElement);
		svgElement.parseXml(elem);
	}
}
