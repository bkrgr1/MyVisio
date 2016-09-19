package de.bkroeger.myvisio.controller;

import de.bkroeger.myvisio.model.Worksheet;
import de.bkroeger.myvisio.view.WorksheetTabView;
import de.bkroeger.myvisio.view.WorksheetView;

public class WorksheetController {
	
	private Worksheet worksheet;
	
	private WorksheetView view;

	public WorksheetController(WorksheetTabView parentView, Worksheet worksheet) {
		
		this.worksheet = worksheet;
		
		this.view = new WorksheetView(this);
		
		parentView.addTab(worksheet.getTitle().toString(), this.view);
	}
}
