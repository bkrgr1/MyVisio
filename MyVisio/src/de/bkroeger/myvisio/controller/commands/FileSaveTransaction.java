package de.bkroeger.myvisio.controller.commands;

import java.util.Vector;

public class FileSaveTransaction extends Transaction {

	public FileSaveTransaction(Vector<String> commandnamelist, Vector<CommandArgument> commandargumentlist) {
		super(commandnamelist, commandargumentlist);
	}

	public FileSaveTransaction(String command, CommandArgument argument) {
		super(command, argument);
	}
}
