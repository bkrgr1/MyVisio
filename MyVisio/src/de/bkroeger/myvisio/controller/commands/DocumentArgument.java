package de.bkroeger.myvisio.controller.commands;

import de.bkroeger.myvisio.controller.commands.CommandArgument;
import de.bkroeger.myvisio.model.Workbook;

/**
 * Befehls-Argument für ein Visio-Dokument.
 * 
 * @author bk
 */
public class DocumentArgument implements CommandArgument {
	
	private Workbook visioDoc;
	public  Workbook getDocument() { return this.visioDoc; }
	
	/**
	 * Konstruktor
	 * @param doc
	 */
	public DocumentArgument(Workbook doc) {
		
		setArguments(doc);
	}

	
	public Object[] getArguments() {
		
		return new Object[] {this.visioDoc};
	}

	
	public void setArguments(Object... params) {
		
		if (params.length > 0)
			if (params[0] instanceof Workbook)
				this.visioDoc = (Workbook) params[0];
	}

}
