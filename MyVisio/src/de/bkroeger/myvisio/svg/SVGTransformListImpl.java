package de.bkroeger.myvisio.svg;

import java.awt.Point;
import java.io.Serializable;

import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGMatrix;
import org.w3c.dom.svg.SVGTransform;
import org.w3c.dom.svg.SVGTransformList;

/**
 * @author bk
 */
public class SVGTransformListImpl extends SVGListElementImpl<SVGTransform> 
implements SVGTransformList, Serializable {

	private static final long serialVersionUID = -3579153590179103132L;

	/**
	 * Default-Konstruktor.
	 */
	public SVGTransformListImpl() {
		super();
	}
	
	/**
	 * Konstruktor für Transform_Liste mit Transform-Liste-Wert.
	 * @param other - eine andere Transform-Liste
	 */
	@SuppressWarnings("unchecked")
	public SVGTransformListImpl(SVGTransformList other) {
		super((SVGListElementImpl<SVGTransform>) other);
	}

	/**
	 * Konstruktor für eine Transform-Liste mit String-Wert.
	 * @param attribute - Wert des 'transform'-Attribute
	 */
	public SVGTransformListImpl(String attribute) {
		String value = attribute.trim();
		do {
			value = parseTransform(value);
			value = value.trim();
			if (value.startsWith(","))
				value = value.substring(1);
			value = value.trim();
		} while (value.length() > 0);
	}
	
	public Object clone() {
		SVGTransformListImpl clone = new SVGTransformListImpl();
		for (int i=0; i<this.getNumberOfItems(); i++) {
			clone.appendItem((SVGTransform)((SVGTransformImpl)this.getItem(i)).clone());
		}
		return clone;
	}
	
	/**
	 * <p>
	 * Prüft, ob die Transform-Liste eine Transformation des angegebenen Typs enthält.
	 * </p>
	 * @param transformType - der gesuchte Transformationstyp
	 * @return - true oder false
	 */
	public boolean containsTransformType(short transformType) {
		
		for (int i=0; i<this.getNumberOfItems(); i++) {
			if (this.getItem(i).getType() == transformType) return true;
		}
		return false;
	}
	
	/**
	 * <p>
	 * Ersetzt eine Transformation mit einem bestimmten Typ in der Liste.
	 * Wenn keine Transformation mit dem angegebenen Type gefunden wird,
	 * wird die Transformation hinzugefügt.
	 * </p>
	 * @param transformType
	 * @param transform
	 */
	public void replaceTransform(short transformType, SVGTransformImpl transform) {
		
		for (int i=0; i<this.getNumberOfItems(); i++) {
			if (this.getItem(i).getType() == transformType) {
				this.replaceItem(transform, i);
				return;
			}
		}
		this.appendItem(transform);
	}
	
	/**
	 * Analysiert den Wert des transform-Attribute.
	 * @param value
	 * @return
	 */
	private String parseTransform(String value) {
		
		// ist es eine matrix-Transformation?
		if (value.startsWith("matrix")) {
			value = parseMatrixTransform(value.substring("matrix".length()).trim());
		// ist es eine translate-Transformation?
		} else if (value.startsWith("translate")) {
			value = parseTranslateTransform(value.substring("translate".length()).trim());
		// ist es eine scale-Transformation?
		} else if (value.startsWith("scale")) {
			value = parseScaleTransform(value.substring("scale".length()).trim());
		// iest es eine rotate-Transformation?
		} else if (value.startsWith("rotate")) {
			value = parseRotateTransform(value.substring("rotate".length()).trim());
		// ist es eine skewX-Transformation?
		} else if (value.startsWith("skewX")) {
			value = parseSkewxTransform(value.substring("skewX".length()).trim());
		// ist es eine skewY-Transformation?
		} else if (value.startsWith("skewY")) {
			value = parseSkewyTransform(value.substring("skewY".length()).trim());
		} else {
			// nein, eine falsche Transformation
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid transform syntax");
		}
		return value;
	}
	
	/**
	 * Analysiert eine SkewY-Transformation.
	 * @param value - String mit SkewY-Transformation und anderen
	 * @return - restlichen String dahinter
	 */
	private String parseSkewyTransform(String value) {
		
		String rest = null;
		int p = value.indexOf(")");
		if (p > 0) {
			rest = value.substring(p+1);
			value = value.substring(0,p+1);
		} else {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid transform syntax");
		}
		if (value.startsWith("(") == false || value.endsWith(")") == false) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid transform syntax");
		}
		String parts[] = value.substring(1,value.length()-1).split("s+,?s*|,s*");
		if (parts.length != 1) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid transform syntax");
		}
		Float[] floats = new Float[parts.length];
		for (int i=0; i<parts.length; i++) {
			floats[i] = Float.parseFloat(parts[i].trim());
		}
		// Transformation zur Liste hinzufügen
		SVGTransform transform = new SVGTransformImpl(SVGTransform.SVG_TRANSFORM_SKEWY);
		transform.setSkewY(floats[0]);
		this.appendItem(transform);
		
		return rest.trim();
	}

	/**
	 * Analysiert eine SkewX-Transformation.
	 * @param value - String mit SkewX-Transformation und anderen
	 * @return - restlichen String dahinter
	 */
	private String parseSkewxTransform(String value) {
		
		String rest = null;
		int p = value.indexOf(")");
		if (p > 0) {
			rest = value.substring(p+1);
			value = value.substring(0,p+1);
		} else {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid transform syntax");
		}
		if (value.startsWith("(") == false || value.endsWith(")") == false) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid transform syntax");
		}
		String parts[] = value.substring(1,value.length()-1).split("s+,?s*|,s*");
		if (parts.length != 1) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid transform syntax");
		}
		Float[] floats = new Float[parts.length];
		for (int i=0; i<parts.length; i++) {
			floats[i] = Float.parseFloat(parts[i].trim());
		}
		SVGTransform transform = new SVGTransformImpl(SVGTransform.SVG_TRANSFORM_SKEWX);
		transform.setSkewX(floats[0]);
		this.appendItem(transform);
		return rest.trim();
	}

	/**
	 * Analysiert eine Translate-Transformation.
	 * @param value - String mit Translate-Transformation und anderen
	 * @return - restlichen String dahinter
	 */
	private String parseTranslateTransform(String value) {
		
		String rest = null;
		int p = value.indexOf(")");
		if (p > 0) {
			rest = value.substring(p+1);
			value = value.substring(0,p+1);
		} else {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid transform syntax");
		}
		if (value.startsWith("(") == false || value.endsWith(")") == false) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid transform syntax");
		}
		String parts[] = value.substring(1,value.length()-1).split("s+,?s*|,s*");
		if (parts.length < 1 || parts.length > 2) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid transform syntax");
		}
		Float[] floats = new Float[2];
		for (int i=0; i<parts.length; i++) {
			floats[i] = Float.parseFloat(parts[i].trim());
		}
		if (parts.length == 1) floats[1] = 0.0f;
		SVGTransform transform = new SVGTransformImpl(SVGTransform.SVG_TRANSFORM_TRANSLATE);
		transform.setTranslate(floats[0], floats[1]);
		this.appendItem(transform);
		return rest.trim();
	}

	/**
	 * Analysiert eine Rotate-Transformation.
	 * @param value - String mit Rotate-Transformation und anderen
	 * @return - restlichen String dahinter
	 */
	private String parseRotateTransform(String value) {
		
		String rest = null;
		int p = value.indexOf(")");
		if (p > 0) {
			rest = value.substring(p+1);
			value = value.substring(0,p+1);
		} else {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid transform syntax");
		}
		if (value.startsWith("(") == false || value.endsWith(")") == false) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid transform syntax");
		}
		String parts[] = value.substring(1,value.length()-1).split("s+,?s*|,s*");
		if (parts.length != 1 && parts.length != 3) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid transform syntax");
		}
		Float[] floats = new Float[parts.length];
		for (int i=0; i<parts.length; i++) {
			floats[i] = Float.parseFloat(parts[i].trim());
		}
		if (floats.length == 1) {
			SVGTransform transform = new SVGTransformImpl(SVGTransform.SVG_TRANSFORM_ROTATE);
			transform.setRotate(floats[0], 0.0f, 0.0f);
			this.appendItem(transform);
		} else {
			SVGTransform transform = new SVGTransformImpl(SVGTransform.SVG_TRANSFORM_ROTATE);
			transform.setRotate(floats[0], floats[1], floats[2]);
			this.appendItem(transform);
		}
		return rest.trim();
	}

	/**
	 * Analysiert eine Scale-Transformation.
	 * @param value - String mit Scale-Transformation und anderen
	 * @return - restlichen String dahinter
	 */
	private String parseScaleTransform(String value) {
		
		String rest = null;
		int p = value.indexOf(")");
		if (p > 0) {
			rest = value.substring(p+1);
			value = value.substring(0,p+1);
		} else {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid transform syntax");
		}
		if (value.startsWith("(") == false || value.endsWith(")") == false) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid transform syntax");
		}
		String parts[] = value.substring(1,value.length()-1).split("s+,?s*|,s*");
		if (parts.length < 1 || parts.length > 2) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid transform syntax");
		}
		Float[] floats = new Float[2];
		for (int i=0; i<parts.length; i++) {
			floats[i] = Float.parseFloat(parts[i].trim());
		}
		if (parts.length == 1) floats[1] = 0.0f;
		SVGTransform transform = new SVGTransformImpl(SVGTransform.SVG_TRANSFORM_SCALE);
		transform.setScale(floats[0], floats[1]);
		this.appendItem(transform);
		return rest.trim();
	}

	/**
	 * Analysiert eine Matrix-Transformation.
	 * @param value - String mit Matrix-Transformation und anderen
	 * @return - restlichen String dahinter
	 */
	private String parseMatrixTransform(String value) {
		
		String rest = null;
		int p = value.indexOf(")");
		if (p > 0) {
			rest = value.substring(p+1);
			value = value.substring(0,p+1);
		} else {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid transform syntax");
		}
		if (value.startsWith("(") == false || value.endsWith(")") == false) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid transform syntax");
		}
		String parts[] = value.substring(1,value.length()-1).split("s+,?s*|,s*");
		if (parts.length != 6) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Invalid transform syntax");
		}
		Float[] floats = new Float[6];
		for (int i=0; i<6; i++) {
			floats[i] = Float.parseFloat(parts[i].trim());
		}
		this.appendItem(new SVGTransformImpl(new SVGMatrixImpl(floats)));
		return rest.trim();
	}

	/**
	 * Creates an SVGTransform object outside of any document trees. 
	 * The object is initialized to the given matrix transform (i.e., SVG_TRANSFORM_MATRIX). 
	 * The values from the parameter matrix are copied, the matrix parameter is not adopted 
	 * as SVGTransform::matrix.
	 * @param matrix - eine Matrix
	 * @return - ein SVGTransform-Objekt
	 */
	
	public SVGTransform createSVGTransformFromMatrix(SVGMatrix matrix) {
	
		SVGTransform transform = new SVGTransformImpl(matrix);
		return transform;
	}

	/**
	 * Consolidates the list of separate SVGTransform objects by multiplying the 
	 * equivalent transformation matrices together to result in a list consisting of a 
	 * single SVGTransform object of type SVG_TRANSFORM_MATRIX. The consolidation operation 
	 * creates new SVGTransform object as the first and only item in the list. The returned 
	 * item is the item itself and not a copy. Any changes made to the item are immediately 
	 * reflected in the list.
	 * @return - The resulting SVGTransform object which becomes single item in the list. 
	 * If the list was empty, then a value of null is returned.
	 */
	
	public SVGTransform consolidate() {
		
		if (this.getNumberOfItems() == 0) return null;
		if (this.getNumberOfItems() == 1) return this.getItem(0);
		SVGMatrix newMatrix = multiplyMatrixes(this.getItem(0).getMatrix(), this.getItem(1).getMatrix());
		for (int i=2; i<this.getNumberOfItems(); i++) {
			newMatrix = multiplyMatrixes(newMatrix, this.getItem(i).getMatrix());
		}
		SVGTransform transform = new SVGTransformImpl(newMatrix);
		this.clear();
		this.appendItem(transform);
		return transform;
	}
	
	private SVGMatrix multiplyMatrixes(SVGMatrix matrix1, SVGMatrix matrix2) {
		// multiply the matrixes: each row of matrix A with the each column of matrix B
		// A = a1 x a2 + c1 x b2 + e1 x 0
		// B = b1 x a2 + d1 x b2 + f1 x 0
		// 0 = 0  x a2 + 0  x b2 + 1  x 0
		// C = a1 x c2 + c1 x d2 + e1 x 0
		// D = b1 x c2 + d1 x d2 + f1 x 0
		// 0 = 0  x c2 + 0  x d2 + 1  x 0
		// E = a1 x e2 + c1 x f2 + e1 x 1
		// F = b1 x e2 + d1 x f2 + f1 x 1
		// 1 = 0  x e2 + 0  x f2 + 1  x 1
		return null;
	}

	/**
	 * Transforms map coordinates
	 * from a new coordinate system into a previous coordinate system
	 * @param p1 - point in the new coordinate system
	 * @return - point in the previous coordinate system
	 */
	public Point.Double transform(Point.Double p1) {
		
		Point.Double p = p1;
		for (int i=0; i<this.getNumberOfItems(); i++) {
			SVGTransform transformation = this.getItem(i);
			p = ((SVGTransformListImpl) transformation).transform(p);
		}
		return p;
	}

	
	public String toXmlString() {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<this.getNumberOfItems(); i++) {
			SVGTransform transformation = this.getItem(i);
			if (sb.length() > 0) sb.append(" ");
			sb.append(transformation.toXmlString());
		}
		return sb.toString();
	}
}
