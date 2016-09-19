package de.bkroeger.myvisio.controller.commands;

import java.util.Vector;

public class HelpTransaction extends Transaction {

	public HelpTransaction(Vector<String> commandnamelist, Vector<CommandArgument> commandargumentlist) {
		super(commandnamelist, commandargumentlist);
	}

	public HelpTransaction(String command, CommandArgument argument) {
		super(command, argument);
	}
}
