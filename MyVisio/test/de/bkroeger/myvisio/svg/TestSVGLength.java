package de.bkroeger.myvisio.svg;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.svg.SVGLength;

public class TestSVGLength {

	@Test
	public void testConstructor1() {
		
		SVGLength length = new SVGLengthImpl();
		Assert.assertEquals(0.0f, length.getValueInSpecifiedUnits(), 0.0001);
		Assert.assertEquals(SVGLength.SVG_LENGTHTYPE_UNKNOWN, length.getUnitType());
	}
		

	@Test
	public void testConstructor2() {
		
		SVGLength length = new SVGLengthImpl(SVGLengthImpl.SVG_LENGTHTYPE_MM);
		Assert.assertEquals(0.0f, length.getValueInSpecifiedUnits(), 0.0001);
		Assert.assertEquals(SVGLength.SVG_LENGTHTYPE_MM, length.getUnitType());
	}
	

	@Test
	public void testConstructor3() {
		
		SVGLength length = new SVGLengthImpl("4.125in");
		Assert.assertEquals(4.125, length.getValueInSpecifiedUnits(), 0.0001);
		Assert.assertEquals(SVGLength.SVG_LENGTHTYPE_IN, length.getUnitType());
	}

	@Test
	public void testValueAsString1() {
		
		SVGLength length = new SVGLengthImpl();
		length.setValueAsString("12.45cm");
		Assert.assertEquals(12.45, length.getValueInSpecifiedUnits(), 0.0001);
		Assert.assertEquals(SVGLength.SVG_LENGTHTYPE_CM, length.getUnitType());
	}
	
	@Test
	public void testToString() {
		
		SVGLength length = new SVGLengthImpl();
		length.setValueAsString("12.45cm");
		System.out.println(length.toString());
	}
}
