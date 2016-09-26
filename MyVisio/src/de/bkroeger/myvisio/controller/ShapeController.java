package de.bkroeger.myvisio.controller;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.bkroeger.myvisio.shapes.IShape;
import de.bkroeger.myvisio.view.ShapeView;

/**
 * @author bk
 *
 */
public class ShapeController {
	
	private JComponent view;
	/**
	 * @return - die Ansicht; hier: ShapeView
	 */
	public  JComponent getView() { return view; }
	
	private ShapeView shapeCanvas;
	private JLabel shapeLabel;
	
	private IShape model;
	/**
	 * @return - das Model; hier: IShape
	 */
	public IShape getModel() { return model; }
	

	/**
	 * @param shapeDimension
	 * @param model 
	 * @param labelDimension
	 */
	public ShapeController(Dimension shapeDimension, IShape model, 
			Dimension labelDimension) {
		
		this.model = model;
		this.view = new JPanel();
		this.view.setLayout(new BoxLayout(this.view, BoxLayout.Y_AXIS));
		this.view.setOpaque(false);
		
		shapeCanvas = new ShapeView(shapeDimension, this);
		shapeLabel = new JLabel(model.getTitle().toString());
		shapeLabel.setPreferredSize(labelDimension);
		shapeLabel.setOpaque(false);
		shapeLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		this.view.add(shapeCanvas);
		this.view.add(shapeLabel);
	}
}
