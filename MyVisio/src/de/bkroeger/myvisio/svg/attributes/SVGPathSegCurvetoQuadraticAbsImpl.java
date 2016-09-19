package de.bkroeger.myvisio.svg.attributes;

import org.w3c.dom.svg.SVGPathSeg;
import org.w3c.dom.svg.SVGPathSegCurvetoQuadraticAbs;

/**
 * @author bk
 */
public class SVGPathSegCurvetoQuadraticAbsImpl extends SVGPathSegImpl implements
		SVGPathSegCurvetoQuadraticAbs {

	private static final long serialVersionUID = -6722776032077341870L;
	
	private float x1;
	private float y1;

	/**
	 * @param x2
	 * @param y2
	 * @param x12
	 * @param y12
	 */
	public SVGPathSegCurvetoQuadraticAbsImpl(float x2, float y2, float x12,
			float y12) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public short getPathSegType() {
		
		return SVGPathSeg.PATHSEG_CURVETO_QUADRATIC_ABS;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		
		return "Q";
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
}
