package de.bkroeger.myvisio.controller.commands;

import java.io.File;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.bkroeger.myvisio.model.ApplicationModel;
import de.bkroeger.myvisio.model.Workbook;
import de.bkroeger.myvisio.utility.TechnicalException;
import de.bkroeger.myvisio.view.ApplicationView;

/**
 * Implementiert die Logik zum Speichern einer Visio-Datei in
 * einem neu gewählten Dateipfad.
 * 
 * @author bk
 */
public class SelectFileCommand implements Command {
	
	Logger logger = Logger.getLogger(SelectFileCommand.class.getName());
	
	private ApplicationModel applicationModel;

	/**
	 * Öffnet einen Dialog zur Eingabe/Auswahl einer zu speichernden Datei.
	 * Der Dateipfad wird in das übergebene VisioDocument eingetragen.
	 * 
	 * @arg - das Argument für diesen Befehl; muss vom Typ {@link DocumentArgument} sein.
	 */
	
	public void execute(Object arg) throws TechnicalException {
		
		if (arg != null && arg instanceof DocumentArgument) {
			DocumentArgument docArg = (DocumentArgument)arg;
			Workbook visioDoc = docArg.getDocument();
			if (visioDoc != null) {
				
				// den Hauptframe der Anwendung ermitteln
				ApplicationView applicationView = applicationModel.getMainController().getView();
					
				// Dialogparameter einstellen
				final JFileChooser fileChooser = new JFileChooser();
				fileChooser.setAcceptAllFileFilterUsed(false);
//				fileChooser.setCurrentDirectory(dir);
				fileChooser.setDialogTitle("Save document as...");
				fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
				fileChooser.setMultiSelectionEnabled(false);
				FileNameExtensionFilter filter = 
					new FileNameExtensionFilter("mvd", "xml");
				fileChooser.setFileFilter(filter);
				
				// den Dialog anzeigen
				int returnVal = fileChooser.showSaveDialog(applicationView);
				
				// wurde ein Pfad angegeben?
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		        	
		        	// Dateipfad eintragen
		            File file = fileChooser.getSelectedFile();
		            visioDoc.setFileName(file.getAbsolutePath());
					// und Titel aktualisieren
		            visioDoc.setTitle(file.getName());
		            
		        } else {
		            logger.info("FileSelect command cancelled by user.");
		            return;
		        }
			} else {
				throw new TechnicalException("DocumentArgument does not contain a VisioDocument.");
			}
		} else {
			throw new TechnicalException("Command argument missing or invalid.");
		}
	}
}
