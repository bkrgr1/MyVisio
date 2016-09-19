package de.bkroeger.myvisio.vector;

import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * <p>
 * The "closepath" (Z or z) ends the current subpath and 
 * causes an automatic straight line to be drawn from 
 * the current point to the initial point of the current subpath. 
 * If a "closepath" is followed immediately by a "moveto", 
 * then the "moveto" identifies the start point of the next subpath. 
 * If a "closepath" is followed immediately by any other command, 
 * then the next subpath starts at the same initial point as the current subpath.
 * </p><p>
 * When a subpath ends in a "closepath," it differs in behavior from 
 * what happens when "manually" closing a subpath via a "lineto" command 
 * in how ‘stroke-linejoin’ and ‘stroke-linecap’ are implemented. 
 * With "closepath", the end of the final segment of the subpath is "joined" 
 * with the start of the initial segment of the subpath using the current value of ‘stroke-linejoin’. 
 * If you instead "manually" close the subpath via a "lineto" command, 
 * the start of the first segment and the end of the last segment are not joined 
 * but instead are each capped using the current value of ‘stroke-linecap’. 
 * At the end of the command, the new current point is set to the initial point of the current subpath.
 * </p>
 * @author bk
 *
 */
public final class ClosePathCommand extends BaseVectorCommand {

	/**
	 * <p>
	 * Close the current subpath by drawing a straight line 
	 * from the current point to current subpath's initial point. 
	 * Since the Z and z commands take no parameters, they have an identical effect.
	 * </p>
	 * @param command
	 * @throws TechnicalException 
	 */
	public ClosePathCommand(String command) throws TechnicalException {
		super(command, 0, 0);
		if (!command.equalsIgnoreCase("Z")) {
			throw new TechnicalException("Invalid command char: "+command);
		}
	}

}
