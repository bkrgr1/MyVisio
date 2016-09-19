package de.bkroeger.myvisio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import de.bkroeger.myvisio.controller.ApplicationController;
import de.bkroeger.myvisio.model.ApplicationModel;

/**
 * @author bk
 */
public class MyVisio {
	
	private static final Logger logger = Logger.getLogger(MyVisio.class.getName());

	private static final String HYPHEN = "-";

	/**
	 * Startet die Anwendung durch Initialisierung des ApplicationModel und des
	 * ApplicationController.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		logger.info(String.format("Parameters: %s %s %s", 
				args.length > 0 ? args[0] : "", 
				args.length > 1 ? args[1] : "",
				args.length > 2 ? args[2] : ""));
		
		try {
			List<String> posParams = new ArrayList<String>();
			Map<String, String> keyParams = new HashMap<String, String>();
			for (int i=0; i<args.length; i++) {
				String arg = args[i];
				if (arg.startsWith(HYPHEN)) {
					// Keyword-Parameter auswerten
					String key = (arg.length() >= 2) ? arg.substring(0,2) : null;
					String val = (arg.length() >= 3) ? arg.substring(2) : null;
					if (key != null && val != null) {
						keyParams.put(key, val);
					}
				} else {
					// positional Parameter auswerten
					posParams.add(arg);
				}
			}
	
			ApplicationModel applicationModel = ApplicationModel.getApplicationModel(keyParams, posParams);
			ApplicationController applicationController = new ApplicationController(applicationModel);
			applicationController.showApplicationView();
			
			// alle verbleibenden Fehler auffangen
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Unexpected error!\n"+e.getMessage());
			System.exit(ApplicationModel.EXITCODE_UNCAUGHT_EXCEPTION);
		}
	}

}
