package de.bkroeger.myvisio.svg.attributes;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import org.w3c.dom.svg.SVGBasicShapeElement;
import org.w3c.dom.svg.SVGPathSeg;
import org.w3c.dom.svg.SVGPathSegLinetoAbs;

import de.bkroeger.myvisio.svg.elements.SVGBasicShapeElementImpl;

/**
 * @author bk
 */
public class SVGPathSegLinetoAbsImpl extends SVGPathSegImpl implements
		SVGPathSegLinetoAbs {

	private static final long serialVersionUID = -6722776032077341870L;
	
	/**
	 * @param x
	 * @param y
	 */
	public SVGPathSegLinetoAbsImpl(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public short getPathSegType() {
		
		return SVGPathSeg.PATHSEG_LINETO_ABS;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		
		return "L";
	}
	
	/**
	 * @param path2d
	 * @param shape
	 * @return - modified Path
	 */
	public Path2D.Float paint(Path2D.Float path2d, SVGBasicShapeElement shape, 
			Graphics2D g2d, SVGPathSeg prevSeg) {
		
		Path2D.Float path = path2d;
		Point2D.Float p = new Point2D.Float(this.getX(), this.getY());
		Point2D.Float p2 = ((SVGBasicShapeElementImpl)shape).convertCoordinate(p);
		
		path2d.lineTo(p2.x, p2.y);
		return path;
	}
}
