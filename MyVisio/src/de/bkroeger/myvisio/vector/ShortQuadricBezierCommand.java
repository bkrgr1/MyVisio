package de.bkroeger.myvisio.vector;

import de.bkroeger.myvisio.utility.TechnicalException;

public class ShortQuadricBezierCommand extends QuadricBezierCurveCommand {

	/**
	 * <p>
	 * Draws a quadratic Bézier curve from the current point to (x,y). 
	 * The control point is assumed to be the reflection of the control point on the previous command 
	 * relative to the current point. (If there is no previous command or if the previous command 
	 * was not a Q, q, T or t, assume the control point is coincident with the current point.) 
	 * T (uppercase) indicates that absolute coordinates will follow; 
	 * t (lowercase) indicates that relative coordinates will follow. 
	 * At the end of the command, the new current point becomes the final (x,y) 
	 * coordinate pair used in the polybézier.
	 * </p>
	 * @param command
	 * @param pinX
	 * @param pinY
	 * @throws TechnicalException 
	 */
	public ShortQuadricBezierCommand(String command, int pinX, int pinY) throws TechnicalException {
		super(command, 0, 0, pinX, pinY);
		if (!command.equalsIgnoreCase("t")) {
			throw new TechnicalException("Invalid command char!");
		}
	}

}
