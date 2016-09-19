package de.bkroeger.myvisio.controller;

import java.awt.Dimension;
import java.awt.event.MouseEvent;

import de.bkroeger.myvisio.model.ShapeSet;
import de.bkroeger.myvisio.model.Workbook;
import de.bkroeger.myvisio.utility.ListeningMap;
import de.bkroeger.myvisio.view.ApplicationView;
import de.bkroeger.myvisio.view.NavigatorTabView;

/**
 * <p>
 * Dieser Controller kontrolliert die Anzeige des {@link NavigatorTabView}.
 * Der Navigator zeigt die Summe aller ShapeSets in den angezeigten Dokumenten.
 * </p>
 * @author bk
 */
public class NavigatorTabController extends BaseController {
	
	private NavigatorTabView view;
	public  NavigatorTabView getView() { return this.view; }
	   
    /**
     * Liste der distinkten {@link ShapeSet ShapeSets} in den Workbooks.
     */
    private ListeningMap<String, ShapeSet> shapeSets = new ListeningMap<String, ShapeSet>();
    
    private int navigatorWidth;

    /**
     * Konstruktor
     * 
     * @param parentView
     * @param workbook
     * @param navigatorWidth
     */
    public NavigatorTabController(ApplicationView parentView, 
    		Workbook workbook, int navigatorWidth) {
    	
    	this.navigatorWidth = navigatorWidth;
    	
    	addWorkbookShapeSets(workbook);
    	
    	this.view = new NavigatorTabView(this, 
    			new Dimension(navigatorWidth, parentView.getSize().height));
    }
	
	/**
	 * <p>
	 * Fügt alle ShapeSets des WorkBooks hinzu, die noch nicht 
	 * vorhanden sind. Bei vorhandenen ShapeSets wird der ReferenceCount erhöht.
	 * </p>
	 * @param workbook
	 */
	private void addWorkbookShapeSets(Workbook workbook) {
		
		for (ShapeSet ss : workbook.getShapeSets()) {
			if (shapeSets.containsKey(ss.getName()) == false) {
				shapeSets.put(ss.getName(), ss);
			}
			ss.increaseReferenceCount();			
		}
	}

	public void show() {
		
		this.view.setVisible(true);
		
		for (ShapeSet ss : shapeSets.values()) {
			@SuppressWarnings("unused")
			ShapeSetController shapeSetController = 
					new ShapeSetController(this.view, ss, navigatorWidth);
		}
	}
	
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
