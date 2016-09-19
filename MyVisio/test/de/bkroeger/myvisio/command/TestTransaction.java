package de.bkroeger.myvisio.command;

import org.junit.Test;
import org.mockito.Mockito;

import de.bkroeger.myvisio.controller.commands.DocumentArgument;
import de.bkroeger.myvisio.controller.commands.Transaction;
import de.bkroeger.myvisio.model.Workbook;
import de.bkroeger.myvisio.utility.TechnicalException;

public class TestTransaction {

	@Test
	public void testConstructor1() throws TechnicalException{
		
		Workbook document = Mockito.mock(Workbook.class);
		DocumentArgument docArg = new DocumentArgument(document);
		Transaction transact = new Transaction("FileSaveAs", docArg);
		transact.execute();
	}

}
