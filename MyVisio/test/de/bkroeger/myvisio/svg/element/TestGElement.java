package de.bkroeger.myvisio.svg.element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.svg.SVGGElement;
import org.xml.sax.SAXException;

import de.bkroeger.myvisio.shapes.IShape;
import de.bkroeger.myvisio.svg.elements.SVGGElementImpl;
import de.bkroeger.myvisio.utility.TechnicalException;

public class TestGElement {
	
	private static final String SHAPE_TEST1 = "D:\\Projekte\\workspace\\MyVisio\\test\\ShapeTest1.xml";
	
	private static org.w3c.dom.Document doc = null;

	/**
	 * @throws TechnicalException
	 */
	@Before
	public void setup() throws TechnicalException {
		
		InputStream in = null;
		String filename = SHAPE_TEST1;
		try {
			
			File f = new File(filename);
			in = new FileInputStream(f);
			boolean dtdValidate = false;
			boolean xsdValidate = false;
			
			// XML-Datei in DOM-Struktur parsen
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
		    dbf.setValidating(dtdValidate || xsdValidate);
			DocumentBuilder db = dbf.newDocumentBuilder(); 
			doc = db.parse(in);
				
		} catch(ParserConfigurationException e) {
			e.printStackTrace();
			System.err.println("Error parsing XML-Document '"+filename+"': "+e.getMessage());
			throw new TechnicalException("Error parsing XML-Document '"+filename+"'", e);
			
		} catch (SAXException e) {
			e.printStackTrace();
			System.err.println("Error processing XML-Document '"+filename+"': "+e.getMessage());
			throw new TechnicalException("Error processing XML-Document '"+filename+"'", e);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error reading XML-Document '"+filename+"': "+e.getMessage());
			throw new TechnicalException("Error reading XML-Document '"+filename+"'", e);
			
			
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
	
	@After
	public void terminate() {
		
		if (doc != null) {
			doc = null;
		}
	}

	/**
	 * @throws TechnicalException
	 */
	@Test
	public void TestParseXml() throws TechnicalException {
		
		SVGGElementImpl gElement = new SVGGElementImpl();
		gElement.parseXml(doc.getDocumentElement());
		assertEquals("Square-Shape", gElement.getId());
		assertEquals("Square Group", gElement.getTitle() != null ? gElement.getTitle().toString() : "<null>");
		assertEquals("Ein Quadrat.", gElement.getDescription() != null ? gElement.getDescription().toString() : "<null>");
		assertNotNull(gElement.getMetadata());
	}
}
