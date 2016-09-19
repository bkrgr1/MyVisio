package de.bkroeger.myvisio.view;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import de.bkroeger.myvisio.controller.StartDialogController;
import de.bkroeger.myvisio.model.ApplicationModel;
import de.bkroeger.myvisio.model.Category;
import de.bkroeger.myvisio.model.ExampleWorkbook;
import de.bkroeger.myvisio.utility.ImagesLoader;
import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * <p>
 * Der Start-Dialog ermöglicht die Auswahl eines neuen Workbooks, für eine der
 * angebotenen Kategorien.
 * </p>
 * @author bk
 */
public class StartDialog extends JDialog implements AbstractViewPanel {

	private static final long serialVersionUID = 1068355608959820995L;
	
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(StartDialog.class.getName());
	
	private static final int NUM_PICTURES = 3;

	// Resource-Bundle
	private static ResourceBundle bundle = ApplicationModel.getBundle();
	
	// der Titel des Dialogs
	private static final String title = bundle.getString("start.dialog.title");

	// Margin rund um den Dialog
	private static final int margin = 20;

	// der Controller
	private StartDialogController controller;

	// die Buttons für den Dialog
	private JButton okButton = new JButton("OK");
	public void enableOkButton()  { 
		okButton.setEnabled(true); 
		okButton.addActionListener(controller);
	}
	public void disableOkButton() { okButton.setEnabled(false); }
	private JButton cancelButton = new JButton("CANCEL");
	
	private JPanel samplePanel = null;

	/**
	 * Konstruktor für die StartDialog-Ansicht.
	 * 
	 * @param controller
	 *            - der Controller für den Dialog
	 * @param frame
	 *            - der Parent-Frame
	 * @return 
	 */
	public StartDialog(StartDialogController controller, Frame frame) {

		JDialog.setDefaultLookAndFeelDecorated(true);

		controller.addView(this);
		this.controller = controller;
		
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.addWindowListener(controller);
		this.setTitle(title);
		this.setModal(true);

		this.setLayout(new BorderLayout());

		// zeige links (WEST) eine Liste der Kategorien an
		this.add(createCategoryListView(controller), BorderLayout.WEST);

		// zeige in der Mitte (CENTER) ein Panel an,
		// das je nach ausgewählter Kategorie Beispiel-Bilder anzeigt
		buildSamplePanel();
		this.add(samplePanel, BorderLayout.CENTER);
	
		fillSamplePanel(controller.getCurrentCategory());

		// zeige unten (SOUTH) ein Panel mit den Buttons an
		this.add(buildButtonPanel(controller), BorderLayout.SOUTH);

		// Dialog in der Mitte der Seite positionieren
		if (frame != null) {
			Dimension parentSize = frame.getSize();
			Dimension maximumSize = 
				new Dimension(parentSize.width - (2 * margin),
					parentSize.height - (2 * margin));
			this.setSize(maximumSize);
			this.setLocationRelativeTo(frame);
		}
	}

	/**
	 * Erzeugt den Panel für die Template-Examples
	 * @return
	 */
	private void buildSamplePanel() {
		samplePanel = new JPanel();
		samplePanel.setBorder(BorderFactory.createRaisedBevelBorder());
		samplePanel.setVisible(true);
	}
	
	/**
	 * Füllt die Beispielbilder.
	 * 
	 * @param category - die ausgewählte Kategorie
	 */
	public void fillSamplePanel(Category category) {
		
		samplePanel.removeAll();
		int maxCol = NUM_PICTURES;
		Dimension dim = samplePanel.getSize();
		if (dim.width != 0) {
			maxCol = dim.width / 200;
		}
		samplePanel.setLayout(new GridBagLayout());
		
		int row = 0;
		int col = 0;
		for (int i=0; i<category.getExamples().size(); i++) {
			
			ExampleWorkbook example = category.getExamples().get(i);
			
			if (example.getPicturePath() != null) {
				
				GridBagConstraints c = new GridBagConstraints();
				c.fill = GridBagConstraints.NONE;
				c.gridx = col;
				c.gridy = row;
				c.weightx = 0.33;
				c.weighty = 1.0;
				c.insets = new Insets(2,2,2,2);
				c.ipadx = 3;
				c.ipady = 3;
				c.anchor = GridBagConstraints.FIRST_LINE_START;
				
				PicturePanel pic = new PicturePanel(example);
				pic.setFocusable(true);
				pic.setEnabled(true);
				pic.addMouseListener(controller);
				samplePanel.add(pic, c);
				
				col = col + 1;
				if (col >= maxCol) {
					col = 0;
					row = row + 1;
				}
			}
		}
	}

