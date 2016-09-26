package de.bkroeger.myvisio.model;

import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import de.bkroeger.myvisio.controller.ApplicationController;
import de.bkroeger.myvisio.controller.commands.CommandArgument;
import de.bkroeger.myvisio.shapes.DrawingCategory;
import de.bkroeger.myvisio.shapes.ShapeDef;
import de.bkroeger.myvisio.shapes.TemplateSet;
import de.bkroeger.myvisio.utility.ListeningMap;
import de.bkroeger.myvisio.utility.ShapeCache;
import de.bkroeger.myvisio.utility.TechnicalException;
import de.bkroeger.myvisio.view.AbstractStatusDisplay;

/**
 * <p>
 * Dies ist die Hauptklasse der Anwendung.
 * </p><p>
 * Wenn kein Dateiname beim Aufruf angegeben wird, wird ein Start-Dialog angezeigt,
 * in dem man eine bestehende Datei auswählen oder eine neue Datei anhand der 
 * verschiedenen Kategorien erstellen.
 * </p>
 * @author bk
 */
public class ApplicationModel extends AbstractModel {

	@SuppressWarnings("unused")
	private static final String DEFAULT_SHAPE_SET_FILENAME = "resources/standard.xml";
	
	public static final int EXITCODE_UNCAUGHT_EXCEPTION = 1001;
	public static final int EXITCODE_TECHNICAL_EXECPTION = 1002;

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ApplicationModel.class.getName());
	
	private static ApplicationModel application = null;
	
	private static Locale currentLocale = Locale.GERMAN;
	public  static Locale getLocale() { return currentLocale; }

    private static String language = "DE-de";
    public  static String getLanguage() { return language; }
    
    private static String country;
    public  static String getCountry() { return country; }
    
    private static final int majorVersion = 0;
    private static final int minorVersion = 3;
    private static final int modification = 13;
    public static String getVersion() {
    	return ""+majorVersion+"."+minorVersion+"."+modification;
    }
    
    /**
     * Liste der offenen WorkBooks im MainCanvas.
     * Eines davon ist das currentDocument.
     */
    private ListeningMap<String, Workbook> documentsMap = new ListeningMap<String, Workbook>();
    public  ListeningMap<String, Workbook> getDocuments() {
    	return documentsMap;
    }
    
    public  void addDocument(Workbook workbook) {
    	documentsMap.put(workbook.getTitle(), workbook);
    }
    
    /**
     * Das currentDocument ist das derzeit angezeigte Document im MainCanvas.
     * Beim ändern des sichtbaren Tabs wird auch das currentDocument angepasst.
     */
    private Workbook currentDocument;
    public  Workbook getCurrentDocument() { return currentDocument; }
    public  void setCurrentDocument(Workbook value) { currentDocument = value; }
    
	private Category category;
	public  Category getCategory() { return category; }
    public  void setCategory(Category category) {
    	this.category = category;
    	
    	if (category != null) {
    		createWorkbook(category);
    	}
    }
    
    private String fileName;
    public  String getFileName() { return this.fileName; }
    public  void setFileName(String value) {
    	this.fileName = value;
    	
    	if (fileName != null && !fileName.isEmpty()) {
    		// angegebene Datei öffnen
    		openWorkbook(this.fileName);
    	}
    }
    
	private List<String> posParams = new ArrayList<String>();
	/**
	 * @return
	 */
	public int getPositionalParamsLength() { return posParams.size(); }
	/**
	 * @param index
	 * @return
	 */
	public String getPositionalParamAt(int index) {
		if (index >= 0 && index < posParams.size()) {
			return posParams.get(index);
		} else {
			return null;
		}
	}
	/**
	 * @param posParams
	 */
	public void setPosParams(List<String> posParams) {
		if (posParams != null) this.posParams = posParams;
	}
	
	private Map<String, String> keyParams = new HashMap<String, String>();
	/**
	 * @return
	 */
	public int getKeywordParamsLength() { return keyParams.size(); }
	/**
	 * @param key
	 * @return
	 */
	public String getKeywordParam(String key) {
		return keyParams.get(key);
	}
	/**
	 * @param keyParams
	 */
	public void setKeyParams(Map<String, String> keyParams) {
		if (keyParams != null) this.keyParams = keyParams;
	}

    
    private ShapeCache shapeCache = new ShapeCache();
    public  ShapeDef getShapeDefByName(String name) { return shapeCache.getShapeByName(name); }
    public  ShapeDef getShapeDefByUUID(UUID uuid)   { return shapeCache.getShapeByUUID(uuid); }
    
    
    private Map<String, ShapeSet> shapeSets = new HashMap<String, ShapeSet>();
    public Collection<ShapeSet> getShapeSets() { return shapeSets.values(); }
    public void setShapeSets(Collection<ShapeSet> value) {
    	shapeSets.clear();
    	for (ShapeSet ss : value) {
    		shapeSets.put(ss.getName(), ss);
    	}
    }
	public void addShapeSet(ShapeSet shapeSet) {
		shapeSets.put(shapeSet.getName(), shapeSet);
	}
	public boolean containsShapeSet(String name) {
		return shapeSets.containsKey(name);
	}
	public ShapeSet getShapeSet(String name) {
		return shapeSets.get(name);
	}
    
    
    private ApplicationController controller;
    public ApplicationController getMainController() { return controller; }
