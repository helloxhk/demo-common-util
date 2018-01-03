package com.taiji.common.log;

import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.helpers.PatternParser;
import org.apache.log4j.spi.LoggingEvent;

public class MinePatternParser extends PatternParser {

	private static final ThreadLocal<String> THREAD_LOG_IDENTIFY = new ThreadLocal<>();
	
	public MinePatternParser(String pattern) {
		super(pattern);
	}

	@Override
	protected void finalizeConverter(char c) {
		if(c == '#'){
			addConverter(new MinePatternConverter());
			currentLiteral.setLength(0);
		}else{
			super.finalizeConverter(c);
		}
	}
	
	private class MinePatternConverter extends PatternConverter{

		@Override
		protected String convert(LoggingEvent event) {
			return THREAD_LOG_IDENTIFY.get();
		}
	}
	
	public static void setTHREAD_LOG_IDENTIFY(String thread_log_identify){
		THREAD_LOG_IDENTIFY.set(thread_log_identify);
	}
}
