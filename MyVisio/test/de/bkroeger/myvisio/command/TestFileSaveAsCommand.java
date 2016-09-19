package de.bkroeger.myvisio.command;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import de.bkroeger.myvisio.controller.commands.DocumentArgument;
import de.bkroeger.myvisio.controller.commands.Transaction;
import de.bkroeger.myvisio.model.Workbook;
import de.bkroeger.myvisio.utility.TechnicalException;

public class TestFileSaveAsCommand {
	
	@Before
	public void setup() {
		
	}
	
	@After
	public void teardown() {
		
	}

	/**
	 * Erzeugt ein VisioDocument und speichert es in der angegebenen Datei.
	 * 
	 * @throws TechnicalException
	 */
	@Test
	public void testExecute() throws TechnicalException{
		
		// VisioDocument erzeugen und Dateinamen vorgeben
		Workbook document = new Workbook();
		document.setFileName("c:\\tmp\\testfile1.mvd");
		
		// DocumentArgument mit dem VisioDocument erstellen
		DocumentArgument docArg = new DocumentArgument(document);
		
		// Transaktion erstellen
		Transaction transact = new Transaction("FileSaveAs", docArg);
		
		// Transaktion ausführen
		transact.execute();
	}
}
