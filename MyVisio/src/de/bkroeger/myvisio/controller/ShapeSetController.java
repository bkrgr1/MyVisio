package de.bkroeger.myvisio.controller;

import java.util.logging.Logger;

import javax.swing.ScrollPaneConstants;
import javax.swing.JScrollPane;

import de.bkroeger.myvisio.model.ShapeSet;
import de.bkroeger.myvisio.shapes.IShape;
import de.bkroeger.myvisio.view.NavigatorTabView;
import de.bkroeger.myvisio.view.ShapeSetView;

/**
 * <p>
 * Dieser Controller steuert die Anzeige und Aktionen
 * eines ShapeSets und seiner Shapes.
 * </p>
 * @author bk
 */
public class ShapeSetController {
	
	private final Logger logger = Logger.getLogger(ShapeSetController.class.getName());
	
	/**
	 * Das Daten-Model
	 */
	private ShapeSet shapeSet;
	
	/**
	 * Die Ansicht
	 */
	private ShapeSetView view;
	public  ShapeSetView getView() { return view; }

	/**
	 * Konstruktor.
	 * 
	 * @param parentView
	 * @param shapeSet
	 */
	public ShapeSetController(NavigatorTabView parentView, ShapeSet shapeSet, 
			int navigatorWidth) {
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		this.shapeSet = shapeSet;
		this.view = new ShapeSetView(this, navigatorWidth);
		
		scrollPane.setViewportView(this.view);
		
		// Ansicht zur übergeordneten Ansicht hinzufügen
		parentView.addTab(shapeSet.getName(), scrollPane);
	}
	
	public int getShapeCount() {
		return this.shapeSet.getShapesSize();
	}
	
	public IShape getShapeAt(int index) {
		return shapeSet.getShapeAt(index);
	}
}
