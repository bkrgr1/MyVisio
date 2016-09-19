package de.bkroeger.myvisio.shapes;

import java.awt.Graphics2D;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.tree.DefaultMutableTreeNode;

import org.junit.Assert;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.svg.SVGRect;

import de.bkroeger.myvisio.data.TemplateFile;
import de.bkroeger.myvisio.svg.elements.SVGSVGElementImpl;
import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * <p>
 * Ein TemplateShapeSet ist ein Set von Shape-Templates für ein bestimmtes Thema.
 * </p><p>
 * Ein TemplateShapeSet enthält ein SVGSVGElement mit ein oder mehreren &lt;g&gt;-Tags für die 
 * einzelnen Shapes im Template. Die &lt;g&gt;-Tags sind mit class="shape" markiert.
 * </p><p>
 * Ein TemplateShapeSet wird aus einer {@link TemplateFile | XML-Datei} gelesen.
 * </p>
 * @author bk
 */
public class TemplateSet extends DefaultMutableTreeNode {
	
	private static final long serialVersionUID = -363238919433487701L;

	private static final Logger logger = Logger.getLogger(TemplateSet.class.getName());
	
	private TemplateFile templateFile;
	public TemplateFile getTemplateFile() { return templateFile; }
	// TODO: TemplateFile und TemplateSet zusammenführen
	
	private String id;
	public String getId() { return id; }
	public void   setId(String value) { id = value; }
	
	private String name;
	public  String getName() { 
		Assert.assertNotNull(name);
		return name; 
	}
	public  void   setName(String value) { name = value; }
	
	private Element domElement;
	
	private Map<String, IShape> templates = new HashMap<String, IShape>();
	public  Set<String> getTemplateKeys() { return templates.keySet(); }
	public  IShape getTemplate(String name) { return templates.get(name); }
	public  Collection<IShape> getTemplates() { return templates.values(); }
	
	private SVGSVGElementImpl svgElement;
	public  SVGSVGElementImpl getSVGElement() { return svgElement; }
	
	private String description;
	public  String getDescription() { return this.description; }
	public  void   setDescription(String value) { this.description = value; }
	
	/**
	 * Konstruktor
	 * @throws TechnicalException 
	 */
	public TemplateSet(TemplateFile templateFile) 
		throws TechnicalException {
		
//		// Template-Datei in den Speicher laden
//		Document doc = templateFile.getXmlDocument();
//		
//		// DOM-Struktur in Document-Hierarchie umwandeln
//		this.parseXml(doc);
	}
	
	/**
	 * <p>
	 * Liefert die Anzahl g-Elemente mit class="shape"
	 * </p>
	 * @return - Anzahl g-Elemente
	 */
	public int getShapeCount() {
		return svgElement.getShapeCount();
	}
	
	/**
	 * <p>
	 * Zeichnet die Shapes des TemplateSet.
	 * </p>
	 * @param g2d
	 * @param viewport
	 */
	public void paint(Graphics2D g2d) {
		
		logger.entering(TemplateSet.class.getName(), "paint", 
			new Object[] {g2d});
		
		svgElement.paint(g2d);
	}
	
	/**
	 * <p>
	 * Vereinbar einen Viewport mit dem SVG-Element des TemplateShapeSet.
	 * </p>
	 * @param viewport - Viewport des TemplateCanvas
	 * @return - Liste der Shapes
	 */
	public void negotiateViewport(SVGRect viewport) {
		
		svgElement.negotiateViewport(viewport);
	}

	/**
	 * Liefert eine Liste der Shapes in diesem TemplateSet.
	 * @return
	 */
	public List<IShape> getShapeList() {
		
		return svgElement.getShapeList();
	}
}
