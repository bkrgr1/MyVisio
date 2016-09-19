package de.bkroeger.myvisio.svg.attributes;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import org.w3c.dom.svg.SVGBasicShapeElement;
import org.w3c.dom.svg.SVGPathSeg;
import org.w3c.dom.svg.SVGPathSegClosePath;

import de.bkroeger.myvisio.svg.elements.SVGBasicShapeElementImpl;

/**
 * @author bk
 */
public class SVGPathSegClosePathImpl extends SVGPathSegImpl implements
		SVGPathSegClosePath {

	private static final long serialVersionUID = -6722776032077341870L;

	@Override
	public short getPathSegType() {
		
		return SVGPathSeg.PATHSEG_CLOSEPATH;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		
		return "z";
	}
	
	/**
	 * @param path2d
	 * @return - modified Path
	 */
	public Path2D.Float paint(Path2D.Float path2d, SVGBasicShapeElement shape, 
			Graphics2D g2d, SVGPathSeg prevSeg) {
		
		Path2D.Float path = path2d;
		path.closePath();
		
		
		// Rand zeichnen und Shape füllen
		((SVGBasicShapeElementImpl) shape).drawFillAndStroke(g2d, path);
		path = null;
		return path;
	}
}
