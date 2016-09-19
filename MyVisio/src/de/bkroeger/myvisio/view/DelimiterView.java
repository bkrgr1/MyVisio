package de.bkroeger.myvisio.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import de.bkroeger.myvisio.controller.DelimiterController;

public class DelimiterView extends JPanel {
	
	private static final long serialVersionUID = 7477283318136471824L;
	
	private static final int MARKER_HEIGHT = 40;
	private static final int MARKER_WIDTH = 6;
	
	@SuppressWarnings("unused")
	private DelimiterController controller;

	public DelimiterView(DelimiterController controller, Dimension dimension) {
		
		this.controller = controller;
		
		this.setPreferredSize(dimension);
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		this.setLayout(new GridBagLayout());
		this.setFocusable(false);
		
		createMarker();
		
		this.setVisible(true);
	}
	
	public JPanel createMarker() {
		
		JPanel marker = new JPanel();
		marker.setMaximumSize(new Dimension(MARKER_WIDTH, MARKER_HEIGHT));
		marker.setBackground(Color.DARK_GRAY);
		marker.setFocusable(true);
		marker.addMouseListener(controller);
		marker.addMouseMotionListener(controller);
		
		this.add(marker, new GridBagConstraints());
		return marker;
	}
}
