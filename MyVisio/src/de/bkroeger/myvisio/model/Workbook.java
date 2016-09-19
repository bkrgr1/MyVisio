package de.bkroeger.myvisio.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.bkroeger.myvisio.shapes.IShape;
import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * <p>
 * Die WorkBook-Klasse repräsentiert ein MyVisio-Dokument bestehend aus 
 * mehreren Seiten ({@link Worksheet}) auf denen jeweils diverse Shapes ({@link IShape}) 
 * angeordnet sind.
 * </p><p>
 * Ein WorkBook wird durch seinen voll qualifizierten Dateinamen bezeichnet
 * und durch eine UUID identifiziert.
 * </p><p>
 * Ein Workbook besteht aus ein oder mehreren {@link Worksheet Worksheets}.
 * Ein Workbook enthält ein oder mehrere {@link ShapeSet ShapeSets}.
 * </p><p>
 * Gespeichert werden WorkBooks als XML-Dateien.
 * </p><p>
 * Es gibt zwei Arten von Workbooks:</p>
 * <ol>
 * <li>Standard-Workbooks, die eine Zeichnung eines Benutzers speichern.
 * Dabei werden die benutzten Shapes nur als Referenzen (UUID) gespeichert.
 * </ol>
 * <li>Example-Workbooks, die neben einer Zeichnung auch eine Liste der erforderlichen 
 * ShapeSets enthalten. Die ShapeSets selber sind dabei auch nur referenziert.</ol>
 * </ol>
 * @invariant 
 * <ul>
 * <li>uuid ist unveränderlich</li>
 * </ul>
 * @pre
 * @post
 * <ul>
 * <li>Dirty-Flag ist gesetzt, wenn ein Attribut des Workbooks oder eines Worksheets geändert wurde.</li>
 * </ul>
 * @author bk
 */
public class Workbook {
	
	private static final String SETS_TAG = "shapesets";
	private static final String TITLE_TAG = "title";
	private static final String PAGE_TAG = "page";
	private static final String DESC_TAG = "desc";
	private static final String SHAPE_SETS_TAG = "shape-sets";
	private static final String PAGES_TAG = "pages";
	private static final String DOCUMENT_TAG = "myvisio";

	private static Logger logger = Logger.getLogger(Workbook.class.getName());
	
	/**
	 * Die interne UUID des Workbook
	 */
	private UUID uuid;
	/**
	 * @return - die UUID des Dokuments
	 */
	public UUID getUUID() {
		return uuid;
	}
	
	/**
	 * Der Dateipfad (Dateiname plus Pfad).
	 */
	private String filepath = "";
	/**
	 * @return - der Dateipfad
	 */
	public String getFileName() { return filepath; }
	/**
	 * @param fileName
	 * @throws TechnicalException
	 */
	public void setFileName(String fileName) throws TechnicalException {
		if (fileName == null || fileName.isEmpty()) {
			throw new TechnicalException("File name missing or empty!");
		}
		this.filepath = fileName;
		// wichtig: Setter verwenden, weil dieser wichtige Funktionen enthält
		this.setTitle(new File(fileName).getName());
	}
	
	/**
	 * Der Titel des Dokuments (Dateiname ohne Pfad).
	 */
	private String title = "";
	/**
	 * @return - der Titel
	 */
	public String getTitle() { return title; }
	/**
	 * @param value
	 * @throws TechnicalException
	 */
	public void setTitle(String value) throws TechnicalException { 
		if (value == null || value.isEmpty()) {
			throw new TechnicalException("Title missing or empty!");
		}
		title = value; 
		// alle Seiten informieren
		for (Worksheet sheet : worksheets) {
			sheet.refreshTitle();
		}
	}
	
	private boolean dirtyFlag = false;
	/**
	 * Liefert das Dirty-Flag. Wenn eine der Seiten "schmutzig" ist,
	 * gilt die ganze Datei als schmutzig.
	 * 
	 * @return - true, wenn wenigstens eine Seite verändert wurde
	 */
	public boolean isDirty() {
		boolean sheetDirty = false;
		for (Worksheet sheet : worksheets) {
			if (sheet.isDirty()) {
				sheetDirty = true;
				break;
			}
		}
		return (dirtyFlag || sheetDirty);
	}
	
