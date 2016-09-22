package de.bkroeger.myvisio.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.logging.Logger;

import javax.swing.JPanel;

import org.w3c.dom.svg.SVGElement;
import org.w3c.dom.svg.SVGRectElement;

import de.bkroeger.myvisio.controller.ShapeController;
import de.bkroeger.myvisio.shapes.IShape;
import de.bkroeger.myvisio.svg.elements.SVGRectElementImpl;

/**
 * @author bk
 */
public class ShapeView extends JPanel {

	private static final long serialVersionUID = -1060615370911177510L;
	
	private static Logger logger = Logger.getLogger(ShapeView.class.getName());
	
	private ShapeController controller;

	/**
	 * @param shapeDimension
	 * @param controller 
	 */
	public ShapeView(Dimension shapeDimension, ShapeController controller) {
		
		this.controller = controller;
		IShape shape = controller.getModel();
		
		this.setPreferredSize(shapeDimension);
		this.setBackground(new Color(0, 0, 0, 1));
//		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.setOpaque(true);
		if (shape.getDescription() != null) {
			this.setToolTipText(shape.getDescription().getText());
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Dimension dim = this.getSize();
		float width = dim.width;
		float height = dim.height;
		
		Point location = this.getLocation();
		float posX = location.x;
		float posY = location.y;
		
		try {
			// temporär einen Viewport setzen
			SVGRectElement initialViewport = new SVGRectElementImpl(posX, posY, width, height);
			logger.info(String.format("Set shape viewport: x=%f y=%f width=%f height=%f", posX, posY, width, height));
			controller.getModel().negotiateViewport((SVGElement) initialViewport);
			
			Graphics2D g2d = (Graphics2D) g;
			controller.getModel().paint(g2d);
			
		} finally {
			// temporären Viewport wieder löschen
			controller.getModel().negotiateViewport(null);
		}
	}
}
