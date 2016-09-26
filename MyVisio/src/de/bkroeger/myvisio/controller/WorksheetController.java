package de.bkroeger.myvisio.controller;

import de.bkroeger.myvisio.model.Worksheet;
import de.bkroeger.myvisio.view.WorksheetTabView;
import de.bkroeger.myvisio.view.WorksheetView;

/**
 * @author bk
 */
public class WorksheetController {
	
	private Worksheet worksheet;
	/**
	 * @return - das Worksheet-Model
	 */
	public Worksheet getModel() {
		return worksheet;
	}
	
	private WorksheetView view;

	/**
	 * @param parentView
	 * @param worksheet
	 */
	public WorksheetController(WorksheetTabView parentView, Worksheet worksheet) {
		
		this.worksheet = worksheet;
		
		this.view = new WorksheetView(this);
		
		parentView.addTab(worksheet.getTitle().toString(), this.view);
	}
}
