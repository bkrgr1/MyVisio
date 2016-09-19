package de.bkroeger.myvisio.svg.attributes;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import org.w3c.dom.svg.SVGBasicShapeElement;
import org.w3c.dom.svg.SVGPathSeg;
import org.w3c.dom.svg.SVGPathSegMovetoAbs;

import de.bkroeger.myvisio.svg.elements.SVGBasicShapeElementImpl;

/**
 * @author bk
 */
public class SVGPathSegMovetoAbsImpl extends SVGPathSegImpl implements
		SVGPathSegMovetoAbs {

	private static final long serialVersionUID = -6722776032077341870L;
	
	/**
	 * @param x
	 * @param y
	 */
	public SVGPathSegMovetoAbsImpl(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public short getPathSegType() {
		
		return SVGPathSeg.PATHSEG_MOVETO_ABS;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		
		return "M";
	}
	
	/**
	 * @param path2d
	 * @param shape
	 * @return - modified Path
	 */
	public Path2D.Float paint(Path2D.Float path2d, SVGBasicShapeElement shape, 
			Graphics2D g2d, SVGPathSeg prevSeg) {
		
		Point2D.Float p = new Point2D.Float(this.getX(), this.getY());
		Point2D.Float p2 = ((SVGBasicShapeElementImpl)shape).convertCoordinate(p);
		
		Path2D.Float path = new Path2D.Float();
		path.moveTo(p2.x, p2.y);

		return path;

	}
}
