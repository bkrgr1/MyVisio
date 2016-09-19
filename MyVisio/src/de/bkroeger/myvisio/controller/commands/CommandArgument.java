package de.bkroeger.myvisio.controller.commands;

/**
 * Interface für Befehlsargumente.
 * 
 * @author bk
 */
public interface CommandArgument {

	/**
	 * Liefert ein Array der Argumente
	 * @return
	 */
	public Object[] getArguments(); 

	/**
	 * Setzt das Array der Argumente.
	 * @param params
	 */
	public void setArguments(Object... params);
}
