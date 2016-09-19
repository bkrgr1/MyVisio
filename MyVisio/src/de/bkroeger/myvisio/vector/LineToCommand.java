package de.bkroeger.myvisio.vector;

import org.w3c.dom.Element;

import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * <p>
 * The various "lineto" commands draw straight lines from the current point to a new point.
 * </p>
 * @author bk
 */
public class LineToCommand extends BaseVectorCommand {

	/**
	 * <p>
	 * lineto: Draw a line from the current point to the given (x,y) coordinate 
	 * which becomes the new current point. 
	 * L (uppercase) indicates that absolute coordinates will follow; 
	 * l (lowercase) indicates that relative coordinates will follow. 
	 * A number of coordinates pairs may be specified to draw a polyline. 
	 * At the end of the command, the new current point is set to the final set of coordinates provided.
	 * </p><p>
	 * @throws TechnicalException !
	 */
	public LineToCommand(String command, int pinX, int pinY, int ...pos) throws TechnicalException {
		super(command, pinX, pinY);
		if (!command.equalsIgnoreCase("l")) {
			throw new TechnicalException("Invalid command char: "+command);
		}
		if (pos.length > 0 && (pos.length % 2) != 0) {
			throw new TechnicalException("Invalid number of additional parameters");
		}
	}

	public LineToCommand(String c, Element elem) {
		super(c, elem);
		
		// TODO: weitere Positionen einlesen
	}

}
