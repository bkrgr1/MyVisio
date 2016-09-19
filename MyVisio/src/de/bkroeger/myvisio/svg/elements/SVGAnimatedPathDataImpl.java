package de.bkroeger.myvisio.svg.elements;

import java.io.Serializable;

import org.w3c.dom.svg.SVGAnimatedPathData;
import org.w3c.dom.svg.SVGPathSegList;

import de.bkroeger.myvisio.svg.SVGPathSegListImpl;

/**
 * The SVGAnimatedPathData interface supports elements which have a ‘d’ attribute which holds 
 * SVG path data, and supports the ability to animate that attribute. 
 * The SVGAnimatedPathData interface provides two lists to access and modify the base 
 * (i.e., static) contents of the ‘d’ attribute:
 * <ul>
 * <li>DOM attribute pathSegList provides access to the static/base contents of the ‘d’ 
 * attribute in a form which matches one-for-one with SVG's syntax.</li>
 * <li>DOM attribute normalizedPathSegList provides normalized access to the static/base 
 * contents of the ‘d’ attribute where all path data commands are expressed in terms of 
 * the following subset of SVGPathSeg types: SVG_PATHSEG_MOVETO_ABS (M), SVG_PATHSEG_LINETO_ABS 
 * (L), SVG_PATHSEG_CURVETO_CUBIC_ABS (C) and SVG_PATHSEG_CLOSEPATH (z).</li>
 * </ul>
 * and two lists to access the current animated values of the ‘d’ attribute:
 * <ul>
 * <li>DOM attribute animatedPathSegList provides access to the current animated contents 
 * of the ‘d’ attribute in a form which matches one-for-one with SVG's syntax.</li>
 * <li>DOM attribute animatedNormalizedPathSegList provides normalized access to the 
 * current animated contents of the ‘d’ attribute where all path data commands are 
 * expressed in terms of the following subset of SVGPathSeg types: SVG_PATHSEG_MOVETO_ABS (M), 
 * SVG_PATHSEG_LINETO_ABS (L), SVG_PATHSEG_CURVETO_CUBIC_ABS (C) and SVG_PATHSEG_CLOSEPATH (z).</li>
 * </ul>
 * Each of the two lists are always kept synchronized. Modifications to one list will immediately 
 * cause the corresponding list to be modified. Modifications to normalizedPathSegList might 
 * cause entries in pathSegList to be broken into a set of normalized path segments. 
 * Additionally, the ‘d’ attribute on the ‘path’ element accessed via the XML DOM 
 * (e.g., using the getAttribute() method call) will reflect any changes made to pathSegList 
 * or normalizedPathSegList.
 * @author bk
 */
public class SVGAnimatedPathDataImpl implements SVGAnimatedPathData, Serializable {
	
	private static final long serialVersionUID = -6861664471094757797L;
	
	private SVGPathSegList pathSegList = new SVGPathSegListImpl();
    private SVGPathSegList animatedPathSegList = null;
	
	@Override
	public SVGPathSegList getPathSegList() {
		
		return pathSegList;
	}

	@Override
	public SVGPathSegList getNormalizedPathSegList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SVGPathSegList getAnimatedPathSegList() {
		if (animatedPathSegList != null) {
			return animatedPathSegList;
		} else {
			return pathSegList;
		}
	}

	@Override
	public SVGPathSegList getAnimatedNormalizedPathSegList() {
		// TODO Auto-generated method stub
		return null;
	}

}
