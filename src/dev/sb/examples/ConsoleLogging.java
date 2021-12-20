package dev.sb.examples;

import static java.lang.System.out;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import dev.sb.SimpleLogger.SimpleLogger;

/**
 * @file ConsoleLogging.java
 * @author sb
 * @brief Example show-casing simple console logging capacity
 */

public class ConsoleLogging {
	/* set up the logger and then call the functions */
	private SimpleLogger simpleLogger = new SimpleLogger(); 
	private Logger logger = null;
	
	public void executeLoggerFunctions() {
		try {
			simpleLogger.setupLogger(null, "logs", "log_n", null, true, true, Level.FINEST);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		out.println(SimpleLogger.getLoggingFormat());
		out.println(simpleLogger.getLogFilepath());
		
		logger = SimpleLogger.getLogger();
		logger.warning("Testing a write");

		int count = 0;
		while (true) {
			logger.info("Testing a info");
			count++;
			logger.warning("Updated counter");
			if (count == 3) {
				logger.warning("Breaking out of the loop");
				break;
			}
		}
	}
}
