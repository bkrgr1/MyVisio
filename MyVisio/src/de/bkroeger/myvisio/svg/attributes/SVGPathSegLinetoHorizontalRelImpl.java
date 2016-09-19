package de.bkroeger.myvisio.svg.attributes;

import org.w3c.dom.svg.SVGPathSeg;
import org.w3c.dom.svg.SVGPathSegLinetoHorizontalRel;

/**
 * @author bk
 */
public class SVGPathSegLinetoHorizontalRelImpl extends SVGPathSegImpl implements
		SVGPathSegLinetoHorizontalRel {

	private static final long serialVersionUID = -6722776032077341870L;
	
	/**
	 * @param x
	 */
	public SVGPathSegLinetoHorizontalRelImpl(float x) {
		this.x = x;
	}

	@Override
	public short getPathSegType() {
		
		return SVGPathSeg.PATHSEG_LINETO_HORIZONTAL_REL;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		
		return "h";
	}
}
