package de.bkroeger.myvisio.svg.attributes;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import org.w3c.dom.svg.SVGBasicShapeElement;
import org.w3c.dom.svg.SVGPathSeg;
import org.w3c.dom.svg.SVGPathSegCurvetoCubicAbs;

import de.bkroeger.myvisio.svg.elements.SVGBasicShapeElementImpl;

/**
 * @author bk
 */
public class SVGPathSegCurvetoCubicAbsImpl extends SVGPathSegImpl implements
		SVGPathSegCurvetoCubicAbs {

	private static final long serialVersionUID = -6722776032077341870L;
	
	private float x1;
	private float x2;
	private float y1;
	private float y2;

	/**
	 * @param x3
	 * @param y3
	 * @param x12
	 * @param y12
	 * @param x22
	 * @param y22
	 */
	public SVGPathSegCurvetoCubicAbsImpl(float x3, float y3, float x12,
			float y12, float x22, float y22) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public short getPathSegType() {
		
		return SVGPathSeg.PATHSEG_CURVETO_CUBIC_ABS;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		
		return "C";
	}

	@Override
	public float getX1() {
		return x1;
	}

	@Override
	public void setX1(float x1) {
		this.x1 = x1;
	}

	@Override
	public float getY1() {
		return y1;
	}

	@Override
	public void setY1(float y1) {
		this.y1 = y1;
	}

	@Override
	public float getX2() {
		return x2;
	}

	@Override
	public void setX2(float x2) {
		this.x2 = x2;
	}

	@Override
	public float getY2() {
		return y2;
	}

	@Override
	public void setY2(float y2) {
		this.y2 = y2;
	}
	
	/**
	 * @param path2d
	 * @param shape
	 * @return - modified Path
	 */
	public Path2D.Float paint(Path2D.Float path2d, SVGBasicShapeElement shape, 
			Graphics2D g2d, SVGPathSeg prevSeg) {
		
		Point2D.Float p1 = new Point2D.Float(this.getX(), this.getY());
		Point2D.Float p1c = ((SVGBasicShapeElementImpl)shape).convertCoordinate(p1);
		Point2D.Float c1 = new Point2D.Float(this.getX1(), this.getY1());
		Point2D.Float c1c = ((SVGBasicShapeElementImpl)shape).convertCoordinate(c1);
		Point2D.Float c2 = new Point2D.Float(this.getX2(), this.getY2());
		Point2D.Float c2c = ((SVGBasicShapeElementImpl)shape).convertCoordinate(c2);
		
		path2d.curveTo(p1c.x, p1c.y, c1c.x, c1c.y, c2c.x, c2c.y);
		return path2d;
	}
}
