package de.bkroeger.myvisio.svg.attributes;

import org.w3c.dom.svg.SVGPathSeg;
import org.w3c.dom.svg.SVGPathSegCurvetoCubicRel;

/**
 * @author bk
 */
public class SVGPathSegCurvetoCubicRelImpl extends SVGPathSegImpl implements
		SVGPathSegCurvetoCubicRel {

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
	public SVGPathSegCurvetoCubicRelImpl(float x3, float y3, float x2,
			float y2, float x, float y) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public short getPathSegType() {
		
		return SVGPathSeg.PATHSEG_CURVETO_CUBIC_REL;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		
		return "c";
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
}
