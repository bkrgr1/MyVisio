package de.bkroeger.myvisio.view.menu;

import javax.swing.JButton;
import javax.swing.JToolBar;

import de.bkroeger.myvisio.view.ApplicationView;

public class MainToolBar extends JToolBar {
	
	private static final long serialVersionUID = 4015999200630071802L;

	public MainToolBar(ApplicationView frame) {

		JButton button = new JButton("button 1");
		this.add(button);
		this.addSeparator();

		this.add(new JButton("button 2"));
		this.addSeparator();

//		this.add(new JComboBox(new String[] { "A", "B", "C" }));
	}
}
