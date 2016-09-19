package org.w3c.dom.svg;

import java.awt.Graphics2D;

/**
 * Marker-Interface für Shape-Elemente
 * 
 * @author bk
 */
public interface SVGShapeElement extends SVGGraphicalElement {

	public void paint(Graphics2D g2d);
}
