package de.bkroeger.myvisio.svg.attributes;

import org.w3c.dom.svg.SVGPathSeg;
import org.w3c.dom.svg.SVGPathSegCurvetoQuadraticSmoothAbs;

/**
 * @author bk
 */
public class SVGPathSegCurvetoQuadraticSmoothAbsImpl extends SVGPathSegImpl implements
		SVGPathSegCurvetoQuadraticSmoothAbs {

	private static final long serialVersionUID = -6722776032077341870L;
	
	/**
	 * @param x
	 * @param y
	 */
	public SVGPathSegCurvetoQuadraticSmoothAbsImpl(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public short getPathSegType() {
		
		return SVGPathSeg.PATHSEG_CURVETO_QUADRATIC_SMOOTH_ABS;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		
		return "T";
	}
}
