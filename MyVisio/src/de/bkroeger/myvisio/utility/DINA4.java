package de.bkroeger.myvisio.utility;

import java.awt.Dimension;


/**
 * 210 mm x 297 mm; Auflösung 72px/inch
 */
public class DINA4 extends PageDimension {

	private static final double DINA4_HEIGHT = 29.7;
	private static final double DINA4_WIDTH = 21.0;

	/**
	 * @return - Dimension
	 */
	public Dimension getDimension() {
		return new Dimension((int)(DINA4_WIDTH/CM_PER_INCH*PIXEL_PER_INCH),
				(int)(DINA4_HEIGHT/CM_PER_INCH*PIXEL_PER_INCH));
	}
}
