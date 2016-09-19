package de.bkroeger.myvisio.controller;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.logging.Logger;

import de.bkroeger.myvisio.view.ApplicationView;
import de.bkroeger.myvisio.view.DelimiterView;

/**
 * <p>
 * Dieser Controller steuert die Trennzeile zwischen der
 * Navigator-Ansicht und der Workbook-Ansicht.
 * </p><p>
 * An dem Marker-Panel kann man den Trenner nach rechts oder links schieben.
 * Dies verändert die Größe der beiden Ansichten rechts und links.
 * </p>
 * @author bk
 */
public class DelimiterController implements MouseListener, MouseMotionListener{
	
	private final Logger logger = Logger.getLogger(DelimiterController.class.getName());

	private DelimiterView view;
	
	private boolean drag = false;
	
	private Point dragLocation = null;
	
	private BaseController parentController;
	
	private Cursor cursor;

	/**
	 * Konstruktor
	 * 
	 * @param parentView
	 * @param width
	 */
    public DelimiterController(ApplicationView parentView, 
    		int width, BaseController parentController) {
    	
    	this.parentController = parentController;
    	
    	this.view = new DelimiterView(this, 
    			new Dimension(width, parentView.getSize().height));
    	
    	parentView.add(this.view, BorderLayout.CENTER);
    }

    public void show() {
    	this.view.setVisible(true);
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
    	this.drag = true;
    	this.dragLocation = e.getPoint();
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
    	this.drag = false;
    	this.dragLocation = null;
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
    	
    	if (drag && dragLocation != null) {
    		
    		int diff = e.getX() - dragLocation.x;
    		logger.info("Move marker horizontally:"+diff);
    		parentController.moveHorizontally(diff, e);
    		
    		dragLocation = e.getPoint();
    	}
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
    	
    	this.cursor = this.view.getCursor();
    	this.view.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
    	
    	if (this.cursor != null)
    		this.view.setCursor(this.cursor);
    }

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
