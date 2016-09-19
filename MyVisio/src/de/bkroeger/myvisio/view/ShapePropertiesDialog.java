package de.bkroeger.myvisio.view;

import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class ShapePropertiesDialog extends JDialog {

	private static final long serialVersionUID = 1795787306636675961L;

	public ShapePropertiesDialog(Frame frame, String title, boolean modal) {
		super(frame, title, modal);
		
		final JOptionPane optionPane = new JOptionPane(
                "The only way to close this dialog is by\n"
                + "pressing one of the following buttons.\n"
                + "Do you understand?",
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION);
		
		this.setContentPane(optionPane);
	}
}
