package de.bkroeger.myvisio.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import de.bkroeger.myvisio.utility.TechnicalException;
import de.bkroeger.myvisio.windows.WindowsServices;

/**
 * <p>
 * Anwendungsweite Properties.
 * Sie werden beim Start der Anwendung gelesen und beim Beenden gespeichert.
 * </p>
 * @author bk
 */
public class Preferences {
	
	private static final Logger logger = Logger.getLogger(Preferences.class.getName());
	private static final String APPLICATION_NAME = "MyVisio";
	private static final String PREF_FILE_NAME = "preferences.properties";
	
	// der Pfad zur Properties-Datei
	private String appFolderPath;
	
	// maximal Anzahl RecentFiles
	private int maxNumberOfRecentlyFiles = 10;
	
	// Verzeichnis der RecentFiles
	private List<String> mostRecentlyUsedFiles = new ArrayList<String>();
	
	// Properties-Objekt
	private Properties props = new Properties();
	
	private boolean isDirty = false;
	
	private List<String> shapeSetFolders = new ArrayList<String>();
	/**
	 * @return - Liste der ShapeSet-Folder
	 */
	public List<String> getShapeSetFolders() {
		return shapeSetFolders;
	}
	
	private int numberOfShapeSetFolders;
	/**
	 * @return - Anzahl ShapeSet-Folder
	 */
	public int getNumberOfShapeSetFolders() { return numberOfShapeSetFolders; }

	
	/**
	 * <p>
	 * Konstruktor
	 * </p>
	 */
	public Preferences() {
		
		try {
			// Pfad zum AppFolder ermitteln
			appFolderPath = WindowsServices.getAppFolderPath()+File.separatorChar+APPLICATION_NAME;
			File appFolderDir = new File(appFolderPath);
			// wenn das Unterverzeichnis noch nicht existiert, Verzeichnis anlegen
			if (appFolderDir.exists() == false) {
				appFolderDir.mkdir();
			}
			
		} catch (TechnicalException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <p>
	 * Speichert den übergebenen Dateinamen als erstes in der Liste der Dateien.
	 * Wenn der Dateiname schon vorhanden ist, wird er vorher gelöscht 
	 * und vorne wieder eingefügt.
	 * </p>
	 * @param filename
	 */
	public void setMostRecentlyUsedFile(String filename) {
		
		int index = -1;
		for (int i=0; i<mostRecentlyUsedFiles.size(); i++) {
			String name = mostRecentlyUsedFiles.get(i);
			if (name.equals(filename)) {
				index = i;
				break;
			}
		}
		if (index != -1) {
			mostRecentlyUsedFiles.remove(index);
		}
		mostRecentlyUsedFiles.add(0, filename);
	}
	
	/**
	 * Lädt die Preferences aus einer .properties-Datei.
	 */
	public void load() {
		
	    InputStream is = null;
		 
	    // First try loading from the appFolder directory
	    try {
	        File f = new File(appFolderPath + File.separatorChar + PREF_FILE_NAME);
	        is = new FileInputStream( f );
	    }
	    catch ( Exception e ) { 
	    	is = null; 
	    	logger.info("Preferences not found in app folder.");
	    }
	 
	    try {
	        if ( is == null ) {
	            // Try loading from classpath
	            is = getClass().getResourceAsStream(PREF_FILE_NAME);
	            isDirty = true;
	        }
	 
	        // Try loading properties from the file (if found)
	        props.load( is );
	        logger.info("Properties loaded.");
	   	 
	        // die Liste der "most recently used" Files
		    maxNumberOfRecentlyFiles = new Integer(props.getProperty("MaxRecentlyFiles", "10"));
		    int numFiles = new Integer(props.getProperty("NumRecentlyFiles", "0"));
		    mostRecentlyUsedFiles.clear();
		    for (int i=0; i<numFiles; i++) {
		    	// load the recent files
		    	String fn = props.getProperty("RecentFile_"+i, "");
		    	if (fn != null && !fn.isEmpty()) {
		    		
		    		mostRecentlyUsedFiles.add(fn);
		    	}
		    }
		    
		    // die Liste der ShapeSetFolder
		    numberOfShapeSetFolders = new Integer(props.getProperty("NumShapeSets", "0"));
		    shapeSetFolders.clear();
		    for (int i=0; i<numberOfShapeSetFolders; i++) {
		    	// load the recent files
		    	String fn = props.getProperty("ShapeSetFolder_"+i, "");
		    	if (fn != null && !fn.isEmpty()) {
		    		shapeSetFolders.add(fn);
		    	}
		    }
	    }
	    catch ( Exception e ) {
	    	logger.info("Preferences not found in classpath.");
	    }    
	}
	
	/**
	 * Speichert die Preferences als .properties-Datei
	 */
	public void store() {
		
	    try {
	    	if (isDirty) {
		        Properties props = new Properties();
		        props.setProperty("MaxRecentlyFiles", ""+maxNumberOfRecentlyFiles);
		        props.setProperty("NumRecentlyFiles", ""+mostRecentlyUsedFiles.size());
		        for (int i=0; i<mostRecentlyUsedFiles.size(); i++) {
		        	String fn = mostRecentlyUsedFiles.get(i);
		        	props.setProperty("RecentFile_"+i, fn);
		        }
		        
		        String filePath = appFolderPath + File.separatorChar + PREF_FILE_NAME;
		        File f = new File(filePath);
		        OutputStream out = new FileOutputStream( f );
		        Date today = new Date();
		        props.store(out, "MyViso Preferences. Last stored="+today.toString());
		        logger.info("Properties stored in "+filePath);
		        
		        isDirty = false;
	    	}
	    } catch (Exception e ) {
	        e.printStackTrace();
	    }	}
}
