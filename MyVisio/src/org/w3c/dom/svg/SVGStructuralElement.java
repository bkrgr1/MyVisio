package org.w3c.dom.svg;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 * Marker-Interface für Structural Elements wie "g", "svg"
 * 
 * @author bk
 */
public interface SVGStructuralElement extends SVGContainerElement {

	public void setTransformList(SVGAnimatedTransformList transform);
	
	public SVGAnimatedTransformList getTransformList();
	
	public void paint(Graphics2D g2d);

	public Point2D.Float convertCoordinate(Point2D.Float p);
}
