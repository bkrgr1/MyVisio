package de.bkroeger.myvisio.view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JTabbedPane;

import de.bkroeger.myvisio.controller.NavigatorTabController;

/**
 * <p>
 * Dies ist der Navigations- und Menu-Bereich links vom Zeichenfenster.
 * Er zeigt die ShapeSets in einem vertikalen TabView an.
 * </p>
 * @author bk
 */
public class NavigatorTabView extends JTabbedPane {

	private static final long serialVersionUID = 9158831812134525333L;
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(NavigatorTabView.class.getName());

	@SuppressWarnings("unused")
	private NavigatorTabController controller;
	
	@SuppressWarnings("unused")
	private int navigatorWidth = 300;

	/**
	 * Konstruktor.
	 * @param controller 
	 * @param dimension 
	 */
	public NavigatorTabView(NavigatorTabController controller, Dimension dimension) {
		super(JTabbedPane.TOP, JTabbedPane.WRAP_TAB_LAYOUT);
		
		this.controller = controller;
		
		this.setBorder(BorderFactory.createRaisedBevelBorder());
		this.setPreferredSize(dimension);
		this.setMinimumSize(dimension);
		this.setBackground(new Color(161, 212, 144));	// #A1D490
		this.navigatorWidth = dimension.width;
	}
}
