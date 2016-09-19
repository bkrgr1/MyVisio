package de.bkroeger.myvisio.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.bkroeger.myvisio.shapes.IShape;
import de.bkroeger.myvisio.shapes.ShapeElement;
import de.bkroeger.myvisio.utility.BusinessException;
import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * <p>
 * Ein ShapeSet ist eine Sammlung von Shapes zu einem bestimmten Thema.
 * Es gibt keine Informationen, wie die Shapes im Navigator angezeigt werden sollen.
 * Jedes Shape kann als ganzes auf eine Zeichnungsseite kopiert werden.
 * </p><p>
 * Wird im StartDialog ein Beispiel ausgewählt und geladen, werden die angegebenen ShapeSets 
 * geladen.
 * </p><p>
 * Wird ein leeres Dokument ausgewählt wird, wird mindestens das Standard-ShapeSet geladen.
 * </p><p>
 * Weitere ShapeSets können dynamisch nachgeladen werden. 
 * Sie werden dann zum Workbook hinzu gefügt.
 * </p><p>
 * Die vorgegebenen ShapeSets sind als Resource in der Anwendung enthalten.
 * Weitere ShapeSets können über die Preferences-Datei definiert,
 * vom Benutzer ausgewählt und aus den angegebenen Dateien nachgeladen werden.
 * </p>
 * @author bk
 */
public class ShapeSet {
	
	private static final boolean XSD_VALIDATE_OPTION = false;
	private static final boolean DTD_VALIDATE_OPTION = false;
	
	private static final Logger logger = Logger.getLogger(ShapeSet.class.getName());

	/**
	 * Name des ShapeSet
	 */
	private String name;
	/**
	 * @return - Name des ShapeSet
	 */
	public  String getName() { return this.name; }
	
	/**
	 * Flag, ob das ShapeSet bereits geladen wurde.
	 */
	private boolean isLoaded = false;
	
	/**
	 * Pfad zum ShapeSet-File
	 */
	private String filePath;
	/**
	 * @return - Pfad zur ShapeSet-Datei
	 */
	public  String getFilePath() { return  filePath; }
	/**
	 * Setzt den Pfad zur Datei
	 * @param filePath - Dateipfad
	 * @throws IllegalArgumentException wenn der Parameter leer ist
	 */
	public  void setFilePath(String filePath) throws IllegalArgumentException {
		
		if (filePath == null || filePath.isEmpty()) 
			throw new IllegalArgumentException("Parameter 'filePath' missing!");
		
		this.filePath = filePath;
	}
	
	/**
	 * Anzahl Referenzen auf das ShapeSet.
	 */
	private int referenceCount = 0;
	/**
	 * @return - den Wert des Referenz-Count
	 */
	public  int getReferenceCount() { return this.referenceCount; }
	/**
	 * 
	 */
	public  void increaseReferenceCount() { this.referenceCount++; }
	/**
	 * 
	 */
	public  void decreaseReferenceCount() { this.referenceCount--; }
	
	/**
	 * Liste der Shapes in diesem ShapeSet
	 */
	private List<IShape> shapes = new ArrayList<IShape>();
	/**
	 * @return - Anzahl Shapes
	 */
	public  int getShapesSize() { return shapes.size(); }
	/**
	 * @return - Iterator
	 */
	public  Iterator<IShape> getShapeIterator() { return shapes.iterator(); }
	/**
	 * @param index
	 * @return - das Shape an der gesuchten Position
	 */
	public  IShape getShapeAt(int index) { 
		if (index < 0 || index >= shapes.size()) {
			throw new IndexOutOfBoundsException("Index out of bound.");
		}
		return shapes.get(index);
	}
	
	private static ResourceBundle props = null;

	/**
	 * Konstruktor 1
	 * 
	 * @param name - Name des ShapeSet
	 */
	public ShapeSet(String name) {
		
		this.name = name;
		this.isLoaded = false;
	}

	/**
	 * Konstruktor 2
	 * 
	 * @param name - Name des ShapeSet
	 * @param filePath - Pfad zur Datei
	 */
	public ShapeSet(String name, String filePath) {
		
		this.name = name;
		this.filePath = filePath;
		this.isLoaded = false;
	}
	
