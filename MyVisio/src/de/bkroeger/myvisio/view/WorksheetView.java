package de.bkroeger.myvisio.view;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import de.bkroeger.myvisio.controller.WorksheetController;
import de.bkroeger.myvisio.controller.commands.DocumentArgument;
import de.bkroeger.myvisio.controller.commands.FileSaveAsTransaction;
import de.bkroeger.myvisio.controller.commands.FileSaveTransaction;
import de.bkroeger.myvisio.controller.commands.Transaction;
import de.bkroeger.myvisio.model.Worksheet;
import de.bkroeger.myvisio.shapes.IShape;
import de.bkroeger.myvisio.shapes.ShapeStructure;
import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * <p>
 * Canvas für eine Seite einer Zeichnung.
 * </p>
 * <p>
 * Dieser Canvas dient als DropTarget für Shapes aus dem Navigator.
 * </p>
 * 
 * @author bk
 */
public class WorksheetView extends JPanel implements DropTargetListener, ItemListener {

	private static final long serialVersionUID = 1059061086692643035L;

	protected static final Logger logger = Logger.getLogger(WorksheetView.class
			.getName());

	static final int LINEWIDTH = 3;

	static final BasicStroke linestyle = new BasicStroke(LINEWIDTH);

	static final Border normalBorder = new BevelBorder(BevelBorder.LOWERED);

	static final Border dropBorder = new BevelBorder(BevelBorder.RAISED);

	@SuppressWarnings("unused")
	private ShapeStructure currentShape;

	/**
	 * aktuell bearbeitetes Shape auf der Seite (vgl. MouseActionListener und
	 * MouseMotionActionListener)
	 */
	private IShape movedShape;

	/**
	 * Referenz auf die Dokumentstruktur der Seite.
	 */
	private Worksheet worksheet;

	/**
	 * Skalierungsfaktor für die Anzeige. Default: 1:1 d.h. 100%
	 */
	private float scaleFactor = 1.0f;

	/**
	 * @return - ScaleFactor
	 */
	public float getScaleFactor() {
		return scaleFactor;
	}

	/**
	 * @param factor
	 */
	public void setScaleFactor(float factor) {
		this.scaleFactor = factor;
		repaint();
	}

	private WorksheetController controller;

	private Rule columnView;
	private Rule rowView;
	private JToggleButton isMetric;
	private ScrollablePicture picture;

	/**
	 * Konstruktor.
	 * @param controller 
	 */
	public WorksheetView(WorksheetController controller) {

		this.controller = controller;

		this.setBorder(BorderFactory.createRaisedBevelBorder());
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		// Get the image to use.
		// ImageIcon bee = createImageIcon("images/flyingBee.jpg");
		Canvas canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(1000, 1000));

		// Create the row and column headers.
		columnView = new Rule(Rule.HORIZONTAL, true);
		rowView = new Rule(Rule.VERTICAL, true);

		if (canvas != null) {
			columnView.setPreferredWidth(1000);
			rowView.setPreferredHeight(1000);
//		} else {
//			columnView.setPreferredWidth(320);
//			rowView.setPreferredHeight(480);
		}

		// Create the corners.
		JPanel buttonCorner = new JPanel(); // use FlowLayout
		isMetric = new JToggleButton("cm", true);
		isMetric.setFont(new Font("SansSerif", Font.PLAIN, 11));
		isMetric.setMargin(new Insets(2, 2, 2, 2));
		isMetric.addItemListener(this);
		buttonCorner.add(isMetric);

		// Set up the scroll pane.
//		picture = new ScrollablePicture(canvas, columnView.getIncrement(), controller);
		picture = new ScrollablePicture(null, columnView.getIncrement(), controller);
		JScrollPane pictureScrollPane = new JScrollPane(picture);
		pictureScrollPane.setPreferredSize(new Dimension(300, 250));
		pictureScrollPane.setViewportBorder(BorderFactory
				.createLineBorder(Color.black));

		pictureScrollPane.setColumnHeaderView(columnView);
		pictureScrollPane.setRowHeaderView(rowView);
		pictureScrollPane
				.setCorner(JScrollPane.UPPER_LEFT_CORNER, buttonCorner);
		pictureScrollPane
				.setCorner(JScrollPane.LOWER_LEFT_CORNER, new Corner());
		pictureScrollPane.setCorner(JScrollPane.UPPER_RIGHT_CORNER,
				new Corner());