	/**
	 * Liste der WorkSheets
	 */
	private List<Worksheet> worksheets = new ArrayList<Worksheet>();
	/**
	 * @return - die Anzahl WorkSheets
	 */
	public int getWorksheetSize() { return worksheets.size(); }
	/**
	 * @return - WorkSheet-Iterator
	 */
	public Iterator<Worksheet> getWorksheetIterator() {
		return worksheets.iterator();
	}
	
	/**
	 * @return - das aktuelle WorkSheet
	 */
	public Worksheet getCurrentWorksheet() {
		if (currentPageUUID == null) return null;
		for (Worksheet sheet : worksheets) {
			if (sheet.getUuid() == currentPageUUID) return sheet;
		}
		return null;
	}
	
	/**
	 * die aktuelle Seite
	 */
	private UUID currentPageUUID = null;
	/**
	 * @return - die UUID der aktuellen Seite
	 */
	public  UUID getCurrentPageUUID() { return currentPageUUID; }
	
	/**
	 * Map der ShapeSets
	 */
	private Map<String, ShapeSet> shapeSets = new HashMap<String, ShapeSet>();
	/**
	 * @return - Liste der ShapeSets
	 */
	public  Collection<ShapeSet> getShapeSets() {
		return this.shapeSets.values();
	}
	/**
	 * @return - Anzahl der ShapeSets
	 */
	public int getShapeSetSize() { return shapeSets.size(); }
	
	/**
	 * Beschreibung
	 */
	private String description = "";
	/**
	 * @return - Beschreibung
	 */
	public  String getDescription() { return description; }
	
	/**
	 * <p>
	 * Konstruktor für leeres/neues Worksheet.
	 * </p><p>
	 * Für ein neues Dokument wird ein Titel vergeben,
	 * das Page-Set initialisiert 
	 * und eine erste leere Seite erzeugt. Dies ist die aktuelle/Default-Seite.
	 * Der Filename bleibt leer.
	 * </p>
	 * @throws TechnicalException 
	 */
	public Workbook() throws TechnicalException {
		
		this.setTitle("New Document");
		this.dirtyFlag = false;
		this.uuid = UUID.randomUUID();
		worksheets.add(Worksheet.createDefaultWorksheet(this));
	}

	/**
	 * <p>
	 * Konstruktor mit Dateipfad. Der Pfad-String muss eine Datei mit
	 * Extension ".mvd" (myVisioDocument) oder ".xml" definieren.
	 * Die Datei muss existieren!
	 * </p><p>
	 * Als Titel wird der Name der Datei (ohne Pfad) verwendet.
	 * Das Page-Set wird initialisiert 
	 * und die Struktur des Dokumentes aus der XML-Datei eingelesen.
	 * </p>
	 * @param fileName - Pfad zur Document-XML-Datei
	 * @throws TechnicalException - wenn
	 * <ul>
	 * <li>der fileName null oder leer ist</li>
	 * <li>der fileName nicht die richtige Extension hat</li>
	 * <li>die angegebene Datei nicht existiert</li>
	 * </ul>
	 */
	public Workbook(String fileName) throws TechnicalException {
		
		if (fileName == null || fileName.isEmpty()) {
			throw new TechnicalException("Filename empty");
		}
		if (fileName.endsWith(".xml") == false && fileName.endsWith(".mvd") == false) {
			throw new TechnicalException("Invalid file type! Path="+fileName);
		}
		
		File file = new File(fileName);
		if (!file.exists()) {
			throw new TechnicalException("File '"+fileName+"' does not exist!");
		}
		
		// File-Name speichern
		this.filepath = fileName;
		// Title setzen
		this.setTitle(file.getName());
		// Workbook ist unverändert
		this.dirtyFlag = false;
		
		// Workbook laden
		this.load(file);
		
		// wenn kein Worksheet geladen wurde, Default-Worksheet hinzufügen
		if (this.getWorksheetSize() == 0) {
			this.addWorksheet(Worksheet.createDefaultWorksheet(this));
		}
	}
	
