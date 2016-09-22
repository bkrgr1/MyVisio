package de.bkroeger.myvisio.view;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.logging.Logger;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import de.bkroeger.myvisio.controller.ShapeController;
import de.bkroeger.myvisio.controller.ShapeSetController;
import de.bkroeger.myvisio.shapes.IShape;

/**
 * <p>
 * Panel zur Anzeige der Template-Shapes eines Sets.
 * </p><p>
 * Der Panel wird in x Spalten aufgeteilt. 
 * Die Höhe ergibt sich aus der Anzahl Shapes.
 * </p>
 * @author bk
 */
public class ShapeSetView extends JPanel {
//	public class ShapeSetView extends JPanel implements DragGestureListener, DragSourceListener {

	private static final long serialVersionUID = 3005422069479363707L;
	
	/**
	 * Der Logger
	 */
	private static Logger logger = Logger.getLogger(ShapeSetView.class.getName());
	
	private static final int SHAPE_WIDTH = 65;
	private static final int SHAPE_HEIGHT = 65;
	private static final int LABEL_HEIGHT = 12;
	
	private static final int VGAP = 5;
	private static final int HGAP = 5;

	/**
	 * Linienstärke für die Drag-Markierung.
	 */
	static final int LINEWIDTH = 3;

	/**
	 * Linienstil für die Drag-Markierung.
	 */
	static final BasicStroke linestyle = new BasicStroke(LINEWIDTH);
	
	/**
	 * Flag, ob erstes Mal gezeichnet wird.
	 */
	@SuppressWarnings("unused")
	private boolean firstPaint = true;

	IShape currentShape; // The shape in progress

	IShape beingDragged; // The shape being dragged

	DragSource dragSource; // A central DnD object

	boolean dragMode; // Are we dragging or scribbling?
	/**
	 * @param dragMode
	 */
	public void setDragMode(boolean dragMode) { this.dragMode = dragMode; }
	/**
	 * @return flag
	 */
	public boolean getDragMode() { return dragMode; }
	
	@SuppressWarnings("unused")
	private ShapeSetController controller;
	
	private int colCnt = 0;
	private int rowCnt = 0;

	/**
	 * Konstruktor
	 * @param controller 
	 * @param navigatorWidth 
	 */
	public ShapeSetView(ShapeSetController controller, int navigatorWidth) {
	
		this.controller = controller;
		
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		this.setBackground(new Color(161, 212, 144));	// #A1D490
		
		JPanel gridPanel = new JPanel();
		this.setLayout(new SpringLayout());
		gridPanel.setBackground(new Color(161, 212, 144));	// #A1D490
		
		int shapeCnt = controller.getShapeCount();
		this.colCnt = (navigatorWidth - 10) / (SHAPE_WIDTH);
		this.rowCnt = shapeCnt / colCnt;
		if ((shapeCnt % colCnt) > 0) {
			rowCnt++;
		}
		
		// Grösse des GridPanel berechnen
		Dimension gridDim = new Dimension((colCnt * SHAPE_WIDTH) + (colCnt * VGAP), 
				(rowCnt * (SHAPE_HEIGHT + LABEL_HEIGHT)) + (rowCnt * HGAP));
		gridPanel.setSize(gridDim);
		gridPanel.setPreferredSize(gridDim);
		
		// auch das ShapeSetView benötigt eine Grösse,
		// damit das ScrollPane entscheiden kann,
		// ob es die ScrollBars zeigen soll.
		this.setPreferredSize(gridDim);
		
		for (int i=0; i<shapeCnt; i++) {
			IShape shape = controller.getShapeAt(i);
			
			ShapeController shapeController = createShapeController(shape);
			gridPanel.add(shapeController.getView());
		}
		for (int i=shapeCnt; i<(rowCnt * colCnt); i++) {
			
			JPanel dummyView = createDummyView();
			gridPanel.add(dummyView);
		}
		
		SpringUtilities.makeCompactGrid(gridPanel,
                colCnt, rowCnt, //rows, cols
                0, 0, //HGAP, VGAP, //initialX, initialY
                HGAP, VGAP);//xPad, yPad

		this.add(gridPanel);
		this.setVisible(true);
	}
	
	private JPanel createDummyView() {
		
		JPanel fillPanel = new JPanel();
		Dimension dim = new Dimension(SHAPE_WIDTH-(HGAP*2), SHAPE_HEIGHT-(VGAP*2));
		fillPanel.setPreferredSize(dim);
		fillPanel.setMinimumSize(dim);
		fillPanel.setBackground(new Color(0, 0, 0, 1));
		return fillPanel;
	}
	
