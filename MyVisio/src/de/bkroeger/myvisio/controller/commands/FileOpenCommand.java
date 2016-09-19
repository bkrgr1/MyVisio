package de.bkroeger.myvisio.controller.commands;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import de.bkroeger.myvisio.model.ApplicationModel;
import de.bkroeger.myvisio.model.Workbook;
import de.bkroeger.myvisio.utility.TechnicalException;

public class FileOpenCommand implements Command {
	
	private ApplicationModel applicationModel;
	
	public void execute(Object arg) throws TechnicalException {
		
		Workbook visioDoc = null;
		//Create a file chooser
		final JFileChooser fc = new JFileChooser();
		
		//In response to a button click:
		int returnVal = fc.showOpenDialog(applicationModel.getMainController().getView());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
        	
            File file = fc.getSelectedFile();
            // neues VisioDocument erzeugen
            visioDoc = new Workbook(file.getAbsolutePath());
			try {
				
				// Dokument laden
				visioDoc.load(file);
				
				// Dokument zur Map hinzufügen
				visioDoc.setTitle(file.getName());
//				this.putDocument(visioDoc);
				
			} catch(TechnicalException e) {
				// Fehlermeldung anzeigen
				JOptionPane.showMessageDialog(applicationModel.getMainController().getView(),
					    e.getMessage(),
					    "Load Error",
					    JOptionPane.ERROR_MESSAGE); 
			}
            
        } else {
//            logger.info("Open command cancelled by user.");
        }
	}
}
