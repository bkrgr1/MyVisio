package de.bkroeger.myvisio.svg.attributes;

import org.w3c.dom.svg.SVGPathSeg;
import org.w3c.dom.svg.SVGPathSegLinetoRel;

/**
 * @author bk
 */
public class SVGPathSegLinetoRelImpl extends SVGPathSegImpl implements
		SVGPathSegLinetoRel {

	private static final long serialVersionUID = -6722776032077341870L;
	
	/**
	 * @param x
	 * @param y
	 */
	public SVGPathSegLinetoRelImpl(float x, float y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public short getPathSegType() {
		
		return SVGPathSeg.PATHSEG_LINETO_REL;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		
		return "l";
	}
}
