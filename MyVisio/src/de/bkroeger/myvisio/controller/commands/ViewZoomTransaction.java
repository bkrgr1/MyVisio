package de.bkroeger.myvisio.controller.commands;

import java.util.Vector;

public class ViewZoomTransaction extends Transaction {

	public ViewZoomTransaction(Vector<String> commandnamelist, Vector<CommandArgument> commandargumentlist) {
		super(commandnamelist, commandargumentlist);
	}

	public ViewZoomTransaction(String command, CommandArgument argument) {
		super(command, argument);
	}
}
