package de.bkroeger.myvisio.controller.commands;

import javax.swing.JOptionPane;

import de.bkroeger.myvisio.model.ApplicationModel;
import de.bkroeger.myvisio.model.Workbook;
import de.bkroeger.myvisio.utility.TechnicalException;

public class FileSaveCommand implements Command {
	
	private ApplicationModel applicationModel;
	
	public void execute(Object arg) throws TechnicalException {
		
		if (arg instanceof DocumentArgument) {
			
			Workbook visioDoc = ((DocumentArgument)arg).getDocument();
			if (visioDoc.getFileName() != null && !visioDoc.getFileName().isEmpty()) {
				
				// Datei speichern
				try {
					visioDoc.store();
					
				} catch(TechnicalException e) {
					JOptionPane.showMessageDialog(applicationModel.getMainController().getView(),
						    e.getMessage(),
						    "Store Error",
						    JOptionPane.ERROR_MESSAGE); 
				}
			} else {
				
				// FileAs aufrufen
//				saveFileAs();
			}
		} else {
			throw new TechnicalException("Invalid argument type: "+arg.getClass().getName());
		}
	}
}
