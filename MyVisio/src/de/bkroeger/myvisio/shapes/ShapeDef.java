package de.bkroeger.myvisio.shapes;

import java.util.UUID;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * <p>
 * Eine ShapeDef repräsentiert ein geladenes Shape im {@link ShapeCache}.
 * </p>
 * @author bk
 */
public class ShapeDef {

	private static final String SHAPE_ELEMENT_TAG = "Shape";

	private IShape shape;
	public  IShape getShape() { return this.shape; }
	
	private String shapeName;
	public  String getName() { return this.shapeName; }
	
	private UUID shapeUUID;
	public  UUID getUUID() { return this.shapeUUID; }
	
	@SuppressWarnings("unused")
	private Element shapeElement;
	
	/**
	 * Lädt das Shape aus einem W3C-Element
	 * 
	 * @param shapeElement
	 */
	public void loadShape(Element shapeElement) {
		
		this.shapeElement = shapeElement;
		
		// TODO: Shape laden
	}
	
	/**
	 * Speichert das Shape als XML-W3C-Element
	 * 
	 * @param doc - das W3C-Document
	 * @return
	 */
	public Element storeShape(Document doc) {
		
		Element shapeElement = doc.createElement(SHAPE_ELEMENT_TAG);
		// TODO: Shape schreiben
		return shapeElement;
	}
}
