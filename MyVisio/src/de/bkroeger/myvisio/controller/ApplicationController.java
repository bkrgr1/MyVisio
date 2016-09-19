package de.bkroeger.myvisio.controller;

import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URL;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import de.bkroeger.myvisio.model.ApplicationModel;
import de.bkroeger.myvisio.model.Category;
import de.bkroeger.myvisio.model.ExampleWorkbook;
import de.bkroeger.myvisio.model.ShapeSet;
import de.bkroeger.myvisio.model.StartDialogModel;
import de.bkroeger.myvisio.model.Workbook;
import de.bkroeger.myvisio.model.Worksheet;
import de.bkroeger.myvisio.utility.BusinessException;
import de.bkroeger.myvisio.utility.TechnicalException;
import de.bkroeger.myvisio.view.ApplicationView;
import de.bkroeger.myvisio.view.NavigatorTabView;
import de.bkroeger.myvisio.view.WorksheetTabView;

/**
 * <p>
 * Dies ist der Controller für die {@link ApplicationView Gesamt-Ansicht}.
 * Er steuert alle anderen Views und Controller.
 * </p><p>
 * Diese Ansicht zeigt ein einzelnes Workbook an. Wenn ein weiteres Workbook geöffnet wird
 * oder ein neues Workbook erzeugt wird, wird eine weitere Instanz gestartet.
 * </p>
 * @author bk
 */
public class ApplicationController extends BaseController implements WindowListener {

	private static final String SHAPESET_STANDARD = "Standard";

	private Logger logger = Logger.getLogger(ApplicationController.class.getName());
	
	private static final int DEFAULT_NAVIGATOR_WIDTH = 300;
	private static final int DELIMITER_WIDTH = 10;
	private static final int BORDER_WIDTH = 4;
	
	/**
	 * Das Hauptfenster der Anwendung
	 */
    private ApplicationView view;
    /**
     * @return - die Gesamt-Ansicht
     */
    public  ApplicationView getView() { return this.view; }
    
    /**
     * Das Navigator-Fenster
     */
    private NavigatorTabView navigatorTabView;
    /**
     * @return - die Navigations-Ansicht
     */
    public  NavigatorTabView getNavigatorView() { return this.navigatorTabView; }
    
    /**
     * Das Dokument-Fenster
     */
    private WorksheetTabView worksheetTabView;
    /**
     * @return - die Dokument-Ansicht
     */
    public  WorksheetTabView getDocumentView() { return this.worksheetTabView; }
	
    private NavigatorTabController navigatorTabController;
    /**
     * @return - der Controller für die Navigation
     */
    public  NavigatorTabController getNavigatorController() { return this.navigatorTabController; }
    
    private WorksheetTabController worksheetTabController;
    /**
     * @return - der Controller für das Dokument
     */
    public  WorksheetTabController getDocumentController() { return this.worksheetTabController; }
    
    /**
     * Das Model der Anwendung.
     */
	private ApplicationModel model;
	/**
	 * @return - das Model der Anwendung
	 */
	public  ApplicationModel getModel() { return this.model; }

	/**
	 * Constructor
	 * 
	 * @param model - das Application-Model
	 */
	public ApplicationController(ApplicationModel model) {
		
		this.model = model;
		this.view = new ApplicationView(this, model);
	}
	
	/**
	 * Zeigt die Hauptansicht
	 * und wenn kein Document angegeben wurde, den Start-Dialog.
	 */
	public void showApplicationView() {
		
		ApplicationView.setDefaultLookAndFeelDecorated(true);
		
		try {
			// das Haupt-Fenster anzeigen
			this.view.setVisible(true);
			
			//  wurde ein Dokument beim Aufruf mit angegeben?
			if (model.getDocuments().size() == 0) {
				
				// nein, Auswahldialog anzeigen
				showStartDialog();
				
			} else {
				
				showWorkbook(model.getDocuments().values().iterator().next());
			}
			
		} catch (TechnicalException e) {
			e.printStackTrace();
			System.out.println("Technical exception: "+e.getMessage());
			System.exit(ApplicationModel.EXITCODE_TECHNICAL_EXECPTION);
		} catch (BusinessException e) {
			
		}
	}
	
