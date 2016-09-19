package de.bkroeger.myvisio.svg;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.svg.SVGTransformList;
import de.bkroeger.myvisio.svg.SVGTransformListImpl;

public class TestSVGTransformList {

	@Test
	public void testConstructorString() {
		String value = "matrix (-10 ,-20, 0, 0, +20, +10)  scale(2) , rotate(45) translate(5,10)";
		SVGTransformList transforms = new SVGTransformListImpl(value);
		Assert.assertEquals(4, transforms.getNumberOfItems());
	}
}
