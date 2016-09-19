package de.bkroeger.myvisio.svg.attributes;

import org.w3c.dom.svg.SVGPathSeg;
import org.w3c.dom.svg.SVGPathSegCurvetoCubicSmoothRel;

/**
 * @author bk
 */
public class SVGPathSegCurvetoCubicSmoothRelImpl extends SVGPathSegImpl implements
		SVGPathSegCurvetoCubicSmoothRel {

	private static final long serialVersionUID = -6722776032077341870L;
	
	private float x2;
	private float y2;

	/**
	 * @param x3
	 * @param y3
	 * @param x22
	 * @param y22
	 */
	public SVGPathSegCurvetoCubicSmoothRelImpl(float x3, float y3, float x22,
			float y22) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public short getPathSegType() {
		
		return SVGPathSeg.PATHSEG_CURVETO_CUBIC_SMOOTH_REL;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		
		return "s";
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
}