	/**
	 * <p>
	 * Sucht ein ShapeSet mit dem gegebenen Namen in den programminternen
	 * Resourcen und in den ShapeSet-Verzeichnissen, die in den Preferenzen
	 * definiert sind.
	 * </p><p>
	 * Für die Suche in den Resourcen wird eine Properties-Datei gelesen,
	 * die zu dem ShapeSet-Namen den Resource-Pfad enthält.
	 * </p>
	 * @param name
	 * @return - ein ShapeSet
	 * @throws BusinessException 
	 * @throws TechnicalException 
	 */
	public static ShapeSet getShapeSet(String name) throws BusinessException, TechnicalException {
		
		// Liste der internen ShapeSet-Resourcen laden
		if (props == null) {
			props = ResourceBundle.getBundle("shapesets", Locale.ENGLISH);
		}
		
		// ShapeSet in den internen Resources suchen
		String filePath = "";
		try {
			String refName = props.getString(name);
			
			if (refName.startsWith("/resources/")) {
				URL url = ShapeSet.class.getResource(refName);
				if (url != null) {
					filePath = url.getPath().substring(1);
				} else {
					throw new TechnicalException("No file URL found for '"+refName+"'!");
				}
			}
			
		} catch(MissingResourceException e) {
			
			// ShapeSet in den external Folders suchen
			ApplicationModel application = ApplicationModel.getApplicationModel();
			Preferences preferences = application.getPreferences();
			if (preferences.getNumberOfShapeSetFolders() > 0) {
				
				for (String folderPath : preferences.getShapeSetFolders()) {
					
					if (!folderPath.endsWith(File.pathSeparator)) {
						folderPath += File.pathSeparator;
					}
					
					// Name an den Pfad anfügen
					String path = folderPath + name;
					File file = new File(path);
					if (file.exists()) {
						// wenn die Datei existiert, kann das ShapeSet eingelesen werden
						filePath = path;
						break;
					}
				}
			}
		}
		
		// wenn es keine Datei gibt, Fehler ausgeben
		if (filePath == null) {
			throw new BusinessException("ShapeSet '"+name+"' not found!");
		}
		
		ShapeSet shapeSet = new ShapeSet(name, filePath);
		shapeSet.load();
		return shapeSet;
	}
	
	/**
	 * @throws TechnicalException
	 * @throws BusinessException
	 */
	public void load() throws TechnicalException, BusinessException {
		
		if (filePath == null || filePath.isEmpty()) {
			throw new TechnicalException("File path for ShapeSet missing!");
		}
		
		try {
			File file = new File(filePath);
			FileInputStream inStream = new FileInputStream(file);
			
			load(inStream);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new TechnicalException(e.getMessage(), e);
		}
	}
	
	/**
	 * Lädt das ShapeSet aus der Datei
	 * @param in - Input-Stream
	 * @throws TechnicalException 
	 * @throws BusinessException 
	 */
	public void load(InputStream in) throws TechnicalException, BusinessException {
		
		if (!isLoaded) {
			
			try {
				
				// XML-Datei in DOM-Struktur parsen
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				dbf.setNamespaceAware(true);
			    dbf.setValidating(DTD_VALIDATE_OPTION || XSD_VALIDATE_OPTION);
				DocumentBuilder db = dbf.newDocumentBuilder(); 

				// DOM-Struktur füllen
				Document doc = db.parse(in);
				
				// Struktur analysieren
				parseXml(doc);
				
				isLoaded = true;
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new TechnicalException(e.getMessage(), e);
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
				throw new TechnicalException(e.getMessage(), e);
			} catch (SAXException e) {
				e.printStackTrace();
				throw new TechnicalException(e.getMessage(), e);
			} catch (IOException e) {
				e.printStackTrace();
				throw new TechnicalException(e.getMessage(), e);
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
					// ignore exception
				}
			}
		}
	}
	
	/**
	 * <p>
	 * Interpretiert die XML-Struktur des ShapeSets
	 * </p>
	 * @param doc - ein W3C-DOM-Document
	 * @throws TechnicalException 
	 */
	private void parseXml(Document doc) throws TechnicalException {
		
		// das Hauptelement lesen
		Element docElem = doc.getDocumentElement();
		if (docElem.getLocalName().equals("shapeset")) {
			
			// das (einzige) <shapes>-Tag lesen
			NodeList shapesNode = docElem.getElementsByTagName("shapes");
			if (shapesNode.getLength() == 1) {
			
				// die Liste der <shape>-Tags bearbeiten
				NodeList children = shapesNode.item(0).getChildNodes();
				for (int i=0; i<children.getLength(); i++) {
					Node child = children.item(i);
					
					// nur Element-Nodes verarbeiten
					if (child.getNodeType() == Node.ELEMENT_NODE) {
						
						// <shape>-Tag
						if (child.getLocalName().equals("shape")) {
							
							// ein ShapeElement erstellen und parsen
							ShapeElement shape = new ShapeElement();
							shape.parseXml((Element) child);
							shapes.add(shape);
							
						} else {
							throw new TechnicalException("Invalid tag found in shapes collection: "+child.getLocalName());
						}
					}
				}
			} else if (shapesNode.getLength() > 1) {
				throw new TechnicalException("Multiple <shapes> tags!");
			} else {
				throw new TechnicalException("<shapes> tag not found!");
			}
		} else {
			logger.warning("Invalid XML-Structur: Root element not found.");
			throw new TechnicalException("Invalid XML-Structur: Root element not found.");
		}
	}
	
	/**
	 * @param doc
	 * @param docElem
	 */
	public void convertToXml(Document doc, Element docElem) {
		
	}
}
