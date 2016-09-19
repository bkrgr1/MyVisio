package de.bkroeger.myvisio.vector;

import de.bkroeger.myvisio.utility.TechnicalException;

public class CubicBezierCurveCommand extends BaseVectorCommand {

	/**
	 * <p>
	 * Draws a cubic Bézier curve from the current point to (x,y) using (x1,y1) 
	 * as the control point at the beginning of the curve and (x2,y2) 
	 * as the control point at the end of the curve. 
	 * C (uppercase) indicates that absolute coordinates will follow; 
	 * c (lowercase) indicates that relative coordinates will follow. 
	 * Multiple sets of coordinates may be specified to draw a polybézier. 
	 * At the end of the command, the new current point becomes the final (x,y) 
	 * coordinate pair used in the polybézier.
	 * </p>
	 * @param command
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param pinX
	 * @param pinY
	 * @throws TechnicalException 
	 */
	public CubicBezierCurveCommand(String command, int x1, int y1, int x2, int y2, int pinX, int pinY) throws TechnicalException {
		super(command, pinX, pinY);
		if (!command.equalsIgnoreCase("c")) {
			throw new TechnicalException("Invalid command char!");
		}
	}

}
