package de.bkroeger.myvisio.vector;

import de.bkroeger.myvisio.utility.TechnicalException;

public class HorizontalLineToCommand extends LineToCommand {

	/**
	 * <p>
	 * Draws a horizontal line from the current point (cpx, cpy) to (x, cpy). 
	 * H (uppercase) indicates that absolute coordinates will follow; 
	 * h (lowercase) indicates that relative coordinates will follow. 
	 * Multiple x values can be provided (although usually this doesn't make sense). 
	 * At the end of the command, the new current point becomes (x, cpy) for the final value of x.
	 * </p>
	 * @param command
	 * @param pinX
	 * @param pos
	 * @throws TechnicalException
	 */
	public HorizontalLineToCommand(String command, int pinX, int ...pos)
			throws TechnicalException {
		super(command, pinX, 0, pos);
	}

}
