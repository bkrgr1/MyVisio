package de.bkroeger.myvisio.svg.attributes;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import org.w3c.dom.svg.SVGBasicShapeElement;
import org.w3c.dom.svg.SVGPathSeg;
import org.w3c.dom.svg.SVGPathSegCurvetoCubicRel;
import org.w3c.dom.svg.SVGPathSegCurvetoCubicSmoothAbs;
import org.w3c.dom.svg.SVGPathSegCurvetoCubicSmoothRel;

import java.util.logging.Logger;

import de.bkroeger.myvisio.svg.elements.SVGBasicShapeElementImpl;

/**
 * Draws a cubic Bézier curve from the current point to (x,y). The first
control point is assumed to be the reflection of the second control
point on the previous command relative to the current point. (If there
is no previous command or if the previous command was not an C,
c, S or s, assume the first control point is coincident with the current
point.) (x2,y2) is the second control point (i.e., the control point at
the end of the curve). S (uppercase) indicates that absolute
coordinates will follow; s (lowercase) indicates that relative
coordinates will follow. 
 * @author bk
 */
public class SVGPathSegCurvetoCubicSmoothAbsImpl extends SVGPathSegImpl implements
		SVGPathSegCurvetoCubicSmoothAbs {

	private static final long serialVersionUID = -6722776032077341870L;
	
	private static final Logger logger = Logger.getLogger(SVGPathSegCurvetoCubicSmoothAbsImpl.class.getName());
	
	private float x2;
	private float y2;

	/**
	 * @param x
	 * @param y
	 * @param x2
	 * @param y2
	 */
	public SVGPathSegCurvetoCubicSmoothAbsImpl(float x2, float y2, float x, float y) {
		this.x = x;
		this.y = y;
		this.x2 = x2;
		this.y2 = y2;
	}

	@Override
	public short getPathSegType() {
		
		return SVGPathSeg.PATHSEG_CURVETO_CUBIC_SMOOTH_ABS;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		
		return "S";
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
		logger.info("Current point="+p1.x+"/"+p1.y);
		Point2D.Float p1c = ((SVGBasicShapeElementImpl)shape).convertCoordinate(p1);
		
		Point2D.Float prevPt = new Point2D.Float(prevSeg.getX(), prevSeg.getY());
		Point2D.Float p = null;
		if (prevSeg instanceof SVGPathSegCurvetoCubicAbsImpl) {
			SVGPathSegCurvetoCubicAbsImpl pSeg = (SVGPathSegCurvetoCubicAbsImpl)prevSeg;
			p = new Point2D.Float(pSeg.getX2(), pSeg.getY2());
		} else if (prevSeg instanceof SVGPathSegCurvetoCubicRel) {
			
		} else if (prevSeg instanceof SVGPathSegCurvetoCubicSmoothAbsImpl) {
			SVGPathSegCurvetoCubicSmoothAbsImpl pSeg = (SVGPathSegCurvetoCubicSmoothAbsImpl)prevSeg;
			p = new Point2D.Float(pSeg.getX2(), pSeg.getY2());
		} else if (prevSeg instanceof SVGPathSegCurvetoCubicSmoothRel) {
			
		} else {
			p = prevPt;
		}
		logger.info("Previous point="+p.x+"/"+p.y);
		float dX = p.x - prevPt.x;
		float dY = p.y - prevPt.y;
		Point2D.Float c1 = new Point2D.Float(prevPt.x - dX, prevPt.y - dY);
		Point2D.Float c1c = ((SVGBasicShapeElementImpl)shape).convertCoordinate(c1);
		logger.info("Control point 1="+c1.x+"/"+c1.y);
		
		Point2D.Float c2 = new Point2D.Float(this.getX2(), this.getY2());
		Point2D.Float c2c = ((SVGBasicShapeElementImpl)shape).convertCoordinate(c2);
		logger.info("Control point 2="+c2.x+"/"+c2.y);
		
		path2d.curveTo(c1c.x, c1c.y, c2c.x, c2c.y, p1c.x, p1c.y);
		return path2d;
	}
}
