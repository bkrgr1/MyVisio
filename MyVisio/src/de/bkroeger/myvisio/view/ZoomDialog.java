package de.bkroeger.myvisio.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.StrokeBorder;

import de.bkroeger.myvisio.controller.commands.HelpArgument;
import de.bkroeger.myvisio.controller.commands.HelpTransaction;
import de.bkroeger.myvisio.controller.commands.Transaction;
import de.bkroeger.myvisio.model.ApplicationModel;
import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * <p>
 * Der Dialog zur Auswahl des Zoom-Faktors.
 * </p>
 * @author bk
 */
public class ZoomDialog extends JDialog {

	private static final long serialVersionUID = -7775430092892392392L;
	
	private static final Logger logger = Logger.getLogger(ZoomDialog.class.getName());
	
	private static ApplicationModel application;
	
	private static final String ZOOM_DIALOG =
		application.getBundle().getString("ZoomDialog");
	
	private boolean dialogResult = false;
	public  boolean getDialogResult() { return dialogResult; }
	
	private ZoomFactor zoomFactor;
	public  ZoomFactor getZoomFactor() { return zoomFactor; }
	
	private int zoomPercent;
	public  int getZoomPercent() { return zoomPercent; }
	
	private javax.swing.border.Border nullBorder = new StrokeBorder(new BasicStroke(0));

	/**
	 * Konstruktor
	 * @param parent - der übergeordnete JFrame
	 * @param factor - der derzeitige ZoomFactor
	 */
	public ZoomDialog(JFrame parent, ZoomFactor factor) {
		
		super(parent, ZOOM_DIALOG, true);
		
		// Dialog in der Mitte der Seite positionieren
		if (parent != null) {
		      Dimension parentSize = parent.getSize(); 
		      Point p = parent.getLocation(); 
		      setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		    }		
		this.zoomFactor = factor;
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
		// die Elemente in einem Grid anordnen
        GridLayout layout = new GridLayout(10,1);
        // der Abstand zwischen den Elementen beträgt 5 Pixel in alle Richtungen
        layout.setHgap(5);
        layout.setVgap(5);
        
        // einen neuen Content-Panel erzeugen
        JPanel contentPane = new JPanel(layout);
        // und einen leeren Rahmen einfügen
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JRadioButton button400 = new JRadioButton("400%");
        button400.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	        	zoomFactor = ZoomFactor.PROZ400;
	        	zoomPercent = 400;
			}
        });
        if (factor == ZoomFactor.PROZ400) button400.setSelected(true);
        contentPane.add(button400);
        
        JRadioButton button200 = new JRadioButton("200%");
        button200.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	        	zoomFactor = ZoomFactor.PROZ200;
	        	zoomPercent = 200;
			}
        });
        if (factor == ZoomFactor.PROZ200) button200.setSelected(true);
        contentPane.add(button200);
        
        JRadioButton button150 = new JRadioButton("150%");
        button150.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
	        	zoomFactor = ZoomFactor.PROZ150;
	        	zoomPercent = 150;
			}
        });
        if (factor == ZoomFactor.PROZ150) button150.setSelected(true);
        contentPane.add(button150);
        
        JRadioButton button100 = new JRadioButton(
        	application.getBundle().getString("percent100"));
        button100.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
	        	zoomFactor = ZoomFactor.PROZ100;
	        	zoomPercent = 100;
			}
        });
        if (factor == ZoomFactor.PROZ100) button100.setSelected(true);
        contentPane.add(button100);
        
        JRadioButton button75 = new JRadioButton("75%");
        button75.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
	        	zoomFactor = ZoomFactor.PROZ75;
	        	zoomPercent = 75;
			}
        });
        if (factor == ZoomFactor.PROZ75) button75.setSelected(true);
        contentPane.add(button75);
        
        JRadioButton button50 = new JRadioButton("50%");
        button50.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
	        	zoomFactor = ZoomFactor.PROZ50;
	        	zoomPercent = 50;
			}
        });
        if (factor == ZoomFactor.PROZ50) button50.setSelected(true);
        contentPane.add(button50);
        
        JRadioButton buttonBreite = new JRadioButton(
    		application.getBundle().getString("pageWidth"));
        buttonBreite.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
	        	zoomFactor = ZoomFactor.PROZWIDTH;
			}
        });
        if (factor == ZoomFactor.PROZWIDTH) buttonBreite.setSelected(true);
        contentPane.add(buttonBreite);
        
        JRadioButton buttonGanz = new JRadioButton(
    		application.getBundle().getString("fullPage"));
        buttonGanz.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
	        	zoomFactor = ZoomFactor.PROZFULL;
			}
        });
        if (factor == ZoomFactor.PROZFULL) buttonGanz.setSelected(true);
        contentPane.add(buttonGanz);
        
        // ein Teil Panel mit Button und Eingabefeld
        JPanel panelProzent = new JPanel(new BorderLayout(5, 5));
        buttonGanz.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
	        	zoomFactor = ZoomFactor.PROZVALUE;
			}
        });
        JRadioButton buttonProzent = new JRadioButton("Prozentsatz");
        panelProzent.add(buttonProzent, BorderLayout.WEST);
        
        final JTextField fieldProzent = new JTextField(100);
        fieldProzent.addFocusListener(new FocusListener() {
			
			public void focusLost(FocusEvent e) {
				try {
					zoomPercent = Integer.parseInt(fieldProzent.getText());
				} catch (NumberFormatException ex) {
					// TODO: Was ist zu tun?
				}
			}
			
			public void focusGained(FocusEvent e) {
				// no action
			}
        });
        panelProzent.add(fieldProzent, BorderLayout.CENTER);
        panelProzent.setBorder(null);
        panelProzent.setVisible(true);
        // Teilpanel einfügen
        contentPane.add(panelProzent);
        
        // die Action-Buttons in einem eigenen Panel anordnen
        JPanel actionPanel = new JPanel(new BorderLayout(5, 5));
        
        JButton okButton = new JButton("Ok");
        okButton.setBounds(5, 5, 80, 20);
        okButton.setBorder(new RoundedBorder(10));
        okButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
	        	dialogResult = true;
	        	setVisible(false);
			}
        });
        actionPanel.add(okButton, BorderLayout.CENTER);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(5, 5, 80, 20);
        cancelButton.setBorder(new RoundedBorder(10));
        cancelButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
	        	dialogResult = false;
	        	setVisible(false);
			}
        });
        actionPanel.add(cancelButton, BorderLayout.WEST);
        
        JButton helpButton = new JButton("Help");
        helpButton.setBounds(5, 5, 80, 20);
        helpButton.setBorder(new RoundedBorder(10));
        actionPanel.add(helpButton, BorderLayout.EAST);
        helpButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					Transaction t = new HelpTransaction("Help", 
						new HelpArgument(HelpArgument.HelpZoomDialogId));
					t.execute();
				} catch (TechnicalException e1) {
					logger.severe(e1.getMessage());
					e1.printStackTrace();
				}
			}
        });
        
        actionPanel.setBorder(nullBorder);
        actionPanel.setVisible(true);
        contentPane.add(actionPanel);
        
        this.getContentPane().add(contentPane);
        this.pack();
        this.setSize(250,300);
        this.setVisible(true);
	}
	
	public static enum ZoomFactor {
		PROZ400,
		PROZ200,
		PROZ100,
		PROZ150,
		PROZ75,
		PROZ50,
		PROZWIDTH,
		PROZFULL,
		PROZVALUE;
	}
}
