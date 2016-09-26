package de.bkroeger.myvisio.svg;

import java.awt.geom.Point2D;
import java.io.Serializable;

import org.w3c.dom.svg.SVGMatrix;
import org.w3c.dom.svg.SVGTransform;

/**
 * Many of SVG's graphics operations utilize 2x3 matrices of the form:
 * [a c e]
 * [b d f]
 * which, when expanded into a 3x3 matrix for the purposes of matrix arithmetic, become:
 * [a c e]
 * [b d f]
 * [0 0 1]
 * 
 * @author bk
 */
public class SVGTransformImpl implements SVGTransform, Serializable {
	
	private static final long serialVersionUID = -3588174098450759815L;

	private short transformType;
	
	private SVGMatrix matrix;
	
	private float angle;
	
	private float x;
	
	private float y;

	public SVGTransformImpl() {
		this.transformType = SVGTransform.SVG_TRANSFORM_UNKNOWN;
	}
	
	public SVGTransformImpl(short transformType) {
		this.transformType = transformType;
	}
	
	public SVGTransformImpl(SVGMatrix matrix) {
		this.transformType = SVGTransform.SVG_TRANSFORM_MATRIX;
		this.matrix = new SVGMatrixImpl(
				matrix.getA(), matrix.getB(),
				matrix.getC(), matrix.getD(),
				matrix.getE(), matrix.getF());
	}
	
	
	public Object clone() {
		SVGTransformImpl clone = new SVGTransformImpl();
		clone.angle = this.angle;
		clone.matrix = (SVGMatrix) ((SVGMatrixImpl)this.matrix).clone();
		clone.transformType = this.transformType;
		clone.x = this.x;
		clone.y = this.y;
		return clone;
	}

	
	public short getType() {
		return this.transformType;
	}

	
	public SVGMatrix getMatrix() {
		return this.matrix;
	}

	
	public float getAngle() {
		return this.angle;
	}

	
	public void setMatrix(SVGMatrix matrix) {
		this.transformType = SVGTransform.SVG_TRANSFORM_MATRIX;
		this.matrix = matrix;
	}

	/**
	 * Translation is equivalent to the matrix or [1 0 0 1 tx ty], 
	 * where tx and ty are the distances to translate coordinates in X and Y, respectively.
	 * @param tx - translate x
	 * @param ty - translate y
	 */
	
	public void setTranslate(float tx, float ty) {
		this.transformType = SVGTransform.SVG_TRANSFORM_TRANSLATE;
		this.x = tx;
		this.y = ty;
		this.matrix = new SVGMatrixImpl(1.0f, 0.0f, 0.0f, 1.0f, tx, ty);
	}

	/**
	 * Scaling is equivalent to the matrix or [sx 0 0 sy 0 0]. 
	 * One unit in the X and Y directions in the new coordinate system 
	 * equals sx and sy units in the previous coordinate system, respectively.
	 * @param sx - Scaling x
	 * @param sy - Scaling y
	 */
	
	public void setScale(float sx, float sy) {
		this.transformType = SVGTransform.SVG_TRANSFORM_SCALE;
		this.x = sx;
		this.y = sy;
		this.matrix = new SVGMatrixImpl(sx, 0.0f, 0.0f, sy, 0.0f, 0.0f);
	}

	/**
	 * Rotation about the origin is equivalent to the matrix
	 * or [cos(a) sin(a) -sin(a) cos(a) 0 0], which has the effect 
	 * of rotating the coordinate system axes by angle a.
	 * @param angle - Winkel
	 * @param cx - center x
	 * @param cy - center y
	 */
	
	public void setRotate(float angle, float cx, float cy) {
		this.transformType = SVGTransform.SVG_TRANSFORM_ROTATE;
		this.angle = angle;
		this.x = cx;
		this.y = cy;
		this.matrix = new SVGMatrixImpl(
				(float)Math.cos(angle),
				(float)Math.sin(angle),
				(float)Math.sin(angle) * -1,
				(float)Math.cos(angle),
				0.0f, 0.0f);
	}

