package de.bkroeger.myvisio.controller.commands;


/**
 * Befehls-Argument für ein Visio-Dokument.
 * 
 * @author bk
 */
public class HelpArgument implements CommandArgument {
	
	private int helpId = 0;
	public static final int HelpZoomDialogId = 101;
	
	/**
	 * Konstruktor
	 * @param doc
	 */
	public HelpArgument(Integer helpId) {
		
		setArguments(helpId);
	}

	
	public Object[] getArguments() {
		
		return new Object[] {this.helpId};
	}

	
	public void setArguments(Object... params) {
		
		if (params.length > 0)
			if (params[0] instanceof Integer)
				this.helpId = (Integer) params[0];
	}

}
