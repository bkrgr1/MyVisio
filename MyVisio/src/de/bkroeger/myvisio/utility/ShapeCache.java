package de.bkroeger.myvisio.utility;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.bkroeger.myvisio.shapes.ShapeDef;

/**
 * <p>
 * Der ShapeCache enthält alle geladenen Shapes.
 * </p>
 * @author bk
 */
public class ShapeCache {

	/**
	 * Namen der geladenen Shapes; verweist über die UUID auf die Shape-Definition.
	 */
	private Map<String, UUID> shapeNameMap = new HashMap<String, UUID>();
	public  ShapeDef getShapeByName(String name) {
		if (shapeNameMap.containsKey(name)) {
			return shapeUUIDMap.get(shapeNameMap.get(name));
		} else
			return null;
	}
	
	/**
	 * UUIDs der geladenen Shapes
	 */
	private Map<UUID, ShapeDef> shapeUUIDMap = new HashMap<UUID, ShapeDef>();
	public  ShapeDef getShapeByUUID(UUID uuid) {
		return shapeUUIDMap.get(uuid);
	}
	
	/**
	 * Fügt eine Shape-Definition zum Cache hinzu.
	 * 
	 * @param shapeDef
	 */
	public void addShapeDef(ShapeDef shapeDef) {
		
		if (shapeUUIDMap.containsKey(shapeDef.getUUID()) == false) {
			
			shapeUUIDMap.put(shapeDef.getUUID(), shapeDef);
			shapeNameMap.put(shapeDef.getName(), shapeDef.getUUID());
		}
	}
}
