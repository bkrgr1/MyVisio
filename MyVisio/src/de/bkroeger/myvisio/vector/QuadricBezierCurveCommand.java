package de.bkroeger.myvisio.vector;

import de.bkroeger.myvisio.utility.TechnicalException;

public class QuadricBezierCurveCommand extends BaseVectorCommand {

	/**
	 * <p>
	 * Draws a quadratic Bézier curve from the current point to (x,y) 
	 * using (x1,y1) as the control point. 
	 * Q (uppercase) indicates that absolute coordinates will follow; 
	 * q (lowercase) indicates that relative coordinates will follow. 
	 * Multiple sets of coordinates may be specified to draw a polybézier. 
	 * At the end of the command, the new current point becomes the final (x,y) 
	 * coordinate pair used in the polybézier.
	 * </p>
	 * @param command
	 * @param x1
	 * @param y1
	 * @param pinX
	 * @param pinY
	 * @throws TechnicalException 
	 */
	public QuadricBezierCurveCommand(String command, int x1, int y1, int pinX, int pinY) throws TechnicalException {
		super(command, pinX, pinY);
		if (!command.equalsIgnoreCase("q")) {
			throw new TechnicalException("Invalid command char!");
		}
	}

}
