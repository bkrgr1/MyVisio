package de.bkroeger.myvisio.svg.attributes;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.logging.Logger;

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
	
	private static final Logger logger = Logger.getLogger(SVGPathSegCurvetoCubicAbsImpl.class.getName());
	
	private float x1;
	private float x2;
	private float y1;
	private float y2;

	/**
	 * @param x
	 * @param y
	 * @param x1 
	 * @param y1 
	 * @param x2 
	 * @param y2 
	 */
	public SVGPathSegCurvetoCubicAbsImpl( 
			float x1, float y1, 
			float x2, float y2,
			float x, float y) {
		this.x = x;
		this.y = y;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
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
	 * Draws a cubic Bézier curve from the current point to (x,y) using
	 * (x1,y1) as the control point at the beginning of the curve and (x2,y2)
	 * as the control point at the end of the curve. C (uppercase) indicates
	 * that absolute coordinates will follow; c (lowercase) indicates that
	 * relative coordinates will follow.
	 * 
	 * @param path2d
	 * @param shape
	 * @return - modified Path
	 */
	public Path2D.Float paint(Path2D.Float path2d, SVGBasicShapeElement shape, 
			Graphics2D g2d, SVGPathSeg prevSeg) {
		
		Point2D.Float p0 = new Point2D.Float(prevSeg.getX(), prevSeg.getY());
		@SuppressWarnings("unused")
		Point2D.Float p0c = ((SVGBasicShapeElementImpl)shape).convertCoordinate(p0);
		
		Point2D.Float p1 = new Point2D.Float(this.getX(), this.getY());
		Point2D.Float p1c = ((SVGBasicShapeElementImpl)shape).convertCoordinate(p1);
		logger.info("Current Point="+p1.x+"/"+p1.y);
		Point2D.Float c1 = new Point2D.Float(this.getX1(), this.getY1());
		Point2D.Float c1c = ((SVGBasicShapeElementImpl)shape).convertCoordinate(c1);
		logger.info("Control Point 1="+c1.x+"/"+c1.y);
		Point2D.Float c2 = new Point2D.Float(this.getX2(), this.getY2());
		Point2D.Float c2c = ((SVGBasicShapeElementImpl)shape).convertCoordinate(c2);
		logger.info("Control Point 2="+c2.x+"/"+c2.y);
		
		path2d.curveTo(c1c.x, c1c.y, c2c.x, c2c.y, p1c.x, p1c.y);
		
//		// Control Point 1
//		g2d.draw(new Ellipse2D.Float(c1c.x-2, c1c.y-2, 4, 4));
//		g2d.draw(new Line2D.Float(c1c.x, c1c.y, p0c.x, p0c.y));
//		// Control Point 2
//		g2d.draw(new Ellipse2D.Float(c2c.x-2, c1c.y-2, 4, 4));
//		g2d.draw(new Line2D.Float(c2c.x, c2c.y, p1c.x, p1c.y));
		
		return path2d;
	}
}
