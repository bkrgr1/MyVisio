package de.bkroeger.myvisio.controller.commands;

import de.bkroeger.myvisio.model.ApplicationModel;
import de.bkroeger.myvisio.model.Workbook;
import de.bkroeger.myvisio.model.Worksheet;
import de.bkroeger.myvisio.utility.TechnicalException;
import de.bkroeger.myvisio.view.ZoomDialog;

/**
 * <p>
 * Dieser Command implementiert die Anzeige und Auswahl des Zoom-Faktors
 * für die aktuelle Visio-Page.
 * </p>
 * @author bk
 */
public class ViewZoomCommand implements Command {
	
	private ApplicationModel applicationModel = ApplicationModel.getApplicationModel();

	/**
	 * Führt den Befehl aus.
	 */
	
	public void execute(Object arg) throws TechnicalException {
		
		// aktuelle Dokument ermitteln
		Workbook doc = applicationModel.getCurrentDocument();
		if (doc != null) {
			// aktuelle Seite ermitteln
			Worksheet sheet = doc.getCurrentWorksheet();
			if (sheet != null) {
				// den Dialog zur Auswahl des Zoom-Faktors anzeigen
				ZoomDialog dlg = new ZoomDialog(applicationModel.getMainController().getView(), sheet.getZoomFactor());
				// wurde der Dialog mit OK beendet?
				if (dlg.getDialogResult() == true) {
					// Zoom-Faktor übernehmen
					sheet.setZoomFactor(dlg.getZoomFactor());
					if (dlg.getZoomFactor() == ZoomDialog.ZoomFactor.PROZVALUE) {
						
						sheet.setZoomPercent(dlg.getZoomPercent());
					}
				}
			} else {
				// keine aktuelle Seite ausgewählt
			}
		} else {
			// kein Dokument ausgewählt
		}
	}
}
