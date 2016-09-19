package de.bkroeger.myvisio.shapes;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.util.UUID;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDescElement;
import org.w3c.dom.svg.SVGElement;
import org.w3c.dom.svg.SVGMetadataElement;
import org.w3c.dom.svg.SVGTitleElement;

/**
 * <p>
 * Interface für ein Visio-Shapes. 
 * Ein Shape hat einen Namen und eine interne UUID.
 * </p><p>
 * Im SVG-sinne entspricht das einem G-Element.
 * </p><p>
 * Shapes sind in {@link ShapeSet ShapeSets} gruppiert.
 * Die gleichen Shapes können in mehreren ShapeSets enthalten sein.
 * </p>
 * @author bk
 */
public interface IShape {
	
	/**
	 * @return - die UUID des Shapes
	 */
	public UUID getUUID();
	
	/**
	 * @return - die ID des Shapes
	 */
	public String getId();
	
	/**
	 * @return - der Titel des Shapes
	 */
	public SVGTitleElement getTitle();
	
	/**
	 * @return - die Beschreibung des Shapes
	 */
	public SVGDescElement getDescription();
	
	/**
	 * @return - die Metadaten
	 */
	public SVGMetadataElement getMetadata();

	/**
	 * Erzeugt eine tiefe Kopie des Shapes und seiner Substruktur.
	 * @return - eine Kopie des IShape
	 */
	public Object clone();

	/**
	 * <p>
	 * Speichert die Shape-Struktur in dem Visio-Dokument.
	 * </p>
	 * @param doc - das Visio-Dokument
	 * @param shapeElem 
	 */
	public void store(Document doc, Element shapeElem);
	// TODO: DOM-Element durch IShape ersetzen

	/**
	 * <p>
	 * Zeichnet das Shape; evtl. hervorgehoben als selektiert.
	 * </p>
	 * @param g2d - Rendering-Umgebung
	 */
	public void paint(Graphics2D g2d);

	/**
	 * <p>
	 * Liefert das umschliessende Rechteck.
	 * </p>
	 * @return - liefert das umschliessende Rechteck
	 */
	public Shape getBoundingRectangle();

	/**
	 * Setzt den Hotspot des Shapes.
	 * 
	 * @param point
	 */
	public void setOrigin(Point2D.Float point);

	/**
	 * Liefert das Flag, ob das Shape ausgewählt ist.
	 * 
	 * @return - True, wenn ausgewählt
	 */
	public boolean isSelected();
	
	/**
	 * Setzt das Flag "isSelected".
	 * 
	 * @param b
	 */
	public void setSelected(boolean b);
	
	/**
	 * <p>
	 * Der angegebene Viewport wird verwendet, um die Höhe und Breite des Shapes
	 * an die Höhe und Breite des Viewports anzupassen.
	 * </p>
	 * @param viewport
	 */
	public void negotiateViewport(SVGElement viewport);
}
