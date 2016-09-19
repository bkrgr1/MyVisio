package de.bkroeger.myvisio.controller.commands;

import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * Interface für einen einzelnen Befehl.
 * 
 * @author bk
 */
public interface Command {

	/**
	 * Führt den Befehl aus
	 * @throws TechnicalException 
	 */
	public void execute(Object param) throws TechnicalException;
}
