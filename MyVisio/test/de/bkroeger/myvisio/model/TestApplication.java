package de.bkroeger.myvisio.model;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import de.bkroeger.myvisio.model.Workbook;
import de.bkroeger.myvisio.model.Worksheet;

public class TestApplication {

	@Test
	public void testConstructor1(){
		
		Workbook visioDoc = Mockito.mock(Workbook.class);
		Worksheet worksheet = Worksheet.createDefaultWorksheet(visioDoc);
		Assert.assertNotNull(worksheet.getUuid());
		Assert.assertNotNull(worksheet.getPageNo());
		Assert.assertNotNull(worksheet.getDocument());
	}
}
