package de.bkroeger.myvisio.vector;

import org.w3c.dom.Element;

public class BaseVectorCommand {
	
	protected String command;
	protected int pinX;
	protected int pinY;

	public BaseVectorCommand(String command, int pinX, int pinY) {
		
		this.setCommand(command);
		this.setPinX(pinX);
		this.setPinY(pinY);
	}
	
	public BaseVectorCommand(String command, Element elem) {
		
		this.setCommand(command);
		this.setPinX(Integer.parseInt(elem.getAttribute("x")));
		this.setPinY(Integer.parseInt(elem.getAttribute("y")));
	}
	
	public int getPinX() {
		return pinX;
	}

	public void setPinX(int pinX) {
		this.pinX = pinX;
	}

	public int getPinY() {
		return pinY;
	}

	public void setPinY(int pinY) {
		this.pinY = pinY;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

}
