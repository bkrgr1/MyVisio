package de.bkroeger.myvisio.svg.elements;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import org.w3c.dom.svg.SVGGraphicalElement;
import org.w3c.dom.svg.SVGShapeElement;

/**
 * <p>
 * One of the element types that can cause graphics to be drawn onto the target canvas. 
 * Specifically: ‘circle’, ‘ellipse’, ‘image’, ‘line’, ‘path’, ‘polygon’, ‘polyline’, 
 * ‘rect’, ‘text’ and ‘use’.
 * </p>
 * @author bk
 */
public class SVGGraphicalElementImpl extends SVGElementImpl implements SVGGraphicalElement {

	private static final long serialVersionUID = -3783954627101896514L;

	/**
	 * Default-Konstruktor
	 */
	public SVGGraphicalElementImpl() {
		// Auto-generated constructor stub
	}
	
	/**
	 * @param p1
	 * @return - converted Point
	 */
	public Point2D.Float convertCoordinate(Point2D.Float p1) {
		return null;
	}

	public void paint(Graphics2D g2d) {
		
		for (int i=0; i<this.getChildCount(); i++) {
			SVGElementImpl childNode = (SVGElementImpl) this.getChildAt(i);
			SVGShapeElement shape = (SVGShapeElement) childNode;
			shape.paint(g2d);
		}
	}
}
