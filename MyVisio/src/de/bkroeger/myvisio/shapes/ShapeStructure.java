package de.bkroeger.myvisio.shapes;

import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;

import de.bkroeger.myvisio.svg.elements.SVGGElementImpl;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * <p>
 * Diese Struktur dient zur Verwaltung der Shapes auf einer Seite oder im Navigator.
 * </p>
 * @author bk
 */
public class ShapeStructure implements Transferable, Serializable, Cloneable {
	
	private static final long serialVersionUID = -4762717981946707194L;
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ShapeStructure.class.getName());

	/**
	 * Position der linken oberen Ecke des Bounding-Rectangle
	 */
	public Point.Float origin;
	
	/**
	 * Flag, ob das Shape ausgewählt ist
	 */
	public boolean isSelectedFlag = false;
	public boolean isSelected() { return isSelectedFlag; }
	public void setSelected(boolean value) { isSelectedFlag = value; }
	
	/**
	 * Shape (SVG-Objekthierarchie)
	 */
	private IShape shape;
	public IShape getShape() { return shape; }


	//====== The following methods implement the Transferable interface =====

	/**
	 *  Spezielles DataFlavor für Shape-Strukturen.
	 */
	public static DataFlavor ShapeDataFlavor = 
		new DataFlavor(SVGGElementImpl.class, "SVGGElement");
	
	/** 
	 * Liste der unterstützten DataFlavors.
	 * Nur ein Eintrag: das {@link ShapeDataFlavor}
	 */
	public static DataFlavor[] supportedFlavors = { ShapeDataFlavor, DataFlavor.stringFlavor };

	/** 
	 * Liefert eine Liste der DataFlavors, die transportiert werden können.
	 */
	
	public DataFlavor[] getTransferDataFlavors() {
		return (DataFlavor[]) supportedFlavors.clone();
	}

	/** 
	 * Prüft, ob ein DataFlavor unterstützt wird.
	 */
	
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return (flavor.equals(ShapeDataFlavor) || flavor.equals(DataFlavor.stringFlavor));
	}

	/**
	 * Liefert die Transfer-Daten für den angegebenen DataFlavor.
	 */
	
	public Object getTransferData(DataFlavor flavor) 
		throws UnsupportedFlavorException, IOException {
		
		if (flavor.equals(ShapeDataFlavor)) {
			return this;
		} else {
			return this.toString();
		}
	}
	
	/**
	 * Default-Konstruktor
	 */
	public ShapeStructure() {
		origin = new Point.Float(0.0f, 0.0f);
	}
	
	/**
	 * Konstruktor mit Shape-Template
	 * @param shape
	 */
	public ShapeStructure(IShape shape) {
		origin = new Point.Float(0.0f, 0.0f);
		this.shape = shape;
	}
	
	/**
	 * Erstellt eine Kopie der Shape-Struktur
	 */
	public ShapeStructure clone() {
		
		ShapeStructure struct = new ShapeStructure();
		struct.isSelectedFlag = false;
		struct.shape = (IShape) shape.clone();
		struct.origin = this.origin;
		
		return struct;
	}
	
	public void translate(Point dropPosition, float scaleFactor, float width, float height){
		
		this.origin = new Point.Float(
			(float)dropPosition.getX() * scaleFactor, 
			(float)dropPosition.getY() * scaleFactor);
	}
	
	
	public String toString() {
		
		StringBuilder sb = new StringBuilder("{");
		sb.append("isSelected: "+(this.isSelectedFlag == true ? "true" : "false")+", ");
		String shapeString = "null";
		if (shape != null) shapeString = shape.toString();
		sb.append("shape: "+"{"+shapeString+"}"+", ");
		sb.append("}");
		
		return sb.toString();
	}
	
	public static ShapeStructure parse(String json) {
		// TODO: JSON-Struktur parsen
		throw new NotImplementedException();
	}
	
	public void setOrigin(Point2D.Float point) {
		this.shape.setOrigin(point);
	}
}
