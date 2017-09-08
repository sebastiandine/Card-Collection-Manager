package com.sebastiandine.cardcollectionmanager.logging;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

import com.sebastiandine.cardcollectionmanager.factories.PropertiesFactory;

/**
 * This class encapsulates the log4j engine an provides an easy to use log meachnism
 * throughout the whole application.
 * 
 * @author Sebastian Dine
 *
 */
public class Logger {
	
	private static org.apache.logging.log4j.Logger logger;
	
	static{
		logger = factoryLogger();
	}
	
	public static void info(String text){
		logger.info(text);
	}
	public static void error(String text){
		logger.error(text);
	}
	public static void warn(String text){
		logger.warn(text);
	}
	public static void debug(String text){
		logger.debug(text);
	}
	public static void fatal(String text){
		logger.fatal(text);
	}
	
	
	/**
	 * This method configures and initiates the internal {@link  org.apache.logging.log4j.Logger } object {@link #logger}.
	 */
	private static org.apache.logging.log4j.Logger factoryLogger(){
		/* Create Configuration */
		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config = ctx.getConfiguration();
		
		PatternLayout layout = PatternLayout.newBuilder()
				.withConfiguration(config)
				.withPattern("%d{YYYY:MM:dd HH:mm:ss.SS} - %level : %msg%n")
				.build();
		
		Appender appender = FileAppender.newBuilder()
				.setConfiguration(config)
				.withName("myFileAppender")
				.withLayout(layout)
				.withFileName(PropertiesFactory.getLogfileUrl())
				.build();
		
		AppenderRef ref = AppenderRef.createAppenderRef("myFileAppender", null, null);
		AppenderRef[] refs = new AppenderRef[]{ref};
		
		/* Combine everything */
		LoggerConfig loggerConfig = LoggerConfig
				.createLogger(false, PropertiesFactory.getLogLevel(), "myLogger", "true", refs, null, config, null);
		loggerConfig.addAppender(appender, null, null);
		
		config.addLogger("myLogger", loggerConfig);
		
		/* Initiate Logger */
		return LogManager.getLogger("myLogger");
		
	}
	

}

