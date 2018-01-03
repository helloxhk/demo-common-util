package com.taiji.common.log;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.helpers.PatternParser;

public class MinePatternLayout extends PatternLayout {
	
	public MinePatternLayout() {
		this(DEFAULT_CONVERSION_PATTERN);
	}
	
	public MinePatternLayout(String pattern) {
		super(pattern);
	}

	@Override
	protected PatternParser createPatternParser(String pattern) {
		return new MinePatternParser(pattern == null ? DEFAULT_CONVERSION_PATTERN : pattern);
	}

	public static void main(String[] args) {
		//MinePatternParser.setTHREAD_LOG_IDENTIFY("sfser23r2ef");
		
		Layout layout = new MinePatternLayout("[%#][%p] - %m%n");
		Logger logger = Logger.getLogger("some.cat");
		logger.addAppender(new ConsoleAppender(layout, ConsoleAppender.SYSTEM_OUT));
		logger.debug("Hello, log");
		logger.info("Hello again...");
		
	}
}
