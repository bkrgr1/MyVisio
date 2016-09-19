package de.bkroeger.myvisio.view;

import java.awt.BorderLayout;
import java.awt.DisplayMode;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;

import org.pushingpixels.flamingo.api.ribbon.JRibbonFrame;

import de.bkroeger.myvisio.controller.ApplicationController;
import de.bkroeger.myvisio.model.ApplicationModel;
import de.bkroeger.myvisio.model.Workbook;
import de.bkroeger.myvisio.view.menu.MainRibbon;

/**
 * <p>
 * Dies ist das Hauptfenster der Anwendung.
 * </p>
 * @author bk
 */
public class ApplicationView extends JRibbonFrame implements AbstractViewPanel {

	private static final long serialVersionUID = -7062777952579323726L;
    
	private static final String APPLICATION_TITLE = "My Visio";

	private boolean maximized = true;
	
	private NavigatorTabView navigator;
	public  NavigatorTabView getNavigator() { return navigator; }
	
	private WorksheetTabView worksheetTabView;
	public  WorksheetTabView getMainCanvas() { return worksheetTabView; }
	
	@SuppressWarnings("unused")
	private Workbook currentDocument;
	
	private JSplitPane splitScreen;
	
	private StandardFooter footer;

	/**
	 * Konstruktor.
	 * 
	 * @param title
	 * @param docMap
	 */
	public ApplicationView(ApplicationController controller, ApplicationModel model) {
		super(APPLICATION_TITLE);
		
		// Frame maximieren
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH );
		 
        // Was soll bei Klick auf das System-X rechts oben passieren:
        // Das Programm soll gänzlich beendet werden.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        new MainRibbon(this);
		
		splitScreen = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitScreen.setOneTouchExpandable(true);
		splitScreen.setResizeWeight(0.25);
		
		this.add(splitScreen, BorderLayout.CENTER);

        
        // Footer hinzufügen
        footer = new StandardFooter(this);
        this.add(footer, BorderLayout.SOUTH);
		
		// den Controller als WindowListener registrieren
		addWindowListener((WindowListener) controller);
	}
	
	public void addLeft(NavigatorTabView view) {
		this.splitScreen.setLeftComponent(view);
	}
	
	public void addRight(WorksheetTabView view) {
		this.splitScreen.setRightComponent(view);
	}

	public void putStatusMessage(String msg) {
		this.footer.putStatusMessage(msg);
	}

	public void clearStatusMessage() {
		this.footer.clearStatusMessage();
	}
	
	public void modelPropertyChange(PropertyChangeEvent evt) {
		
		
		if (evt.getPropertyName().equals("mainPanel")) {
			// den Panel im Hauptteil setzen
			setMainPanel(evt);
			
			
		} else
		if (evt.getPropertyName().equals("frameTitle")) {
			// den Titel setzen
			this.setTitle((String) evt.getNewValue());
			
		} else 
		if (evt.getPropertyName().equals("mainPanel")) {
//			// den Mittelteil setzen
//			this.add((Window) evt.getNewValue(), BorderLayout.CENTER);

		} else
		if (evt.getPropertyName().equals("statusDisplay")) {
			// den Statusbereich setzen
			setStatusDisplay(evt);
		}
	}

	private void setStatusDisplay(PropertyChangeEvent evt) {
		if (evt.getNewValue() != null)
			this.add((JPanel) evt.getNewValue(), BorderLayout.SOUTH);
		else
			this.add(footer, BorderLayout.SOUTH);
	}

	private void setMainPanel(PropertyChangeEvent evt) {
		JPanel mainPanel = (JPanel) evt.getNewValue();
		this.add(mainPanel, BorderLayout.CENTER);
		this.pack();
		if(maximized){
	        DisplayMode mode = this.getGraphicsConfiguration().getDevice().getDisplayMode();
	        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
	        this.setMaximizedBounds(new Rectangle(
	                mode.getWidth() - insets.right - insets.left, 
	                mode.getHeight() - insets.top - insets.bottom
	        ));
	        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	    }else{
	        this.setExtendedState(JFrame.NORMAL);
	    }
	}
}
