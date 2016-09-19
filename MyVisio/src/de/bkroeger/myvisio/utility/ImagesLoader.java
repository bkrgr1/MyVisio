package de.bkroeger.myvisio.utility;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImagesLoader {

	private GraphicsConfiguration gc;

	public ImagesLoader() {
		initLoader();
	}

	private void initLoader() {
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
	} // end of initLoader()

	/**
	 * Load the image from the given filename, returning it as a BufferedImage.
	 * 
	 * @param fnm
	 * @return
	 * @throws TechnicalException
	 */
	public BufferedImage loadImage(String fnm) throws TechnicalException {
		try {
			BufferedImage im = ImageIO.read(new File(fnm));
			// An image returned from ImageIO in J2SE <= 1.4.2 is
			// _not_ a managed image, but is after copying!

			int transparency = im.getColorModel().getTransparency();
			BufferedImage copy = gc.createCompatibleImage(im.getWidth(),
					im.getHeight(), transparency);
			// create a graphics context
			Graphics2D g2d = copy.createGraphics();
			// g2d.setComposite(AlphaComposite.Src);

			// reportTransparency(IMAGE_DIR + fnm, transparency);

			// copy image
			g2d.drawImage(im, 0, 0, null);
			g2d.dispose();
			return copy;
		} catch (IOException e) {
			throw new TechnicalException(e.getMessage());
		}
	} // end of loadImage() using ImageIO
}
