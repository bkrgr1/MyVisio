package de.bkroeger.myvisio.model;

import java.util.ArrayList;
import java.util.List;

import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * Beispiele für die Nutzung von MyVisio.
 * 
 * @author bk
 */
public class ExampleWorkbook extends Workbook {
	
	/**
	 * Name des Beispiels.
	 */
	private String name;
	public String getName() { return this.name; }
	
	/**
	 * Liste der ShapeSets, die für dieses Beispiel benötigt werden.
	 */
	private List<ShapeSet> shapeSets = new ArrayList<ShapeSet>();
	public List<ShapeSet> getShapeSets() {
		return shapeSets;
	}
	
	/**
	 * Application-Pfad zum Bild des Beispiels.
	 */
	private String picturePath;
	public void setPicturePath(String value) {
		this.picturePath = value;
	}
	public String getPicturePath() { return this.picturePath; }
	
	/**
	 * Application-Pfad zu dem MyVisio-Dokument.
	 */
	private String modelPath;
	public void setModelPath(String value) {
		this.modelPath = value;
	}
	public String getModelPath() { return this.modelPath; }

	/**
	 * Konstruktor
	 * @param name
	 * @throws TechnicalException
	 */
	public ExampleWorkbook(String name) throws TechnicalException {
		super();
		this.name = name.replace(".", " ");
	}
	
	// TODO: Shape sets laden
}
