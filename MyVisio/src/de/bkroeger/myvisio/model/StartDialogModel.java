package de.bkroeger.myvisio.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * <p>
 * Der Start-Dialog zeigt links eine Liste der verfügbaren Kategorien.
 * Beim Klick auf eine der Kategorien wird im Hauptteil eine Reihe von 
 * Vorlagen für die jeweilige Kategorie angezeigt.
 * </p><p>
 * Dieses Modell verweist auf die Liste der Kategorien.
 * </p>
 * @author bk
 */
public class StartDialogModel extends AbstractModel {
	
	/**
	 * Die sortierte Map der verfügbaren Categories.
	 */
	private SortedMap<String, Category> categoryMap = new TreeMap<String, Category>();

	/**
	 * Die sortierte Map der verfügbaren ShapeSets.
	 */
	private SortedMap<String, ShapeSet> shapeSetMap = new TreeMap<String, ShapeSet>();
	
	private ExampleWorkbook example;
	public  ExampleWorkbook getExample() { return example; }
	public void setCurrentExample(ExampleWorkbook example) {
		this.example = example;
	}

	/**
	 * Die ausgewählte Category.
	 */
	private Category selectedCategory;
	public void setSelectedCategory(Category newValue) { 
		Category oldValue = this.selectedCategory;
		this.selectedCategory = newValue; 
		if (newValue != null) {
			this.setExamplesList(newValue.getExamples());
		} else {
			this.setExamplesList(null);
		}
		this.firePropertyChange("SelectedCategory", oldValue, newValue);
	}
	public Category getSelectedCategory() { return this.selectedCategory; }
	
	/**
	 * Liste der Examples
	 */
	private List<ExampleWorkbook> exampleList;
	public void setExamplesList(List<ExampleWorkbook> newValue) {
		List<ExampleWorkbook> oldValue = this.exampleList;
		this.exampleList = newValue;
		this.firePropertyChange("ExampleList", oldValue, newValue);
	}

	/**
	 * Konstruktor. Erzeugt das StartDialog-Model und -Controller.
	 * 
	 * @throws TechnicalException 
	 */
	public StartDialogModel() throws TechnicalException {
		
		loadCategories();
		selectedCategory = categoryMap.get("Standard");
	}

	/**
	 * Liest die Category-Properties ein und erzeugt eine Map der Categories
	 * und ShapeSets.
	 * 
	 * @throws TechnicalException
	 */
	private void loadCategories() throws TechnicalException {
		// Liste der Kategorien aus Resource-Datei laden
		Properties prop = new Properties();
		try {
			// Properties-Datei laden
			InputStream in = getClass().getResourceAsStream("category.properties");
			prop.load(in);
			in.close();
			
			// Übersicht der Kategorien laden
			Set<Object> keys = prop.keySet();
			for (Object key : keys) {
				if (key instanceof String) {
					String keyString = (String)key;
					if (keyString.startsWith("category.")) {
						
						// Category erzeugen
						String categoryName = prop.getProperty(keyString);
						Category category = createCategory(categoryName, prop);
						categoryMap.put(categoryName, category);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new TechnicalException("Unable to load category properties.");
		}
	}
	
	/**
	 * Erzeugt eine Category aus den Property-Angaben.
	 * 
	 * @param name
	 * @param prop
	 * @return
	 * @throws TechnicalException 
	 */
	private Category createCategory(String name, Properties prop) throws TechnicalException {
		
		Category category = new Category(name);
		
		// die ShapeSets zu der Category suchen
		Set<Object> keys = prop.keySet();
		for (Object key : keys) {
			if (key instanceof String) {
				String keyString = (String)key;
				if (keyString.startsWith(name+".shapeSet.")) {
					
					String shapeSetName = prop.getProperty(keyString);
					
					ShapeSet shapeSet = null;
					// gibt es dies ShapeSet schon?
					if (shapeSetMap.containsKey(shapeSetName)) {
						// ja, verwenden
						shapeSet = shapeSetMap.get(shapeSetName);
					} else {
						// nein, erzeugen und speichern
						shapeSet = createShapeSet(shapeSetName);
						shapeSetMap.put(shapeSetName, shapeSet);
					}
					// ShapeSet zur Category hinzufügen
					category.addShapeSet(shapeSet);
				}
			}
		}
		
		// die Workbook-Typen suchen und erzeugen
		for (Object key : keys) {
			if (key instanceof String) {
				String keyString = (String)key;
				if (keyString.startsWith(name+".type.")) {
					
					String exampleName = prop.getProperty(keyString);
					
					ExampleWorkbook example = new ExampleWorkbook(exampleName);
					// Bild-Pfad lesen
					exampleName = exampleName.replace(" ", "_");
					example.setPicturePath(prop.getProperty(exampleName+".pic"));
					// Example-Pfad lesen
					example.setModelPath(prop.getProperty(exampleName+".model"));
					// Example zur Category hinzufügen
					category.addExample(example);
				}
			}
		}

		return category;
	}
	
	/**
	 * Erzeugt ein ShapeSet lädt es aber noch nicht.
	 * 
	 * @param shapeSetName
	 * @return
	 */
	private ShapeSet createShapeSet(String shapeSetName) {
		
		return new ShapeSet(shapeSetName);
	}

	/**
	 * Liefert eine Liste der der Categories für die Anzeige.
	 * 
	 * @return
	 */
	public Collection<Category> getCategories() {
		return categoryMap.values();
	}
}