	/**
	 * Zeigt den Startdialog an und öffnet das ausgewählte Dokument.
	 * 
	 * @throws TechnicalException
	 * @throws BusinessException 
	 */
	public void showStartDialog() throws TechnicalException, BusinessException {
		
		// erzeuge das Datenmodell für den Start-Dialog
		StartDialogModel startDialogModel = new StartDialogModel();
		// erzeuge einen Controller für den StartDialog
		StartDialogController startDialogController = 
				new StartDialogController(this.view, startDialogModel);
		// zeige den StartDialog an und prüfe,
		// ob der Dialog mit OK beendet wurde?
		if (startDialogController.showDialog() == true) {
			
			// die ausgewählte Category ermitteln
			// und damit ein leeres Dokument erstellen
			Category category = startDialogModel.getSelectedCategory();
			ExampleWorkbook example = startDialogModel.getExample();
			model.setCategory(category);
			
			// Beispiel-Dokument öffnen
			URL url = getClass().getResource(example.getModelPath());
			String p = url.getPath().substring(1);
			Workbook workbook = new Workbook(p);
			model.addDocument(workbook);
			
			// zeige den Navigator an
			showWorkbook(workbook);
		}
	}

	private void showWorkbook(Workbook workbook) throws TechnicalException, BusinessException {

		if (workbook.getShapeSetSize() == 0) {
			
			ShapeSet shapeSet = model.getShapeSet(SHAPESET_STANDARD);
			if (shapeSet == null) {
				shapeSet = ShapeSet.getShapeSet(SHAPESET_STANDARD);
				model.addShapeSet(shapeSet);
			}
			workbook.addShapeSet(shapeSet);
		}
		
		if (workbook.getWorksheetSize() == 0) {
			workbook.addWorksheet(Worksheet.createDefaultWorksheet(workbook));
		}

		navigatorTabController = new NavigatorTabController(view, workbook,
				DEFAULT_NAVIGATOR_WIDTH);
		
		worksheetTabController = new WorksheetTabController(view, workbook,
				DEFAULT_NAVIGATOR_WIDTH+DELIMITER_WIDTH+(4*BORDER_WIDTH));	
		
		this.view.addLeft(navigatorTabController.getView());
		this.view.addRight(worksheetTabController.getView());
		
		// zeige das Dokument an
		navigatorTabController.show();
		worksheetTabController.show();
		
		String dirtyMarker = workbook.isDirty() ? " (*)" : "";
		setApplicationTitle("MyVisio - "+workbook.getTitle()+dirtyMarker);
	}
	
	/**
	 * Zeigt eine Fehlermeldung an.
	 * @param msg
	 * @param title
	 * @param paneType
	 */
	public void showMessageDialog(String msg, String title, int paneType) {
		JOptionPane.showMessageDialog(this.view,
			    msg,
			    title,
			    paneType); 
	}
	
	/**
	 * Gibt eine Statusmeldung im Fuss des Hauptfensters aus.
	 * @param msg
	 */
	public void putStatusMessage(String msg) {
		this.view.putStatusMessage(msg);
	}
	
	/**
	 * Löscht die Status-Anzeige
	 */
	public void clearStatusMessage() {
		this.view.clearStatusMessage();
	}
	
	/**
	 * @param title - Titel der Anwendung
	 */
	public void setApplicationTitle(String title) {
		this.view.setTitle(title);
	}
	
	/**
	 * Gibt die Resourcen frei.
	 */
	public void dispose() {
		
//		mainMenu.dispose();
		view.dispose();
	}
	
	/**
	 * WindowListener-Methoden
	 */

	public void windowActivated(WindowEvent arg0) {
		logger.info("Main window activated.");
	}

	public void windowClosed(WindowEvent arg0) {
		logger.info("Main window closed.");
	}

	public void windowClosing(WindowEvent e) {
		logger.info("Main window is closing.");
		
        model.terminate();
        this.dispose();
        e.getWindow().dispose();
	}

	public void windowDeactivated(WindowEvent arg0) {
//		logger.info("Main window deactivated.");
	}

	public void windowDeiconified(WindowEvent arg0) {
//		logger.info("Main window deiconified.");
	}

	public void windowIconified(WindowEvent arg0) {
//		logger.info("Main window iconified.");
	}

	public void windowOpened(WindowEvent arg0) {
//		logger.info("Main window opened.");
	}
	
	@Override
	public void moveHorizontally(int diff, MouseEvent e) {
		
		if (diff != 0) {
			navigatorTabController.resizeHorizontally(diff * +1, e);
			worksheetTabController.resizeHorizontally(diff * -1, e);
		}
	}
}
