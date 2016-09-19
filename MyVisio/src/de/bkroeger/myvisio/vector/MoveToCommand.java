package de.bkroeger.myvisio.vector;

import org.w3c.dom.Element;

import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * <p>
 * The "moveto" commands (M or m) establish a new current point. 
 * The effect is as if the "pen" were lifted and moved to a new location. 
 * A path data segment (if there is one) must begin with a "moveto" command. 
 * Subsequent "moveto" commands (i.e., when the "moveto" is not the first command) 
 * represent the start of a new subpath
 * </p>
 * @author bk
 */
public class MoveToCommand extends BaseVectorCommand {

	/**
	 * <p>
	 * Start a new sub-path at the given (x,y) coordinate. 
	 * M (uppercase) indicates that absolute coordinates will follow; 
	 * m (lowercase) indicates that relative coordinates will follow. 
	 * If a moveto is followed by multiple pairs of coordinates, 
	 * the subsequent pairs are treated as implicit lineto commands. 
	 * Hence, implicit lineto commands will be relative if the moveto is relative, 
	 * and absolute if the moveto is absolute. 
	 * If a relative moveto (m) appears as the first element of the path, 
	 * then it is treated as a pair of absolute coordinates. 
	 * In this case, subsequent pairs of coordinates are treated as relative 
	 * even though the initial moveto is interpreted as an absolute moveto.
	 * </p>
	 * @param command
	 * @param pinX
	 * @param pinY
	 * @param pos
	 * @throws TechnicalException 
	 */
	public MoveToCommand(String command, int pinX, int pinY, int ... pos) throws TechnicalException {
		super(command, pinX, pinY);
		if (!command.equalsIgnoreCase("m")) {
			throw new TechnicalException("Invalid command char: "+command);
		}
		if (pos.length > 0 && (pos.length % 2) != 0) {
			throw new TechnicalException("Invalid number of additional parameters");
		}
	}

	public MoveToCommand(String c, Element elem) {
		super(c, elem);
		
		// TODO: weitere Positionen einlesen
	}
}