		// Put it in this panel.
		add(pictureScrollPane);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		// this.addComponentListener(new ComponentActionListener());
		// this.addMouseListener(new MouseActionListener(this));
		// this.addMouseMotionListener(new MouseMotionActionListener());
		// this.addKeyListener(new KeyActionListener(this));
		// this.addMouseWheelListener(new MouseWheelActionListener());

		// Create and set up a DropTarget that will listen for drags and
		// drops over this component, and will notify the DropTargetListener
		// DropTarget dropTarget = new DropTarget(this, // component to monitor
		// this); // listener to notify
		// this.setDropTarget(dropTarget); // Tell the component about it.

		this.setVisible(true);
	}

	/**
	 * @param title
	 */
	public void setTitle(String title) {
		JTabbedPane tabbedPane = (JTabbedPane) SwingUtilities
				.getAncestorOfClass(JTabbedPane.class, this);
		for (int i = 0; i < tabbedPane.getTabCount(); i++) {
			if (SwingUtilities.isDescendingFrom(this,
					tabbedPane.getComponentAt(i))) {
				tabbedPane.setTitleAt(i, title);
				break;
			}
		}
	}

	/**
	 * <p>
	 * This method is invoked when the user first drags something over us. If we
	 * understand the data type being dragged, then call acceptDrag() to tell
	 * the system that we're receptive.
	 * </p>
	 * <p>
	 * Also, we change our border as a "drag under" effect to signal that we can
	 * accept the drop.
	 * </p>
	 * 
	 * @param e
	 *            - Event-Struktur
	 */

	public void dragEnter(DropTargetDragEvent e) {
		if (e.isDataFlavorSupported(ShapeStructure.ShapeDataFlavor)
				|| e.isDataFlavorSupported(DataFlavor.stringFlavor)) {

			e.acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);
			logger.info("drag accepted.");
			this.setBorder(dropBorder);
		}
	}

	/**
	 * <p>
	 * Called when a drag operation is ongoing, while the mouse pointer is still
	 * over the operable part of the drop site for the DropTarget registered
	 * with this listener.
	 * </p>
	 * 
	 * @param e
	 *            - Event-Struktur
	 */

	public void dragOver(DropTargetDragEvent e) {
		// Auto-generated method stub
	}

	public void dropActionChanged(DropTargetDragEvent e) {
		// Auto-generated method stub
	}

	/**
	 * The user is no longer dragging over us, so restore the border </p>
	 * 
	 * @param e - Event-Struktur
	 */
	public void dragExit(DropTargetEvent e) {

		this.setBorder(normalBorder);
	}

	/**
	 * This is the key method of DropTargetListener. It is invoked when the user
	 * drops something on us.
	 */

	public void drop(DropTargetDropEvent event) {
		this.setBorder(normalBorder); // Restore the default border

		// First, check whether we understand the data that was dropped.
		// If we supports our data flavors, accept the drop, otherwise reject.
		if (event.isDataFlavorSupported(ShapeStructure.ShapeDataFlavor)
				|| event.isDataFlavorSupported(DataFlavor.stringFlavor)) {

			event.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
			logger.info("Drop accepted.");

			// We've accepted the drop, so now we attempt to get the dropped
			// data
			// from the Transferable object.
			Transferable transferable = event.getTransferable(); // Holds the
																	// dropped
																	// data
			ShapeStructure dropStructure; // This will hold the ShapeStructure
											// object

			// First, try to get the data directly as a ShapeStructure object
			try {
				dropStructure = (ShapeStructure) transferable
						.getTransferData(ShapeStructure.ShapeDataFlavor);
				logger.fine("DataFlavor="
						+ ShapeStructure.class.getSimpleName());

			} catch (IOException | UnsupportedFlavorException ex) { // unsupported
																	// flavor,
																	// IO
																	// exception,
																	// etc.
				logger.warning("Unable to complete drop: " + ex.getMessage());

				// If that doesn't work, try to get it as a String and parse it
				try {
					String s = (String) transferable
							.getTransferData(DataFlavor.stringFlavor);
					dropStructure = ShapeStructure.parse(s);
					logger.fine("DataFlavor="
							+ DataFlavor.stringFlavor.toString());

				} catch (Exception ex2) {

					// If we still couldn't get the data, tell the system we
					// failed
					event.dropComplete(false);
					logger.warning("Unable to complete drop: "
							+ ex2.getMessage());
					return;
				}
			}

			// If we get here, we've got the Shape object
			logger.info("droppedShape = "
					+ (dropStructure != null ? dropStructure.toString()
							: "null"));

			if (dropStructure != null && dropStructure.getShape() != null) {

				Point position = event.getLocation(); // Where did the drop
														// happen?
				logger.info("Drop position = " + position.toString());
				dropStructure.setOrigin(new Point2D.Float((float) position
						.getX(), (float) position.getY()));

				// // start test
				// SVGGElementImpl gElem =
				// (SVGGElementImpl)dropStructure.getShape();
				// SVGAnimatedTransformListImpl transList =
				// (SVGAnimatedTransformListImpl) gElem.getTransformList();
				// if (transList.getAnimVal().getNumberOfItems() > 0) {
				// SVGTransformImpl trans = (SVGTransformImpl)
				// transList.getAnimVal().getItem(0);
				// if (trans != null) {
				// logger.info("Translate transform: x="+trans.getX()+" y="+trans.getY());
				// }
				// }
				// // end test

				try {
					worksheet.getSVGRoot().addShape(dropStructure.getShape());
					event.dropComplete(true); // signal success!
					logger.info("Drop completed");
					repaint(); // ask for redraw

					this.worksheet.setDirty();

				} catch (TechnicalException e1) {
					e1.printStackTrace();
				}
			}

		} else {
			event.rejectDrop();
			logger.info("Drop rejected.");
			return;
		}
	}

	/**
	 * Hilfsklasse als ComponentListener.
	 * 
	 * @author bk
	 */
	@SuppressWarnings("unused")
	private class ComponentActionListener implements ComponentListener {

		public void componentResized(ComponentEvent e) {
			// Auto-generated method stub
		}

		public void componentMoved(ComponentEvent e) {
			// Auto-generated method stub
		}

		public void componentShown(ComponentEvent e) {
			// Auto-generated method stub
		}

		public void componentHidden(ComponentEvent e) {
			// Auto-generated method stub
		}

	}

	/**
	 * ========================================================================
	 * ===
	 */

	/**
	 * Hilfsklasse als MouseListener.
	 * 
	 * @author bk
	 */
	@SuppressWarnings("unused")
	private class MouseActionListener implements MouseListener {

		private WorksheetView canvas;

		public MouseActionListener(WorksheetView canvas) {
			this.canvas = canvas;
		}

		public void mouseClicked(MouseEvent e) {

			int onmask = MouseEvent.CTRL_DOWN_MASK; // |
													// MouseEvent.BUTTON1_DOWN_MASK;

			int modifiers = e.getModifiersEx();
			if ((modifiers & onmask) == onmask) {
				// nicht löschen
			} else {
				clearSelected();
			}

			// erfolgte der Klick auf einem Shape?
			Point p = e.getPoint();
			IShape aShape = worksheet.getSVGRoot().containsPoint(
					new Point2D.Float(p.x, p.y));
			if (aShape != null) {
				// aShape.isSelectedFlag = !aShape.isSelectedFlag;
				// if (aShape.isSelectedFlag)
				// currentShape = aShape;
				// else
				// currentShape = null;
			} else {
				// alle bisher gewählten Shapes freigeben
				clearSelected();
			}
			repaint();
		}

		/**
		 * Löscht alle ausgewählten Shapes.
		 */
		private void clearSelected() {

		}

		public void mousePressed(MouseEvent e) {

			Point p = e.getPoint();
			Point2D.Float p2 = new Point2D.Float(p.x, p.y);
			movedShape = worksheet.getSVGRoot().containsPoint(p2);
		}

		public void mouseReleased(MouseEvent mouseEvent) {
			//
			// movedShape = null;
			//
			// JDialog dialog = null;
			//
			// // wurde die rechte Maustaste freigelassen
			// if ((mouseEvent.getModifiers() & InputEvent.BUTTON3_MASK) ==
			// InputEvent.BUTTON3_MASK) {
			//
			// // erfolgte der Event über einem Shape?
			// Point p = mouseEvent.getPoint();
			// IShape aShape = workSheet.getSVGRoot().containsPoint(new
			// Point2D.Float(p.x, p.y));
			// if (aShape != null) {
			//
			// // DialogBox für Shapes anzeigen
			// dialog = new ShapePropertiesDialog(
			// canvas.getFrame(), "Shape properties", true);
			// dialog.setLocationRelativeTo(canvas);
			// dialog.setLocation(mouseEvent.getPoint());
			// dialog.pack();
			// dialog.setVisible(true);
			// // dialog.getOptionPane().addPropertyChangeListener(
			// // new PropertyChangeListener() {
			// //
			// // public void propertyChange(PropertyChangeEvent e) {
			// // String prop = e.getPropertyName();
			// //
			// // if (dialog.isVisible()
			// // && (e.getSource() == optionPane)
			// // && (prop.equals(JOptionPane.VALUE_PROPERTY))) {
			// // //If you were going to check something
			// // //before closing the window, you'd do
			// // //it here.
			// // dialog.setVisible(false);
			// // }
			// // }
			// // });
			//
			// } else {
			//
			// // DialogBox für VisioPage anzeigen
			// dialog = new PagePropertiesDialog(
			// canvas.getFrame(), "Shape properties", true);
			// dialog.setLocationRelativeTo(canvas);
			// dialog.setLocation(mouseEvent.getPoint());
			// dialog.pack();
			// dialog.setVisible(true);
			// }
			// } else {
			// // Dialog-Anzeige löschen
			// }
		}

		public void mouseEntered(MouseEvent mouseEvent) {
			// Auto-generated method stub
		}

		public void mouseExited(MouseEvent e) {
			// Auto-generated method stub
		}

	}

	/**
	 * ========================================================================
	 * ===
	 */

	/**
	 * Hilfsklasse als MouseMotionListener.
	 * 
	 * @author bk
	 */
	@SuppressWarnings("unused")
	private class MouseMotionActionListener implements MouseMotionListener {

		public void mouseDragged(MouseEvent e) {

			if (movedShape != null) {

				Point p = e.getPoint();
				movedShape.setOrigin(new Point2D.Float((float) p.getX(),
						(float) p.getY()));
				repaint();
			}
		}

		public void mouseMoved(MouseEvent e) {

		}
	}

	/**
	 * ========================================================================
	 * ===
	 */

	/**
	 * Hilfsklasse als MouseWheelListener.
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
	 * ========================================================================
	 * ===
	 */

	/**
	 * Hilfsklasse als KeyListener.
	 * 
	 * @author bk
	 */
	@SuppressWarnings("unused")
	private class KeyActionListener implements KeyListener {

		private WorksheetView canvas;

		public KeyActionListener(WorksheetView canvas) {
			this.canvas = canvas;
		}

		public void keyTyped(KeyEvent e) {

			if (e.getID() == KeyEvent.KEY_TYPED) {
				logger.info("Key '" + e.getKeyCode() + "' typed.");

				if (e.getKeyCode() == KeyEvent.VK_S) {
					if ((e.getExtendedKeyCode() & KeyEvent.CTRL_DOWN_MASK) == KeyEvent.CTRL_DOWN_MASK) {

						// Ctrl+S: Save file
						Transaction t = new FileSaveTransaction("FileSave",
								new DocumentArgument(
										canvas.worksheet.getWorkBook()));
						try {
							t.execute();
						} catch (TechnicalException e1) {
							logger.severe(e1.getMessage());
							e1.printStackTrace();
						}
					}
					if ((e.getExtendedKeyCode() & KeyEvent.CTRL_DOWN_MASK & KeyEvent.SHIFT_DOWN_MASK) == (KeyEvent.CTRL_DOWN_MASK & KeyEvent.SHIFT_DOWN_MASK)) {

						// Ctrl+Shift+S: Save_as file
						Transaction t = new FileSaveAsTransaction("FileSaveAs",
								new DocumentArgument(
										canvas.worksheet.getWorkBook()));
						try {
							t.execute();
						} catch (TechnicalException e1) {
							logger.severe(e1.getMessage());
							e1.printStackTrace();
						}
					}
				}
			}
		}

		public void keyPressed(KeyEvent e) {

			logger.info("Key '" + e.getKeyCode() + "' pressed.");
		}

		public void keyReleased(KeyEvent e) {

			logger.info("Key '" + e.getKeyCode() + "' released.");
		}
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
