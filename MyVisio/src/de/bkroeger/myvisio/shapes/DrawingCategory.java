package de.bkroeger.myvisio.shapes;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * Eine 'DrawingCategory' definiert eine Kategorie für Zeichnungen.
 * Zu jeder Kategorie gehören ein oder mehrere Vorlagen-Sets (siehe {@link TemplateSet}).
 * </p>
 * @author bk
 */
public class DrawingCategory {

	/**
	 * Name der Kategorie
	 */
	private String name;
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	/**
	 * Beschreibung
	 */
	private String description;
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	
	/**
	 * Map der Template-Sets
	 */
	private Map<String, TemplateSet> templateSetMap = new HashMap<String, TemplateSet>();
	public  void addTemplateSet(TemplateSet value) { templateSetMap.put(value.getName(), value); }
	public  Set<String> getTemplateKeySet() { return templateSetMap.keySet(); }
	public  TemplateSet getTemplateSet(String name) { return templateSetMap.get(name); }
	
	/**
	 * Konstruktor mit Name.
	 * @param name - Name der Kategorie
	 */
	public DrawingCategory(String name, String description) {
		this.setName(name);
		this.description = description;
	}
}
