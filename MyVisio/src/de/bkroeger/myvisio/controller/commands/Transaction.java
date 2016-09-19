package de.bkroeger.myvisio.controller.commands;

import java.util.Vector;

import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * <p>
 * Ein TransactionCommand sammelt mehrere Commands und führt sie zusammen aus.
 * </p><p>
 * Er trennt einen Befehl von der Klasse, die den Befehl ausführt.
 * Die Ausführung wird an einen Befehl aus dem Package "de.bkroeger.myvisio.commands" delegiert.
 * Der Name des Befehls wird in der Befehlsliste übergeben.
 * Zu jedem Befehl muss ein CommandArgument in der Argumentliste bereitgestellt werden (oder null).
 * </p>
 * @author bk
 */
public class Transaction {
	
	private Vector<String> commandNameList;
	private Vector<CommandArgument> commandArgumentList;
	private String cmdName;
	private Command command;

	/**
	 * Default-Konstruktor
	 */
	public Transaction() {
		
		this.commandNameList = new Vector<String>();
		this.commandArgumentList = new Vector<CommandArgument>();
	}

	/**
	 * Konstruktor mit Liste der auszuführenden Befehle und deren Argumente.
	 * 
	 * @param commandnamelist
	 * @param commandargumentlist
	 */
	public Transaction(Vector<String> commandnamelist, Vector<CommandArgument> commandargumentlist) {
		
		this.commandNameList = commandnamelist;
		this.commandArgumentList = commandargumentlist;
	}
	
	/**
	 * Konstruktor mit einzelnem Befehl/Argument.
	 * 
	 * @param name
	 * @param argument
	 */
	public Transaction(String name, CommandArgument argument) {
		
		this.commandNameList = new Vector<String>();
		this.commandNameList.add(name);
		this.commandArgumentList = new Vector<CommandArgument>();
		this.commandArgumentList.add(argument);
	}

	/**
	 * <p>
	 * Befehle in der Liste abarbeiten.
	 * </p><p>
	 * Der Command wird dynamisch instanziiert und dann die execute()-Methode
	 * mit dem Command-Parameter aufgerufen.
	 * </p>
	 * @throws TechnicalException 
	 */
	public void execute() throws TechnicalException {
		
		for (int i = 0; i < commandNameList.size(); i++) {
			cmdName = commandNameList.get(i);
			Object cmdArg = commandArgumentList.get(i);
			try {
				String classname = String.format("%s.%sCommand",
						this.getClass().getPackage().getName(), cmdName);
				Class<?> cls = Class.forName(classname);
				command = (Command) cls.newInstance();
			} catch (Throwable e) {
				System.err.println(e);
			}
			command.execute(cmdArg);
		}
	}
}