//    public void setMainController(MainController value) { controller = value; }
    
    private Map<String, DrawingCategory> categoriesMap = new HashMap<String, DrawingCategory>();
    public  DrawingCategory getCategory(String name) { return categoriesMap.get(name); }
    
    private Map<String, TemplateSet> templatesMap = new HashMap<String, TemplateSet>();
    public  TemplateSet getTemplateSet(String name) { return templatesMap.get(name); }
    
    private CommandArgument argument;
	public void setCommandArgument(CommandArgument argument) { this.argument = argument; }
	public CommandArgument getCommandArgument() { return this.argument; }
	
	private Preferences preferences;
	/**
	 * @return - Preferences
	 */
	public Preferences getPreferences() {
		return this.preferences;
	}
	
	private String frameTitle = "Bertholds Visio";
	public String getFrameTitle() { return this.frameTitle; }
	public void setFrameTitle(String newValue) {
		String oldValue = this.frameTitle;
		this.frameTitle = newValue;
		firePropertyChange("frameTitle", oldValue, newValue);
	}
	
	private JPanel mainPanel;
	public void setMainPanel(JPanel newValue) {
		JPanel oldValue = mainPanel;
		this.mainPanel = newValue;
		firePropertyChange("mainPanel", oldValue, newValue);
	}
	
	public  String getWorkbookName() { return fileName; }
	public  void   setWorkbookName(String fileName) { 
		
		// wurde ein Dateiname angegeben und ist gültig?
		if (fileName != null && !fileName.isEmpty()) {
			File file = new File(fileName);
			if (file.exists() == false) {
				// TODO: Fehler im Statusbereich anzeigen
				
			} else fileName = null;
		} else fileName = null;
		
		// wurde ein Dateiname angegeben?
		if (fileName != null) {
			// Workbook öffnen!
			setFileName(fileName);
		}
	}

	private AbstractStatusDisplay statusDisplay;
	public void setStatusDisplay(AbstractStatusDisplay newValue) {
		AbstractStatusDisplay oldValue = statusDisplay;
		statusDisplay = newValue;
		this.firePropertyChange("statusDisplay", oldValue, newValue);
	}
	
	/**
	 * 
	 * @param msg
	 */
	public void putStatusMessage(String msg) {
		this.controller.putStatusMessage(msg);
	}
	
	/**
	 * @return
	 */
	public static ApplicationModel getApplicationModel() {
		if (application == null)
			application = new ApplicationModel();
		return application;
	}
	
	/**
	 * @param keyParams
	 * @param posParams
	 * @return
	 */
	public static ApplicationModel getApplicationModel(Map<String, String> keyParams, List<String> posParams) {
		if (application == null)
			application = new ApplicationModel();
		
		application.keyParams = keyParams;
		application.posParams = posParams;
		
		if (posParams.size() > 0) {
			String filename = posParams.get(0);
			File file = new File(filename);
			if (file.exists()) {
				application.fileName = filename;
				
				Workbook workbook = null;
				try {
					workbook = new Workbook(filename);
//					workbook.load(file);
				} catch(TechnicalException e) {
					e.printStackTrace();
					logger.severe(e.getMessage());
				}

				application.documentsMap.put(filename, workbook);
			}
		}
		
		return application;
	}
	
	public static ResourceBundle getBundle() {

        return ResourceBundle.getBundle("Messages", currentLocale);
	}
	
	/**
	 * Privater Konstruktor für die VisioApplication.
	 * @throws TechnicalException 
	 */
	ApplicationModel() {

		addPropertyChangeListener(controller);
		
		// Preferences-Datei lesen
		this.preferences = new Preferences();
		this.preferences.load();
	}
	
	public void buildLocale() {

		// TODO: Font aus den Preferences lesen
		setUIFont (new FontUIResource("Arial", Font.PLAIN, 14));
		// TODO: language und country aus den Preferences lesen
		if (country != null)
			currentLocale = new Locale(language, country);
		else
			currentLocale = new Locale(language);
	}
	
	/**
	 * Öffnet ein Workbook aus der angegebenen Datei.
	 * @param fileName
	 */
	private void openWorkbook(String fileName) {
		
		// TODO: ein Workbook aus der angegebenen Datei laden
	}
	
	/**
	 * Erzeugt ein neues Workbook der angegebenen Kategorie.
	 * @param category
	 */
	private void createWorkbook(Category category) {
		
		// TODO: ein Workbook für die angegebene Category erstellen
	}
	
	public static void setUIFont (javax.swing.plaf.FontUIResource f){
		
	    @SuppressWarnings("rawtypes")
		java.util.Enumeration keys = UIManager.getDefaults().keys();
	    while (keys.hasMoreElements()) {
	      Object key = keys.nextElement();
	      Object value = UIManager.get (key);
	      if (value != null && value instanceof javax.swing.plaf.FontUIResource)
	        UIManager.put (key, f);
	      }
	    } 
	
	/**
	 * Beendet die Verarbeitung der Anwendung.
	 */
	public void terminate() {
		
		// Preferences speichern
		preferences.store();
	}
}
