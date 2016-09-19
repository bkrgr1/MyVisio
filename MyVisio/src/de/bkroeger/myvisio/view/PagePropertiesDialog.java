package de.bkroeger.myvisio.view;

import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class PagePropertiesDialog extends JDialog {

	private static final long serialVersionUID = 1795787306636675961L;

	public PagePropertiesDialog(Frame frame, String title, boolean modal) {
		super(frame, title, modal);
		
		final JOptionPane optionPane = new JOptionPane(
                "The only way to close this dialog is by\n"
                + "pressing one of the following buttons.\n"
                + "Do you understand?",
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION);
		
		this.setContentPane(optionPane);
//		this.setDefaultCloseOperation(
//		    JDialog.DO_NOTHING_ON_CLOSE);
//		this.addWindowListener(new WindowAdapter() {
//		    public void windowClosing(WindowEvent we) {
//		        setLabel("Thwarted user attempt to close window.");
//		    }
//		});
//		optionPane.addPropertyChangeListener(
//		    new PropertyChangeListener() {
//		    	
//		        public void propertyChange(PropertyChangeEvent e) {
//		            String prop = e.getPropertyName();
//
//		            if (dialog.isVisible() 
//		             && (e.getSource() == optionPane)
//		             && (prop.equals(JOptionPane.VALUE_PROPERTY))) {
//		                //If you were going to check something
//		                //before closing the window, you'd do
//		                //it here.
//		                dialog.setVisible(false);
//		            }
//		        }
//		    });
		this.pack();
		this.setVisible(true);
	}
}
