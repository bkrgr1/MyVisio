package de.bkroeger.myvisio.controller.commands;

import java.util.logging.Logger;

import javax.swing.JOptionPane;

import de.bkroeger.myvisio.model.ApplicationModel;
import de.bkroeger.myvisio.model.Workbook;
import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * Implementiert die Logik zum Speichern einer VisioDocument in
 * dem gespeicherten File-Path.
 * 
 * @author bk
 */
public class FileSaveAsCommand implements Command {
	
	Logger logger = Logger.getLogger(FileSaveAsCommand.class.getName());
	
	private ApplicationModel applicationModel;

	/**
	 * Speichert die Visio-Datei in einem neu gewählten Verzeichnis.
	 * 
	 * @arg - das Argument für diesen Befehl; muss vom Typ {@link DocumentArgument} sein.
	 */
	
	public void execute(Object arg) throws TechnicalException {
		
		if (arg != null && arg instanceof DocumentArgument) {
			DocumentArgument docArg = (DocumentArgument)arg;
			Workbook visioDoc = docArg.getDocument();
			if (visioDoc != null) {
				
				// ist ein Filepath vorhanden?
				if (visioDoc.getFileName() != null && !visioDoc.getFileName().isEmpty()) {				
				
					// den Hauptframe der Anwendung ermitteln
					try {
						// Dokument speichern
						visioDoc.store();
						
					} catch(TechnicalException e) {
						JOptionPane.showMessageDialog(applicationModel.getMainController().getView(),
							    e.getMessage(),
							    "Store Error",
							    JOptionPane.ERROR_MESSAGE); 
					}
				} else {
					throw new TechnicalException("File name missing.");
				}
			} else {
				throw new TechnicalException("VisioDocument missing.");
			}
		} else {
			throw new TechnicalException("Command argument missing or invalid.");
		}
	}
}
