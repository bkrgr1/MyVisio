package de.bkroeger.myvisio.vector;

import de.bkroeger.myvisio.utility.TechnicalException;

public class VerticalLineToCommand extends LineToCommand {

	/**
	 * <p>
	 * Draws a vertical line from the current point (cpx, cpy) to (cpx, y). 
	 * V (uppercase) indicates that absolute coordinates will follow; 
	 * v (lowercase) indicates that relative coordinates will follow. 
	 * Multiple y values can be provided (although usually this doesn't make sense). 
	 * At the end of the command, the new current point becomes (cpx, y) for the final value of y.
	 * </p>
	 * @param command
	 * @param pinY
	 * @param pos
	 * @throws TechnicalException
	 */
	public VerticalLineToCommand(String command, int pinY, int... pos)
			throws TechnicalException {
		super(command, 0, pinY, pos);
		if (!command.equalsIgnoreCase("v")) {
			throw new TechnicalException("Invalid command char!");
		}
	}

}
