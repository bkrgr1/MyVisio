package de.bkroeger.myvisio.view.menu;

import java.util.ResourceBundle;

import javax.swing.JMenuBar;

import de.bkroeger.myvisio.model.ApplicationModel;
import de.bkroeger.myvisio.view.ApplicationView;

/**
 * <p>
 * Die MenuBar zeigt die Standard-Menü-Einträge an.
 * </p>
 * @author bk
 */
public class MainMenuBar extends JMenuBar {

	private static final long serialVersionUID = -9108973100880107489L;
	
	private ApplicationView frame;
	public  ApplicationView getFrame() { return this.frame; }

	/**
	 * Konstruktor für das Hauptmenü.
	 * @param frame
	 * @param application
	 */
	public MainMenuBar(ApplicationView frame) {
		super();
		this.frame = frame;
		
		ResourceBundle bundle = ApplicationModel.getBundle();
		
		this.add(new FileMenu(this, bundle.getString("File"), bundle));
		
		this.add(new EditMenu(this, bundle.getString("Edit"), bundle));
		
		this.add(new ViewMenu(this, bundle.getString("View"), bundle));
		
		this.add(new InsertMenu(this, bundle.getString("Insert"), bundle));
		
		this.add(new FormatMenu(this, bundle.getString("Format"), bundle));
		
		this.add(new ToolMenu(this, bundle.getString("File"), bundle));
		
		this.add(new ShapeMenu(this, bundle.getString("Shape"), bundle));
		
		this.add(new HelpMenu(this, bundle.getString("Help"), bundle));
		
		this.frame.setJMenuBar(this);
	}
	
	/**
	 * Gibt die Resourcen frei.
	 */
	public void dispose() {
		
//		for (int i=0; i<this.getComponentCount(); i++) {
//			Component comp = this.getComponent(i);
//			comp.enable(false);
//		}
	}
}
