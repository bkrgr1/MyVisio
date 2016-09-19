package de.bkroeger.myvisio.controller;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import de.bkroeger.myvisio.model.Workbook;
import de.bkroeger.myvisio.model.Worksheet;
import de.bkroeger.myvisio.view.ApplicationView;
import de.bkroeger.myvisio.view.WorksheetTabView;

/**
 * <p>
 * Dieser Controller steuert die TabView der Worksheets eines Workbook.
 * </p>
 * @author bks
 */
public class WorksheetTabController extends BaseController {
	
	private WorksheetTabView view;
	public  WorksheetTabView getView() { return this.view; }
	
	private Workbook workbook;
	
    /**
     * Konstruktor für den Controller.
     * 
     * @param parentView - das Parent-Fenster
     * @param workbook - das Workbook, dessen Sheets angezeigt werden sollen.
     */
	public WorksheetTabController(ApplicationView parentView, 
			Workbook workbook, int usedWidth) {
		
		this.workbook = workbook;
		
		Dimension parentDimension = parentView.getSize();
		Dimension viewDimension = new Dimension(parentDimension.width - usedWidth, 
				parentDimension.height);
		this.view = new WorksheetTabView(this, viewDimension);
	}
	
	/**
	 * Zeigt die TabView an
	 */
	public void show() {
		
		// gibt es Sheets zum anzeigen?
		if (workbook.getWorksheetSize() > 0) {
			Iterator<Worksheet> iter = workbook.getWorksheetIterator();
			while (iter.hasNext()) {
				Worksheet worksheet = iter.next();
				
				// Controller für ein WorkSheet erstellen
				@SuppressWarnings("unused")
				WorksheetController worksheetController = 
						new WorksheetController(this.view, worksheet);
			}
		}
		
		this.view.setVisible(true);
	}
	
	/**
	 * Versteckt die TabView
	 */
	public void hide() {
		
		this.view.setVisible(false);
	}
	
	public void resizeHorizontally(int diff, MouseEvent e) {
		
		Dimension dim = this.view.getSize();
		dim.width += diff;
		this.view.setSize(dim);
		this.view.invalidate();
	}
}
