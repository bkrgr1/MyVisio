package de.bkroeger.myvisio.view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import de.bkroeger.myvisio.controller.commands.CommandArgument;
import de.bkroeger.myvisio.controller.commands.Transaction;
import de.bkroeger.myvisio.controller.commands.ViewZoomTransaction;
import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * <p>
 * Das ViewMenu enthält Aktionen zur Beeinflussung der Anzeige der aktuellen Seite.
 * </p>
 * @author bk
 */
public class ViewMenu extends JMenu {
	
	private static final long serialVersionUID = -3019332387753310502L;

	private static final Logger logger = Logger.getLogger(ViewMenu.class.getName());
	
	private MainMenuBar menuBar;
	public  MainMenuBar getMenuBar() { return this.menuBar; }

	public ViewMenu(MainMenuBar menuBar, String title, ResourceBundle bundle) {
		super(title);
		this.menuBar = menuBar;
		
		// Zoom-Menü
		final JMenuItem item = new JMenuItem(bundle.getString("Zoom"));
		// Zoom-Action-Listener
		item.addActionListener(new ActionListener() {	
			
			
			public void actionPerformed(ActionEvent e) {		
				try {
					Transaction t = new ViewZoomTransaction("ViewZoom", 
						(CommandArgument)null);
					t.execute();
				} catch (TechnicalException e1) {
					logger.severe(e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		item.addFocusListener(new FocusListener() {

			
			public void focusGained(FocusEvent e) {
				// Prüfen, ob es eine aktuelle Visio-Page gibt
//				if (application.getCurrentDocument() != null) {
//					WorkBook doc = application.getCurrentDocument();
//					if (doc.getCurrentPage() != null) {
//						item.setEnabled(true);
//					} else {
//						item.setEnabled(false);
//					}
//				} else {
//					item.setEnabled(false);
//				}
			}

			
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
			}
			
		});
		this.add(item);
	}
}