	/**
	 * <p>
	 * Speichert die Document-Hierarchie in einer XML-Datei.
	 * </p>
	 * @throws TechnicalException 
	 */
	public void store() throws TechnicalException {
		
		try {

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder(); 
			
			// ein XML-Dokument erstellen
			org.w3c.dom.Document doc = db.newDocument();
			// und mit den Daten der Visio-Struktur füllen
			convertToXml(doc);

			// einen Transformer erstellen
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			// und Optionen setzen
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			
			// die XML-Struktur in die Datei schreiben
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(this.filepath));
			transformer.transform(source, result);	
			
			logger.info("File '"+this.filepath+"' stored.");
			
			// das Dirty-Flag in allen Seiten löschen
			for (Worksheet sheet : worksheets) {
				sheet.clearDirty();
			}
			this.dirtyFlag = false;
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			logger.severe("ParserConfiguration: "+e.getMessage());
			throw new TechnicalException("ParserConfiguration: "+e.getMessage(), e);
			
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
			logger.severe("TransformerConfiguration: "+e.getMessage());
			throw new TechnicalException("TransformerConfiguration: "+e.getMessage(), e);
			
		} catch (TransformerException e) {
			e.printStackTrace();
			logger.severe("Transformer: "+e.getMessage());
			throw new TechnicalException("Transformer: "+e.getMessage(), e);
		}
	}
	
	/**
	 * <p>
	 * Lädt die Document-Struktur aus der angegebenen XML-Datei.
	 * </p>
	 * @param file - XML-File
	 * @throws TechnicalException 
	 */
	public void load(File file) throws TechnicalException {
		
		logger.info("Loading WorkBook '"+file.getAbsolutePath()+"'.");
		
		InputStream inStream = null;
		try {
			inStream = new FileInputStream(file);
			load(inStream);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(), e);
			
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
					inStream = null;
				} catch (IOException e) {
					e.printStackTrace();
					logger.severe(e.getMessage());
				}
			}
		}
	}
	
	/**
	 * Lädt die Document-Struktur aus dem Input-Stream.
	 * 
	 * @param in
	 * @throws TechnicalException
	 */
	public void load(InputStream in) throws TechnicalException {
		
		boolean dtdValidate = false;
		boolean xsdValidate = false;
		
		try {
			// XML-Datei in DOM-Struktur parsen
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
		    dbf.setValidating(dtdValidate || xsdValidate);
		    
			DocumentBuilder db = dbf.newDocumentBuilder(); 
			org.w3c.dom.Document doc = db.parse(in);
			
			// DOM-Struktur in Visio-Hierarchie umwandeln
			this.parseXml(doc);
			
		} catch(ParserConfigurationException e) {
			e.printStackTrace();
			logger.severe("Error parsing XML-Document from Stream: "+e.getMessage());
			throw new TechnicalException("Error parsing XML-Document from Stream", e);
			
		} catch (SAXException e) {
			e.printStackTrace();
			logger.severe("Error processing XML-Document from Stream: "+e.getMessage());
			throw new TechnicalException("Error processing XML-Document from Stream", e);
			
		} catch (IOException e) {
			e.printStackTrace();
			logger.severe("Error reading XML-Document from Stream: "+e.getMessage());
			throw new TechnicalException("Error reading XML-Document from Stream", e);
		}
	}
		
	/**
	 * <p>
	 * Konvertiert eine DOM-Struktur in die Visio-Document-Hierarchie
	 * </p>
	 * @param w3cDoc
	 * @throws TechnicalException 
	 */
	private void parseXml(org.w3c.dom.Document w3cDoc) throws TechnicalException {
		
		Element docElem = w3cDoc.getDocumentElement();
		if (docElem.getLocalName().equals(DOCUMENT_TAG)) {
			
			// TODO: alle Attribute analysieren
			
			// alle untergeordneten Elemente analysieren
			NodeList children = docElem.getChildNodes();
			for (int i=0; i<children.getLength(); i++) {
				Node child = children.item(i);
				
				// Element-Nodes analysieren
				if (child.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element)child;
					
					switch (elem.getLocalName()) {
					case PAGES_TAG:
						parsePagesTag(elem);
						break;
					case SHAPE_SETS_TAG:
						parseShapeSets(elem);
						break;
					case DESC_TAG:
						parseDescription(elem);
						break;
					default:
						throw new TechnicalException("Unexpected node: "+elem.getLocalName());
					}
					
				// Text-Nodes analysieren
				} else if (child.getNodeType() == Node.TEXT_NODE) {
					
					switch (child.getNodeName()) {
					case TITLE_TAG:
						parseTitle(child);
						break;
					case "#text":
						break;
					default:
						throw new TechnicalException("Unexpected node: "+child.getNodeName());
					}
					
				} else {
					throw new TechnicalException("Unexpected node type.");
				}
			}
			
		} else {
			logger.warning("Invalid XML-Structur: Root element not found.");
			throw new TechnicalException("Invalid XML-Structur: Root element not found.");
		}
	}
	
	/**
	 * Ein ShapeSet analysieren.
	 * 
	 * @param set
	 * @throws TechnicalException
	 */
	private void parseShapeSets(Element set) throws TechnicalException {
		
		// TODO: ShapeSets analysieren
	}
	
	/**
	 * Desc-Tag analysieren
	 * 
	 * @param elem
	 */
	private void parseDescription(Element elem) {
		
		this.description = elem.getTextContent();
	}
	
	/**
	 * Title-Tag analysieren
	 * 
	 * @param desc
	 */
	private void parseTitle(Node desc) {
		
		// TODO: Title/Description analysieren
	}
	
	/**
	 * Page-Tag analysieren
	 * 
	 * @param pages
	 * @throws TechnicalException
	 */
	private void parsePagesTag(Element pages) throws TechnicalException {
		
		NodeList children = pages.getChildNodes();
		for (int i=0; i<children.getLength(); i++) {
			Node child = children.item(i);
			
			if (child.getNodeType() == Node.ELEMENT_NODE) {
				Element elem = (Element)child;
				
				switch (elem.getLocalName()) {
				case PAGE_TAG:
					Worksheet sheet = new Worksheet(this);
					sheet.parseXml(elem);
					break;
					
				default:
					throw new TechnicalException("Unexpected node: "+elem.getLocalName());
				}
			}
		}
	}
	
	/**
	 * Konvertiert die Daten in eine DOM-Struktur.
	 * 
	 * @param w3cDoc
	 * @throws TechnicalException
	 */
	private void convertToXml(org.w3c.dom.Document w3cDoc) throws TechnicalException {
		
		// Visio-Tag für das Dokument erstellen
		Element docElem = w3cDoc.createElement(DOCUMENT_TAG);
		w3cDoc.appendChild(docElem);
		
		if (this.title != null && !this.title.isEmpty()) {
			
			// Title-Tag
			Element titleElem = w3cDoc.createElement(TITLE_TAG);
			titleElem.setTextContent(this.title);
			docElem.appendChild(titleElem);
		}
		
		// Sets-Tag
		Element setsElem = w3cDoc.createElement(SETS_TAG);
		docElem.appendChild(setsElem);
		
		// alle ShapeSets schreiben
		for (ShapeSet set : shapeSets.values()) {
			set.convertToXml(w3cDoc, setsElem);
		}
		
		// Pages-Tag
		Element pagesElem = w3cDoc.createElement(PAGES_TAG);
		docElem.appendChild(pagesElem);
		
		// alle Worksheets schreiben
		for (Worksheet sheet : worksheets) {
			sheet.convertToXml(w3cDoc, pagesElem);
		}
	}
	
	/**
	 * Speichert das ShapeSet im Workbook.
	 * 
	 * @param shapeSet
	 */
	public void addShapeSet(ShapeSet shapeSet) {
		
		this.shapeSets.put(shapeSet.getName(), shapeSet);
	}
	
	/**
	 * @param sheet
	 */
	public void addWorksheet(Worksheet sheet) {
		
		worksheets.add(sheet);
	}
}
