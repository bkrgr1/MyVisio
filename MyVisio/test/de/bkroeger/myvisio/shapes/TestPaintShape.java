package de.bkroeger.myvisio.shapes;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Canvas;
import java.awt.Color;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestPaintShape {
	
	private JPanel canvas;

	@Before
	public void setup() {
		   final String title = "Test Window";
		    final int width = 1200;
		    final int height = width / 16 * 9;

		    //Creating the frame.
		    JFrame frame = new JFrame(title);

		    frame.setSize(width, height);
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.setLocationRelativeTo(null);
		    frame.setResizable(false);
		    frame.setVisible(true);

		    //Creating the canvas.
		    canvas = new JPanel();

		    canvas.setSize(width, height);
		    canvas.setBackground(Color.WHITE);
		    canvas.setVisible(true);
		    canvas.setFocusable(false);

		    //Putting it all together.
		    frame.add(canvas);
		    frame.setVisible(true);
	}
	
	@After
	public void terminate() {
		
	}
	
	@Test
	public void TestPaintRectangle() {
		
	
	}
}
