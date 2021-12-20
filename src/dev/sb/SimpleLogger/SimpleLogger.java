package dev.sb.SimpleLogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/*
 * TODO:
 * 1. Prepare get and set functions for _format - [done]
 * 2. Prepare optional functions for overload and adding custom handlers - based on the format [done]
 */

public class SimpleLogger {
	private String _fpath = null;						// Log file path
	private static String _format = null;				// logging format
	private ConsoleHandler consoleHandler = null;
	private FileHandler fileHandler = null;
	private static Logger _logger = null;				// Logger instance, sometimes called rootLogger
	
	public SimpleLogger() {
		/* default constructor - code will be added later */
	}

	/**
	 * @brief function to setup the SimpleLogger
	 * 
	 * Provide the logging format if the default format is not acceptable
	 * @param loggingFormat String
	 * @param logDir String
	 * @param filename String
	 * @param loggerContextName String
	 * @param logToConsole Boolean
	 * @param logToFile Boolean
	 * 
	 * @throws IOException
	 */
	public void setupLogger(String loggingFormat, String logDir, String filename, String loggerContextName, 
			boolean logToConsole, boolean logToFile, Level loggingLevel) throws IOException {
		if (loggingFormat != null && !loggingFormat.equalsIgnoreCase(Constants.DEFAULT_FORMAT) && 
				this.consoleHandler == null && this.fileHandler == null) {
			throw new NullPointerException(
					"Please setup console handler and filehandler respectively before setting up logger");
		}
		if (logDir == null || logDir.isEmpty()) {
			throw new NullPointerException("Log directory path has not been provided");
		}
		if (filename == null || filename.isEmpty() || filename.isBlank()) {
			throw new NullPointerException("Log filename has not been provided");
		}

		Path path = Paths.get(logDir);
		if (Files.exists(path)) {
			new File(logDir).mkdirs();
		}
		this._fpath = logDir + System.getProperty("file.separator") + filename;
		_format = loggingFormat == null ? Constants.DEFAULT_FORMAT : loggingFormat;
		_logger = Logger.getLogger(loggerContextName == null ? Logger.GLOBAL_LOGGER_NAME : loggerContextName);
		
		/* console and file handler setup */
		if (logToConsole) {
			this.consoleHandler = new ConsoleHandler();
			this.consoleHandler.setLevel(loggingLevel == null ? Level.INFO : loggingLevel);
			if (_format.equalsIgnoreCase(Constants.DEFAULT_FORMAT)) {
				this.consoleHandler.setFormatter(new SimpleFormatter() {
					@Override
					public synchronized String format(LogRecord logRecord) {
						return String.format(_format,  new Date(logRecord.getMillis()), 
								logRecord.getLevel().getLocalizedName(), logRecord.getMessage());
					}
				});
			}
			_logger.addHandler(consoleHandler);
		}
		if (logToFile) {
			this.fileHandler = new FileHandler(this._fpath, true);
			this.fileHandler.setLevel(loggingLevel == null ? Level.INFO : loggingLevel);
			if (_format.equalsIgnoreCase(Constants.DEFAULT_FORMAT)) {
				this.fileHandler.setFormatter(new SimpleFormatter() {
					@Override
					public synchronized String format(LogRecord logRecord) {
						return String.format(_format,  new Date(logRecord.getMillis()), 
								logRecord.getLevel().getLocalizedName(), logRecord.getMessage());
					}
				});
			}
			_logger.addHandler(fileHandler);
		}
	}
	
	/**
	 * @brief function to setup the Console Handler for custom formatting
	 * 
	 * @note It is recommended to call this function before calling setupLogger
	 * in case a custom format is used
	 */
	public void setupConsoleHandler(SimpleFormatter simpleFormatter) {
		this.consoleHandler.setFormatter(simpleFormatter);
		_logger.addHandler(consoleHandler);
	}
	
	/**
	 * @brief function to setup the File Handler for custom formatting
	 * 
	 * @note It is recommended to call this function before calling setupLogger
	 * in case a custom format is used
	 */
	public void setupFileHandler(SimpleFormatter simpleFormatter) {
		this.fileHandler.setFormatter(simpleFormatter);
		_logger.addHandler(this.fileHandler);
	}
	
	/**
	 * @brief function to set the logging format
	 */
	public void setLoggingFormat(String loggingFormat) {
		if (loggingFormat != null && !loggingFormat.isBlank() && !loggingFormat.isEmpty())
			_format = loggingFormat;
	}

	/**
	 * @brief function to get the logging format
	 */
	public static String getLoggingFormat() {
		return _format;
	}
	
	/**
	 * @brief function to get the resolved logging file path
	 */
	public String getLogFilepath() {
		return this._fpath;
	}
	
	/**
	 * @brief function to get the logger instance created
	 */
	public static Logger getLogger() {
		return _logger;
	}
}
