package de.bkroeger.myvisio.controller;

import java.awt.Dimension;

import de.bkroeger.myvisio.shapes.IShape;
import de.bkroeger.myvisio.view.ShapeView;

/**
 * @author bk
 *
 */
public class ShapeController {
	
	private ShapeView view;
	/**
	 * @return - die Ansicht; hier: ShapeView
	 */
	public  ShapeView getView() { return view; }
	
	private IShape model;
	/**
	 * @return - das Model; hier: IShape
	 */
	public IShape getModel() { return model; }
	

	/**
	 * @param dim
	 * @param model 
	 */
	public ShapeController(Dimension dim, IShape model) {
		
		this.model = model;
		this.view = new ShapeView(dim, this);
	}
}
