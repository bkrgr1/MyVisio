package de.bkroeger.myvisio.utility;

import de.bkroeger.myvisio.svg.elements.SVGElementImpl;

public interface Traversator {

	void doPerNode(SVGElementImpl node);
}
