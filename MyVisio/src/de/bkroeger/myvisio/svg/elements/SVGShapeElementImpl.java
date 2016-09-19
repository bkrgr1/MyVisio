package de.bkroeger.myvisio.svg.elements;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.logging.Logger;

import org.w3c.dom.svg.SVGBasicShapeElement;
import org.w3c.dom.svg.SVGPathElement;
import org.w3c.dom.svg.SVGShapeElement;


/**
 * <p>
 * A graphics element that is defined by some combination of straight lines and curves. 
 * Specifically: ‘path’, ‘rect’, ‘circle’, ‘ellipse’, ‘line’, ‘polyline’ and ‘polygon’.
 * </p>
 * @author bk
 */
public class SVGShapeElementImpl extends SVGGraphicalElementImpl 
	implements SVGShapeElement {
	
	private static final long serialVersionUID = -3620038624309277155L;

	private static final Logger logger = Logger.getLogger(SVGShapeElementImpl.class.getName());

	/**
	 * Flag, ob dies Shape ausgewählt ist.
	 */
	protected boolean isSelectedFlag;
	public boolean isSelected() { return isSelectedFlag; }
	public void setSelected(boolean value) { isSelectedFlag = value; }

	/**
	 * Default-Constructor
	 */
	public SVGShapeElementImpl() { 
		super();
	}
	
	
	public Object clone() {
		return null;
	}

	
	public void paint(Graphics2D g2d) {
		
		for (int i=0; i<this.getChildCount(); i++) {
			SVGElementImpl childNode = (SVGElementImpl) this.getChildAt(i);
			
			if (childNode instanceof SVGBasicShapeElement) {
				SVGBasicShapeElement basic = (SVGBasicShapeElement) childNode;
				basic.paint(g2d);
			} else if (childNode instanceof SVGPathElement) {
				SVGPathElement path = (SVGPathElement) childNode;
				path.paint(g2d);
			}
		}
	}
	
	public boolean containsPoint(Point.Float p) {
		logger.info("ShapeElement contains point="+p.toString()+": false");
		return false;
	}
}
