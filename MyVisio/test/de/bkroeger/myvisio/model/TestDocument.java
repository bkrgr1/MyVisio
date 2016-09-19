package de.bkroeger.myvisio.model;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import de.bkroeger.myvisio.model.Workbook;
import de.bkroeger.myvisio.model.Worksheet;
import de.bkroeger.myvisio.shapes.DrawingCategory;
import de.bkroeger.myvisio.utility.TechnicalException;

public class TestDocument {

	/**
	 * <p>
	 * Konstruktor für leeres/neues Visio-Document.
	 * </p><p>
	 * Für ein neues Dokument wird ein Titel vergeben,
	 * das Page-Set initialisiert 
	 * und eine erste leere Seite erzeugt. Dies ist die aktuelle/Default-Seite.
	 * Der Filename bleibt leer
	 * @throws TechnicalException 
	 */
	@Test
	public void testConstructor1() throws TechnicalException{
		
		Workbook visioDoc = new Workbook();
		
		Assert.assertNotNull(visioDoc.getCurrentWorksheet());
		Assert.assertNotNull(visioDoc.getCurrentPageUUID());
	}
	
	@Test
	public void testSetCategory() throws TechnicalException {
		
		Workbook visioDoc = new Workbook();
		
		DrawingCategory category = Mockito.mock(DrawingCategory.class);
		Mockito.when(category.getName()).thenReturn("Universal");
//		visioDoc.setDrawingCategory(category);
//		
//		Assert.assertNotNull(visioDoc.getDrawingCategory());
//		Assert.assertEquals(visioDoc.getDrawingCategory().getName(), "Universal");
	}

	/**
	 * 
	 */
	@Test
	public void testLoadFile() throws TechnicalException{
		String docTitle = "test2.xml";
		
		String fn = "c:\\tmp\\"+docTitle;
		Workbook visioDoc = new Workbook();
		visioDoc.setFileName(fn);
		visioDoc.load(new File(fn));

		Assert.assertEquals(docTitle, visioDoc.getTitle());

		Assert.assertNotNull(visioDoc.getCurrentWorksheet());
		Assert.assertNotNull(visioDoc.getCurrentPageUUID());
		
		Worksheet page = visioDoc.getCurrentWorksheet();
		Assert.assertNotNull(page.getSVGRoot());
	}
	
	@Test
	public void testStoreFile() throws TechnicalException {
		
		Workbook visioDoc = new Workbook();
		String fn = "c:\\tmp\\test2.xml";
		visioDoc.setFileName(fn);
		visioDoc.load(new File(fn));
		
		visioDoc.setFileName("c:\\tmp\\store1.xml");
		visioDoc.store();
		
		// TODO: Ergebnis vergleichen?
	}
}
