package dev.sb.examples;

import static java.lang.System.out;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import dev.sb.SimpleLogger.SimpleLogger;

public class FileLogging {
	/* set up the logger and then call the functions */
	private SimpleLogger simpleLogger = new SimpleLogger(); 
	private Logger logger = null;
	
	public void executeLoggerFunctions() {
		try {
			simpleLogger.setupLogger(null, "logs", "log_n", null, false, true, Level.FINEST);
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
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException interruptedException) {
				interruptedException.printStackTrace();
			}
			count++;
			logger.warning("Updated counter");
			if (count == 3) {
				logger.warning("Breaking out of the loop");
				break;
			}
		}
	}
}
