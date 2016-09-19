package de.bkroeger.myvisio.svg.attributes;

import org.w3c.dom.svg.SVGPathSeg;
import org.w3c.dom.svg.SVGPathSegLinetoVerticalAbs;

/**
 * @author bk
 */
public class SVGPathSegLinetoVerticalAbsImpl extends SVGPathSegImpl implements
		SVGPathSegLinetoVerticalAbs {

	private static final long serialVersionUID = -6722776032077341870L;
	
	/**
	 * @param y
	 */
	public SVGPathSegLinetoVerticalAbsImpl(float y) {
		this.y = y;
	}

	@Override
	public short getPathSegType() {
		
		return SVGPathSeg.PATHSEG_LINETO_VERTICAL_ABS;
	}

	@Override
	public String getPathSegTypeAsLetter() {
		
		return "V";
	}
}
