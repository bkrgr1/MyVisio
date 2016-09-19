package de.bkroeger.myvisio.svg.elements;

import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.svg.SVGAnimatedBoolean;
import org.w3c.dom.svg.SVGAnimatedString;
import org.w3c.dom.svg.SVGDefsElement;
import org.w3c.dom.svg.SVGElement;
import org.w3c.dom.svg.SVGMatrix;
import org.w3c.dom.svg.SVGRect;
import org.w3c.dom.svg.SVGStringList;


/**
 * <p>
 * The ‘defs’ element is a container element for referenced elements. 
 * For understandability and accessibility reasons, it is recommended that, 
 * whenever possible, referenced elements be defined inside of a ‘defs’.
 * </p><p>
 * The content model for ‘defs’ is the same as for the ‘g’ element; thus, 
 * any element that can be a child of a ‘g’can also be a child of a ‘defs’, and vice versa.
 * </p><p>
 * Elements that are descendants of a ‘defs’ are not rendered directly; 
 * they are prevented from becoming part of the rendering tree just as if the ‘defs’ element 
 * were a ‘g’ element and the ‘display’ property were set to none. Note, however, 
 * that the descendants of a ‘defs’ are always present in the source tree and 
 * thus can always be referenced by other elements; thus, the value of the ‘display’ 
 * property on the ‘defs’ element or any of its descendants does not 
 * prevent those elements from being referenced by other elements.
 * </p>
 * @author bk
 */
public class SVGDefsElementImpl extends SVGStructuralElementImpl implements SVGDefsElement {

	private static final long serialVersionUID = -4939139070437836109L;

	public SVGDefsElementImpl() {
		super();
	}

	
	public SVGStringList getRequiredFeatures() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGStringList getRequiredExtensions() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGStringList getSystemLanguage() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public boolean hasExtension(String extension) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public String getXMLlang() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setXMLlang(String XMLlang) {
		// TODO Auto-generated method stub

	}

	
	public String getXMLspace() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setXMLspace(String XMLspace) {
		// TODO Auto-generated method stub

	}

	
	public SVGAnimatedBoolean getExternalResourcesRequired() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGAnimatedString getClassName() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public CSSStyleDeclaration getStyle() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public CSSValue getPresentationAttribute(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGElement getNearestViewportElement() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGElement getFarthestViewportElement() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGRect getBBox() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGMatrix getCTM() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGMatrix getScreenCTM() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public SVGMatrix getTransformToElement(SVGElement element) {
		// TODO Auto-generated method stub
		return null;
	}

}