	/**
	 * Erzeugt den Hilfspanel mit den Buttons
	 * @param controller
	 * @return
	 */
	private JPanel buildButtonPanel(StartDialogController controller) {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.setBorder(BorderFactory.createRaisedBevelBorder());

		cancelButton.setActionCommand("Cancel");
		cancelButton.addActionListener(controller);
//		cancelButton.setBorder(BorderFactory.createEtchedBorder())
		cancelButton.setMargin(new Insets(2, 5, 2, 5));
		cancelButton.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon cancelIcon = new ImageIcon(getClass().getResource("/resources/cancel.png"));
		cancelButton.setIcon((Icon) cancelIcon);
		buttonPanel.add(cancelButton, BorderLayout.WEST);

		okButton.setActionCommand("Ok");
		okButton.setEnabled(false);
		okButton.addActionListener(controller);
//		okButton.setBorder(BorderFactory.createEtchedBorder());
		okButton.setMargin(new Insets(2, 5, 2, 5));
		okButton.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon okIcon = new ImageIcon(getClass().getResource("/resources/ok.png"));
		okButton.setIcon((Icon) okIcon);
		
		buttonPanel.add(okButton, BorderLayout.EAST);
		return buttonPanel;
	}

	/**
	 * Erzeugt eine JList/JScrollPane der Categories.
	 * 
	 * @param controller
	 * @return
	 */
	private JScrollPane createCategoryListView(StartDialogController controller) {

		ListModel<Category> categories = controller.getCategories();
		JList<Category> list = new JList<Category>(categories);
		
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setCellRenderer(new CategoryListCellRenderer());
		list.setLayout(new BorderLayout());
		list.setVisibleRowCount(-1);
		list.setBorder(BorderFactory.createRaisedBevelBorder());

		ListSelectionModel listSelectionModel = list.getSelectionModel();
		listSelectionModel
				.addListSelectionListener((StartDialogController) controller);

		list.setVisible(true);
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(150, 80));
//		listScroller.setBorder(BorderFactory.createRaisedBevelBorder());

		return listScroller;
	}

	/**
	 * Erzeugt eine Example-Ansicht
	 * @param examples
	 * @return
	 * @throws TechnicalException
	 */
	private JPanel createExampleView(List<ExampleWorkbook> examples)
			throws TechnicalException {

		int count = examples.size();
		// TODO: woher bekomme ich die Breite des Panels?
		int xCnt = 5;
		int yCnt = count / xCnt + 1;

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(xCnt, yCnt));
		panel.setBorder(BorderFactory.createRaisedBevelBorder());
		ImagesLoader loader = new ImagesLoader();

		int x = 0, y = 0;
		for (ExampleWorkbook example : examples) {
			Canvas canvas = new Canvas();
			canvas.setPreferredSize(new Dimension(500, 500));

			Image image = loader.loadImage(example.getPicturePath());
			canvas.prepareImage(image, controller);
			panel.add(canvas, x, y);
			x += 1;
			if (x > xCnt) {
				x = 0;
				y += 1;
			}
		}

		panel.setVisible(true);
		return panel;
	}

	/**
	 * 
	 */
	public void clearExamplePanel() {
		this.add(null, BorderLayout.CENTER);
	}

	/**
	 * 
	 * @param examples
	 * @throws TechnicalException
	 */
	public void setExampleView(List<ExampleWorkbook> examples)
			throws TechnicalException {

		JPanel examplePanel = createExampleView(examples);
		this.add(examplePanel, BorderLayout.CENTER);
	}

	/**
	 * 
	 */
	public void modelPropertyChange(PropertyChangeEvent evt) {

		if (evt.getPropertyName().equals("ExampleList")) {
			@SuppressWarnings("unchecked")
			List<ExampleWorkbook> examples = (List<ExampleWorkbook>) evt
					.getNewValue();
			if (examples == null || examples.size() < 1) {
				clearExamplePanel();
			} else {
				try {
					setExampleView(examples);
				} catch (TechnicalException e) {
					clearExamplePanel();
				}
			}
		}
	}
}
