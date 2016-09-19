package de.bkroeger.myvisio.svg.attributes;

import org.w3c.dom.svg.SVGPathSeg;
import org.w3c.dom.svg.SVGPathSegLinetoHorizontalAbs;

/**
 * @author bk
 */
public class SVGPathSegLinetoHorizontalAbsImpl extends SVGPathSegImpl implements
		SVGPathSegLinetoHorizontalAbs {

	private static final long serialVersionUID = -6722776032077341870L;
	
	/**
	 * @param x
	 */
	public SVGPathSegLinetoHorizontalAbsImpl(float x) {
		this.x = x;
	}

	@Override
	public short getPathSegType() {
		
		return SVGPathSeg.PATHSEG_LINETO_HORIZONTAL_ABS;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		
		return "H";
	}
}
