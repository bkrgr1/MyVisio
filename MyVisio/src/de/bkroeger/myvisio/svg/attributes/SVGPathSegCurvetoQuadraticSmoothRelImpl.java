package de.bkroeger.myvisio.svg.attributes;

import org.w3c.dom.svg.SVGPathSeg;
import org.w3c.dom.svg.SVGPathSegCurvetoQuadraticSmoothRel;

/**
 * @author bk
 */
public class SVGPathSegCurvetoQuadraticSmoothRelImpl extends SVGPathSegImpl implements
		SVGPathSegCurvetoQuadraticSmoothRel {

	private static final long serialVersionUID = -6722776032077341870L;
	
	/**
	 * @param x
	 * @param y
	 */
	public SVGPathSegCurvetoQuadraticSmoothRelImpl(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public short getPathSegType() {
		
		return SVGPathSeg.PATHSEG_CURVETO_QUADRATIC_SMOOTH_REL;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		
		return "t";
	}
}