	private ShapeController createShapeController(IShape shape) {
		
		Dimension shapeDimension = new Dimension(SHAPE_WIDTH-HGAP, SHAPE_HEIGHT-VGAP);
		Dimension labelDimension = new Dimension(SHAPE_WIDTH-HGAP, LABEL_HEIGHT);
		ShapeController shapeController = new ShapeController(shapeDimension, shape, labelDimension);
		return shapeController;
	}
	
	/**
	 * <p>
	 * Zeichnet die Shapes auf dem Canvas.
	 * </p>
	 * @param g - Graphics2D
	 */
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		@SuppressWarnings("unused")
		Graphics2D g2d = (Graphics2D) g;
	}
	
	/**
	 * This method implements the DragGestureListener interface. It will be
	 * invoked when the DragGestureRecognizer thinks that the user has initiated
	 * a drag. If we're not in drawing mode, then this method will try to figure
	 * out which shape object is being dragged, and will initiate a drag on
	 * that object.
	 * @param e 
	 */
	public void dragGestureRecognized(DragGestureEvent e) {
		
		setDragMode(true);

//		// Figure out where the drag started
//		MouseEvent inputEvent = (MouseEvent) e.getTriggerEvent();
//		int x = inputEvent.getX();
//		int y = inputEvent.getY();
//		Point.Float p = new Point.Float(x, y);
//
//		// Figure out which shape was clicked on
//		SVGSVGElementImpl svgElement = (SVGSVGElementImpl) shapeSet.getSVGElement();
//		IShape pointedShape = svgElement.containsPoint(p);
//			
//		if (pointedShape != null) {
//			// The user started the drag on top of this shape, so start to drag it.
//			logger.info("Clicked on shape: "+pointedShape.toString());
//
//			// First, remember which shape is being dragged, so we can
//			// delete it later (if this is a move rather than a copy)
//			beingDragged = pointedShape;
//
//			// Next, create a copy that will be the one dragged
//			ShapeStructure dragStructure = new ShapeStructure((IShape)pointedShape.clone());
//
//			// Choose a cursor based on the type of drag the user initiated
//			Cursor cursor;
//			switch (e.getDragAction()) {
//			case DnDConstants.ACTION_COPY:
//				cursor = DragSource.DefaultCopyDrop;
//				break;
//			case DnDConstants.ACTION_MOVE:
//				cursor = DragSource.DefaultMoveDrop;
//				break;
//			default:
//				return; // We only support move and copys
//			}
//			
//			// Some systems allow us to drag an image along with the
//			// cursor. If so, create an image of the scribble to drag
////			if (DragSource.isDragImageSupported()) {
////				Shape imageBox = dragStructure.getBoundingRectangle();
////					Image dragImage = this.createImage(
////							imageBox.width,
////							imageBox.height);
////					Graphics2D g = (Graphics2D) dragImage.getGraphics();
////					g.setColor(new Color(0, 0, 0, 0)); // transparent background
////					g.fill(0.0f, 0.0f, imageBox.width, imageBox.height);
////					g.setColor(Color.black);
////					g.setStroke(linestyle);
////					g.translate(-imageBox.x, -imageBox.y);
//				
////					try {
////						logger.info("Draw image: "+dragStructure.template.getClass().getSimpleName());
////						dragStructure.template.drawInBox(g, 2, 2, imageBox.width+4, imageBox.height+4, false);
////					} catch (TechnicalException e1) {
////						logger.severe(e1.getMessage());
////						e1.printStackTrace();
////					}
//				
////					Point hotspot = new Point(-imageBox.x, -imageBox.y);
//
//				// Now start dragging, using the image.
////					e.startDrag(cursor, dragImage, hotspot, dragStructure, this);
////			} else {
//				// Or start the drag without an image
//					e.startDrag(cursor, dragStructure, this);
////			}
//			// After we've started dragging one scribble, stop looking
//			return;
//		}
		
		logger.info("No shape to drag found.");
	}


	
	/**
	 * @param dsde
	 */
	public void dragEnter(DragSourceDragEvent dsde) {
		// Auto-generated method stub
	}

	
	/**
	 * @param dsde
	 */
	public void dragOver(DragSourceDragEvent dsde) {
		// Auto-generated method stub
	}

	
	/**
	 * @param dsde
	 */
	public void dropActionChanged(DragSourceDragEvent dsde) {
		// Auto-generated method stub
	}

	
	/**
	 * @param dse
	 */
	public void dragExit(DragSourceEvent dse) {
		// Auto-generated method stub
	}

	/**
	* This method implements the
	* DragSourceListener interface. dragDropEnd() is invoked when the user
	* drops the Shape he was dragging. If the drop was successful, and if
	* the user did a "move" rather than a "copy", then we delete the dragged
	* Shape from the list of scribbles to draw.
	 * @param e 
	*/
	public void dragDropEnd(DragSourceDropEvent e) {
		
		if (!e.getDropSuccess()) {
			logger.fine("DragDrop failed.");
			return;
		}
		
		int action = e.getDropAction();
		if (action == DnDConstants.ACTION_MOVE) {
//			shapes.remove(beingDragged);
			beingDragged = null;
			repaint();
		}
		
		logger.fine("DragDrop ended.");
		setDragMode(false);
	}
	
	/**
	 * ===========================================================================
	 */
	
	/**
	 * <p>
	 * The listener interface for receiving "interesting" mouse events 
	 * (press, release, click, enter, and exit) on a component.
	 * </p>
	 * @author bk
	 */
	@SuppressWarnings("unused")
	private class MouseActionListener extends MouseAdapter {
		
		private ShapeSetView component;
		
		/**
		 * Konstruktor
		 * @param component
		 */
		public MouseActionListener(ShapeSetView component) {
			this.component = component;
		}

		/**
		 * @param e - MouseEvent
		 */
		
		public void mouseClicked(MouseEvent e) {
			
			if (dragMode)
				return;
			
			int onmask = MouseEvent.CTRL_DOWN_MASK;	// | MouseEvent.BUTTON1_DOWN_MASK;
			
			int modifiers = e.getModifiersEx();
			if ((modifiers & onmask) == onmask) {
				// nicht löschen
			} else {
				clearSelected();
			}
		
//			Point p = e.getPoint();
//			boolean shapeFound = false;
//			currentShape = shapeSet.getSVGElement().containsPoint(new Point2D.Float(p.x, p.y));
//			if (currentShape != null) {
//				shapeFound = true;
//				currentShape.setSelected(true);
//			}
//			if (shapeFound == false) {
//				clearSelected();
//			}
			repaint();
		}

		private void clearSelected() {
//			for (ShapeStructure s : component.shapes) {
//				if (s.isSelectedFlag) s.isSelectedFlag = false;
//			}
		}

		
		public void mousePressed(MouseEvent e) {
			// Auto-generated method stub
        }

		
		public void mouseReleased(MouseEvent e) {
			// Auto-generated method stub
		}

		
		public void mouseEntered(MouseEvent e) {
			// Auto-generated method stub
		}

		
		public void mouseExited(MouseEvent e) {
			// Auto-generated method stub
		}
	}
	
	/**
	 * ===========================================================================
	 */

	@SuppressWarnings("unused")
	private class MouseMotionActionListener extends MouseMotionAdapter {

		/**
		 * Invoked when a mouse button is pressed on a component and then dragged. 
		 * MOUSE_DRAGGED events will continue to be delivered to the component 
		 * where the drag originated until the mouse button is released 
		 * (regardless of whether the mouse position is within the bounds of the component). 
		 */
		
		public void mouseDragged(MouseEvent e) {
			
			if (dragMode)
				return;
		}

		
		public void mouseMoved(MouseEvent e) {
			// Auto-generated method stub
		}
	}
	
	/**
	 * ===========================================================================
	 */
	
	/**
	 * The listener interface for receiving mouse wheel events on a component. 
	 * (For clicks and other mouse events, use the MouseListener. 
	 * For mouse movement and drags, use the MouseMotionListener.) 
	 * 
	 * @author bk
	 */
	@SuppressWarnings("unused")
	private class MouseWheelActionListener implements MouseWheelListener {

		
		public void mouseWheelMoved(MouseWheelEvent e) {
			// Auto-generated method stub
		}
	}
	
	/**
	 * ===========================================================================
	 */
	
	/**
	 * The listener interface for receiving keyboard events (keystrokes).
	 * 
	 * @author bk
	 */
	@SuppressWarnings("unused")
	private class KeyActionListener implements KeyListener {

		
		public void keyTyped(KeyEvent e) {
			// Auto-generated method stub
		}

		
		public void keyPressed(KeyEvent e) {
			// Auto-generated method stub
		}

		
		public void keyReleased(KeyEvent e) {
			// Auto-generated method stub
		}
	}
}
