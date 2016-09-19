package de.bkroeger.myvisio.vector;

import de.bkroeger.myvisio.utility.TechnicalException;

public class ShortCubicBezierCommand extends CubicBezierCurveCommand {

	/**
	 * <p>
	 * Draws a cubic Bézier curve from the current point to (x,y). 
	 * The first control point is assumed to be the reflection of the second control point 
	 * on the previous command relative to the current point. 
	 * (If there is no previous command or if the previous command was not an C, c, S or s, 
	 * assume the first control point is coincident with the current point.) 
	 * (x2,y2) is the second control point (i.e., the control point at the end of the curve). 
	 * S (uppercase) indicates that absolute coordinates will follow; 
	 * s (lowercase) indicates that relative coordinates will follow. 
	 * Multiple sets of coordinates may be specified to draw a polybézier. 
	 * At the end of the command, the new current point becomes the final (x,y) 
	 * coordinate pair used in the polybézier.
	 * </p>
	 * @param command
	 * @param x2
	 * @param y2
	 * @param pinX
	 * @param pinY
	 * @throws TechnicalException 
	 */
	public ShortCubicBezierCommand(String command, int x2,
			int y2, int pinX, int pinY) throws TechnicalException {
		super(command, 0, 0, x2, y2, pinX, pinY);
		if (!command.equalsIgnoreCase("s")) {
			throw new TechnicalException("Invalid command char!");
		}
	}

}
