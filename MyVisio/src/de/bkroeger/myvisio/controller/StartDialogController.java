package de.bkroeger.myvisio.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.ImageObserver;
import java.util.Collection;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.bkroeger.myvisio.model.Category;
import de.bkroeger.myvisio.model.StartDialogModel;
import de.bkroeger.myvisio.view.ApplicationView;
import de.bkroeger.myvisio.view.PicturePanel;
import de.bkroeger.myvisio.view.StartDialog;

/**
 * Controller für den Start-Dialog.
 * 
 * @author bk
 */
public class StartDialogController extends AbstractController 
implements WindowListener, ActionListener, ListSelectionListener, ImageObserver,
		MouseListener {
	
	private static Logger logger = Logger.getLogger(StartDialogController.class.getName());
	
	// das Dialog-View
	private StartDialog dialog;
	
	// das Dialog-Model
	private StartDialogModel model;
	
	// Flag, ob der Dialog mit OK beendet wurde
	private boolean dialogOk = false;
	
	// das aktuell ausgewählte Bild
	private PicturePanel currentPicture = null;

	/**
	 * <p>
	 * Konstruktor. Erzeugt den Dialog ohne ihn anzuzeigen.
	 * </p>
	 * @param mainFrame - Application view
	 */
	public StartDialogController(ApplicationView mainFrame, StartDialogModel model) {
	
		this.model = model;
		Frame frame = JOptionPane.getFrameForComponent(mainFrame);
		dialog = new StartDialog(this, frame);
	}
	
	public Category getCurrentCategory() {
		return model.getSelectedCategory();
	}
	
	/**
	 * <p>
	 * Zeigt den Dialog an und liefert ein Flag,
	 * ob der Dialog mit OK beendet wurde.
	 * </p>
	 * @return
	 */
	public boolean showDialog() {

		dialogOk = false;
		dialog.setVisible(true);
		return dialogOk;
	}

	/**
	 * Action-Listener für die Buttons
	 * 
	 * @param event - der Action-Event
	 */
	public void actionPerformed(ActionEvent event) {
		
		if (event.getActionCommand().equals("cancel")) {
			cancelDialog();
			
		} else
		if (event.getActionCommand().equalsIgnoreCase("ok")) {
			exitDialog();
		}
	}

	/**
	 * Wird aufgerufen, wenn der Dialog mit OK beendet wurde.
	 */
	private void exitDialog() {
		dialog.setVisible(false);
		dialogOk = true;
	}

	/**
	 * Wird aufgerufen, wenn der Dialog mit Cancel beendet wurde.
	 */
	private void cancelDialog() {
		dialog.setVisible(false);
		dialogOk = false;
	}
	
	/**
	 * Liefert ein ListModel mit der Übersicht der Categories.
	 * @return
	 */
	public ListModel<Category> getCategories() {
		
		Collection<Category> collection = model.getCategories();
		DefaultListModel<Category> categories = new DefaultListModel<Category>();
		for (Category category : collection) {
			categories.addElement(category);
		}
		return categories;
	}

	public void valueChanged(ListSelectionEvent e) {
		
		ListSelectionModel lsm = (ListSelectionModel)e.getSource();
        if (lsm.isSelectionEmpty()) {
            // keine Auswahl
        	model.setSelectedCategory(null);
        } else {
            // Find out which indexes are selected.
            for (int i = lsm.getMinSelectionIndex(); i <= lsm.getMaxSelectionIndex(); i++) {
                if (lsm.isSelectedIndex(i)) {
                	
                    // index gefunden
                	ListModel<Category> categories = this.getCategories();
                	Category category = categories.getElementAt(i);
                	logger.info("Selected category="+category.getName());
                	
                	model.setSelectedCategory(category);
                	
                	dialog.fillSamplePanel(category);
                	dialog.disableOkButton();
                	dialog.repaint();
                	break;
                }
            }
        }
	}

	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3,
			int arg4, int arg5) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		cancelDialog();
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		Component comp = arg0.getComponent();
		if (comp instanceof PicturePanel) {
			
			if (currentPicture != null) {
				currentPicture.setBorder(BorderFactory.createEtchedBorder());
			}
			currentPicture = (PicturePanel)comp;
			currentPicture.setBorder(BorderFactory.createLineBorder(Color.RED));
			
			model.setCurrentExample(currentPicture.getExample());
			logger.info("Picture '"+currentPicture.getExample().getPicturePath()+"' clicked!");
			
			dialog.enableOkButton();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
