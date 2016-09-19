package de.bkroeger.myvisio.view;

import java.awt.Dimension;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JTabbedPane;

import de.bkroeger.myvisio.controller.WorksheetTabController;

/**
 * <p>
 * Dies ist das Zeichenfenster für ein MyVisio-Workbook.
 * Es enthält eine TabbedPane (mit Tab-Reitern am unteren Rand) für die offenen Worksheets
 * des Workbook.
 * </p>
 * @author bk
 */
public class WorksheetTabView extends JTabbedPane  {

	private static final long serialVersionUID = 7879510019859348803L;
	
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(WorksheetTabView.class.getName());
	
	@SuppressWarnings("unused")
	private WorksheetTabController controller;

	/**
	 * Konstruktor
	 * 
	 * @param parent - der Parent-Frame
	 * @param docMap - Map der Visio-Dokumente
	 */
	public WorksheetTabView(WorksheetTabController controller, Dimension dimension) {
		super(JTabbedPane.BOTTOM, JTabbedPane.SCROLL_TAB_LAYOUT);
		
		this.controller = controller;
		
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		this.setPreferredSize(dimension);
		this.setMinimumSize(dimension);
	}
}
