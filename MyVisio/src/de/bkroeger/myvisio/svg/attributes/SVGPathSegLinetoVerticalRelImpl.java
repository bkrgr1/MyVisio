package de.bkroeger.myvisio.svg.attributes;

import org.w3c.dom.svg.SVGPathSeg;
import org.w3c.dom.svg.SVGPathSegLinetoVerticalRel;

/**
 * @author bk
 */
public class SVGPathSegLinetoVerticalRelImpl extends SVGPathSegImpl implements
		SVGPathSegLinetoVerticalRel {

	private static final long serialVersionUID = -6722776032077341870L;
	
	/**
	 * @param y
	 */
	public SVGPathSegLinetoVerticalRelImpl(float y) {
		this.y = y;
	}

	@Override
	public short getPathSegType() {
		
		return SVGPathSeg.PATHSEG_LINETO_VERTICAL_REL;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		
		return "v";
	}
}
