package de.bkroeger.myvisio.svg.attributes;

import org.w3c.dom.svg.SVGPathSeg;
import org.w3c.dom.svg.SVGPathSegMovetoRel;

/**
 * @author bk
 */
public class SVGPathSegMovetoRelImpl extends SVGPathSegImpl implements
		SVGPathSegMovetoRel {

	private static final long serialVersionUID = -6722776032077341870L;
	
	/**
	 * @param x
	 * @param y
	 */
	public SVGPathSegMovetoRelImpl(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public short getPathSegType() {
		
		return SVGPathSeg.PATHSEG_MOVETO_REL;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		
		return "m";
	}
}
