package de.bkroeger.myvisio.controller.commands;

import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * Interface f�r einen einzelnen Befehl.
 * 
 * @author bk
 */
public interface Command {

	/**
	 * F�hrt den Befehl aus
	 * @throws TechnicalException 
	 */
	public void execute(Object param) throws TechnicalException;
}
