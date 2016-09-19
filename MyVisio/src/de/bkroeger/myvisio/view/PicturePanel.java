package de.bkroeger.myvisio.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import de.bkroeger.myvisio.model.ExampleWorkbook;

public class PicturePanel extends JPanel {
	
	private static final long serialVersionUID = -382829958284084959L;
	
	private BufferedImage image;
	
	private ExampleWorkbook example;
	public  ExampleWorkbook getExample() { return this.example; }
	
	private int newWidth = 200;
	private int newHeight = 200;


	/**
	 * Konstruktor
	 * @param image
	 */
	public PicturePanel(ExampleWorkbook example) {
		this.example = example;
		
		try {
			this.image = ImageIO.read(getClass().getResource(example.getPicturePath()));
			this.image = resizeImage(image, newWidth, newHeight, image.getType());
			init(this.image);
			
		} catch (IOException e) {
			e.printStackTrace();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
	}

	private void init(Image image) {
		this.setBorder(BorderFactory.createEtchedBorder());
		Dimension dim = new Dimension(newWidth, newHeight);
		this.setSize(dim);
		this.setPreferredSize(dim);
		this.setMinimumSize(dim);
		
		// TODO: Name anzeigen
		repaint();
	}
	
	/**
	 * Überschriebene paint-Methode
	 * @param g - Graphics context
	 */
	@Override
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
      
        g.drawImage(image, 0,0, this);
    }
	
	private BufferedImage resizeImage(BufferedImage originalImage, 
			int width, int height, int type) throws IOException {  
        BufferedImage resizedImage = new BufferedImage(width, height, type);  
        Graphics2D g = resizedImage.createGraphics();  
        g.drawImage(originalImage, 0, 0, width, height, null);  
        g.dispose();  
        return resizedImage;  
    }  }
