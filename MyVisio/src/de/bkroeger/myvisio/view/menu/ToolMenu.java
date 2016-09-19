package de.bkroeger.myvisio.view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import de.bkroeger.myvisio.controller.commands.CommandArgument;
import de.bkroeger.myvisio.controller.commands.DocumentArgument;
import de.bkroeger.myvisio.controller.commands.FileSaveAsTransaction;
import de.bkroeger.myvisio.controller.commands.Transaction;
import de.bkroeger.myvisio.model.ApplicationModel;
import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * <p>
 * Das FileMenu enthält Aktionen zur Behandlung von MyVisio-Dateien.
 * </p>
 * @author bk
 */
public class ToolMenu extends JMenu {

	private static final long serialVersionUID = 6608588933721700660L;
	
	private static final Logger logger = Logger.getLogger(ToolMenu.class.getName());
	
	private MainMenuBar menuBar;
	public  MainMenuBar getMenuBar() { return this.menuBar; }
	
	private ApplicationModel application = ApplicationModel.getApplicationModel();

	public ToolMenu(MainMenuBar menuBar, String title, ResourceBundle bundle) {
		super(title);
		this.menuBar = menuBar;
		
		JMenuItem item;
		
		item = new JMenuItem(bundle.getString("New"));
		item.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent e) {		
				try {
					Transaction t = new FileSaveAsTransaction("FileNew", 
						(CommandArgument)null);
					t.execute();
				} catch (TechnicalException e1) {
					logger.severe(e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		this.add(item);
		
		item = new JMenuItem(bundle.getString("Save"));
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					logger.info("Save file");
					Transaction t = new FileSaveAsTransaction("FileSave", 
						new DocumentArgument(application.getCurrentDocument()));
					t.execute();
				} catch (TechnicalException e1) {
					logger.severe(e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		this.add(item);
		
		item = new JMenuItem(bundle.getString("SaveAs"));
		item.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					logger.info("Save as");
					Transaction t = new FileSaveAsTransaction("FileSaveAs", 
						new DocumentArgument(application.getCurrentDocument()));
					t.execute();
				} catch (TechnicalException e1) {
					logger.severe(e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		this.add(item);
	}
}
