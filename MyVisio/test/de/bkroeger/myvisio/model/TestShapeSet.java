package de.bkroeger.myvisio.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import de.bkroeger.myvisio.shapes.IShape;
import de.bkroeger.myvisio.utility.BusinessException;
import de.bkroeger.myvisio.utility.TechnicalException;

/**
 * @author bk
 * @version 1.0
 */
public class TestShapeSet {

	private static final String SHAPESET_NAME = "Standard";
	private static final String STANDARD_SHAPESET = "D:\\Projekte\\workspace\\MyVisio\\src\\resources\\shapeSets\\Standard.xml";

	/**
	 * @throws TechnicalException
	 * @throws IOException
	 * @throws BusinessException 
	 */
	@Test
	public void TestLoad() throws TechnicalException, IOException, BusinessException {
		
		ShapeSet set = new ShapeSet(SHAPESET_NAME);
		InputStream in = null;
		try {
			
			File f = new File(STANDARD_SHAPESET);
			in = new FileInputStream(f);
			set.load(in);
			
			assertEquals(4, set.getShapesSize());
			IShape shape = set.getShapeAt(0);
			assertEquals("Square-Shape", shape.getId());
			assertEquals("Square Group", shape.getTitle() != null ? shape.getTitle().toString() : "<null>");
			assertEquals("Ein Quadrat.", shape.getDescription() != null ? shape.getDescription().toString() : "<null>");
			assertNotNull(shape.getMetadata());
			
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
