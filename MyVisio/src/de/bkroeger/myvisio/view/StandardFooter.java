package de.bkroeger.myvisio.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import de.bkroeger.myvisio.model.ApplicationModel;

/**
 * <p>
 * Dies ist die Fußzeile der Anwendung. In ihr werden aktuelle Statusinformationen angezeigt.
 * </p>
 * @author bk
 */
public class StandardFooter extends JPanel {

	private static final int FOOTER_HEIGHT = 30;

	private static final long serialVersionUID = -6043056813592438416L;
	
	private JTextField field1;
	private JTextField field2;
//	private JTextField field3;

	/**
	 * Konstruktor.
	 * 
	 * @param bundle
	 */
	public StandardFooter(ApplicationView parent) {
		super();
		
		this.setBorder(new LineBorder(Color.black));
		this.setPreferredSize(new Dimension(parent.getWidth(), FOOTER_HEIGHT));
		
		field1 = new JTextField("My Visio "+ApplicationModel.getVersion());
		this.add(field1, BorderLayout.WEST);
		
//		field2 = new JTextField();
//		this.add(field2, BorderLayout.CENTER);
//		
//		field3 = new JTextField();
//		this.add(field3, BorderLayout.EAST);
		
		this.setVisible(true);
	}
	
	/**
	 * Zeigt eine Status-Meldung an.
	 * @param msg
	 */
	public void putStatusMessage(String msg) {
		
		this.field2.setText(msg);
		this.add(field2, BorderLayout.CENTER);
	}
	
	/**
	 * Löscht die Status-Meldung.
	 */
	public void clearStatusMessage() {
		this.field2.setText("");
		for (int i=0; i<this.getComponents().length; i++) {
			Component comp = this.getComponent(i);
			if (comp == this.field2) {
				this.remove(i);
				break;
			}
		}
	}
}
