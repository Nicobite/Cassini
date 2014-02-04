/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sylvain
 */

// handle exceptions and errors according to their severity (fatal or not)
// also triggers log and display on GUI

public class GlobalErrorHandler {
	private static Logger logger;
	
	public static void initLoggers () {
		logger = Logger.getLogger("cassini");
		
		logger.setLevel(Level.WARNING);
		
		Logger rootLogger = Logger.getLogger("");
		rootLogger.setLevel(Level.WARNING);
		
		for (Handler h : rootLogger.getHandlers()) {
			h.setLevel(Level.WARNING);
			if (h instanceof ConsoleHandler)
				h.setFormatter(new CustomLogFormatter());
		}
	}
	
	// TODO check the coherency of the exception handling policy
	// TODO apply the exception handling policy accross the whole project
	public static void notifyNonCriticalException(Throwable t) {
		logger.log(Level.WARNING, "Caught a non-critical exception", t);
		if (MainController.getGui() != null)
			MainController.getGui().displayError(t, "Une exception non critique est survenue");
	}
	
	public static void notifyUnexpectedException(Throwable t) {
		logger.log(Level.SEVERE, "Caught a unexpected exception", t);
		if (MainController.getGui() != null)
			MainController.getGui().displayError(t, "Une exception inattendue est survenue");
	}
	
	public static void notifyCriticalException(Throwable t) {
		// TODO [someday] exit the program
		logger.log(Level.SEVERE, "Caught a critical exception", t);
		if (MainController.getGui() != null)
			MainController.getGui().displayError(t, "Une exception inattendue est survenue");
	}
}
