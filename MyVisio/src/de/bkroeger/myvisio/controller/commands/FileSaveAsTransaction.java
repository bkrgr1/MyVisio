package de.bkroeger.myvisio.controller.commands;

import java.util.Vector;

public class FileSaveAsTransaction extends Transaction {

	public FileSaveAsTransaction(Vector<String> commandnamelist, Vector<CommandArgument> commandargumentlist) {
		super(commandnamelist, commandargumentlist);
	}

	public FileSaveAsTransaction(String command, CommandArgument argument) {
		super(command, argument);
	}
}
