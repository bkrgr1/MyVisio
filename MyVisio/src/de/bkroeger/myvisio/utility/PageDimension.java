package de.bkroeger.myvisio.utility;


/**
 * @author bk
 */
public class PageDimension {
	
	protected static final int PIXEL_PER_INCH = 72;
	protected static final double CM_PER_INCH = 2.54;
	
	/**
	 * @param name 
	 * @return - PageDimension
	 */
	public static PageDimension parseXML(String name) {
		
		switch (name) {
		case "DINA4":
			return new DINA4();
		default:
			return null;
		}
	}
}
