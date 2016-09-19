package org.w3c.dom.svg;

import java.awt.Graphics2D;

public interface SVGGraphicalElement extends SVGElement {

    // Erweiterungen
//	public SVGNode getNode();
//	public void    setNode(SVGNode node);
	public void paint(Graphics2D g2d);
}