	/**
	 * A skew transformation along the x-axis is equivalent to the matrix
	 * or [1 0 tan(a) 1 0 0], which has the effect of skewing X coordinates by angle a.
	 * @param angle - Winkel
	 */
	
	public void setSkewX(float angle) {
		this.transformType = SVGTransform.SVG_TRANSFORM_SKEWX;
		this.angle = angle;
		this.matrix = new SVGMatrixImpl(
				1.0f,
				0.0f,
				(float)Math.tan(angle),
				1.0f,
				0.0f, 
				0.0f);
	}

	/**
	 * A skew transformation along the y-axis is equivalent to the matrix
	 * or [1 tan(a) 0 1 0 0], which has the effect of skewing Y coordinates by angle a.
	 * @param angle - Winkel
	 */
	
	public void setSkewY(float angle) {
		this.transformType = SVGTransform.SVG_TRANSFORM_SKEWY;
		this.angle = angle;
		this.matrix = new SVGMatrixImpl(
				1.0f,
				(float)Math.tan(angle),
				0.0f,
				1.0f,
				0.0f, 
				0.0f);
	}

	public float getX() { return this.x; }
	
	public float getY() { return this.y; }
	
	/**
	 * <p>
	 * Transforms map coordinates
	 * from a new coordinate system into a previous coordinate system
	 * </p>
	 * @param p1 - point in the new coordinate system
	 * @return - point in the previous coordinate system
	 */
	public Point2D.Float transform(Point2D.Float p1) {
		
		Point2D.Float p2 = new Point2D.Float();
		p2.x = matrix.getA() * p1.x + matrix.getC() * p1.y + matrix.getE() * 1.0f;
		p2.y = matrix.getB() * p1.x + matrix.getD() * p1.y + matrix.getF() * 1.0f;
		return p2;
	}

	
	public String toXmlString() {
		switch (this.transformType) {
		case SVGTransform.SVG_TRANSFORM_MATRIX:
			return String.format("matrix(%.3f %.3f %.3f %.3f %.3f %.3f)", 
				this.matrix.getA(), this.matrix.getB(), this.matrix.getC(),
				this.matrix.getD(), this.matrix.getE(), this.matrix.getF());
		case SVGTransform.SVG_TRANSFORM_ROTATE:
			return String.format("rotate(%.f)", this.angle);
		case SVGTransform.SVG_TRANSFORM_SCALE:
			return String.format("scale(%.3f %.3f)", this.x, this.y);
		case SVGTransform.SVG_TRANSFORM_SKEWX:
			return String.format("skewX(%f)", this.angle);
		case SVGTransform.SVG_TRANSFORM_SKEWY:
			return String.format("skewY(%f)", this.angle);
		case SVGTransform.SVG_TRANSFORM_TRANSLATE:
			return String.format("translate(%.3f %.3f)", this.x, this.y);
		default:
			return "";
		}
	}
	
	
	public String toString() {
		
		switch (this.transformType) {
		case SVGTransform.SVG_TRANSFORM_MATRIX:
			return String.format("matrix(%.3f %.3f %.3f %.3f %.3f %.3f)", 
				this.matrix.getA(), this.matrix.getB(), this.matrix.getC(),
				this.matrix.getD(), this.matrix.getE(), this.matrix.getF());
		case SVGTransform.SVG_TRANSFORM_ROTATE:
			return String.format("rotate(%.3f)", this.angle);
		case SVGTransform.SVG_TRANSFORM_SCALE:
			return String.format("scale(%.3f %.3f)", this.x, this.y);
		case SVGTransform.SVG_TRANSFORM_SKEWX:
			return String.format("skewX(%f)", this.angle);
		case SVGTransform.SVG_TRANSFORM_SKEWY:
			return String.format("skewY(%f)", this.angle);
		case SVGTransform.SVG_TRANSFORM_TRANSLATE:
			return String.format("translate(%.3f %.3f)", this.x, this.y);
		default:
			return "";
		}
	}
}
