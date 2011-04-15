package com.ceptrader.util;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Logger {
	// private static org.slf4j.Logger sl = new Log4jLoggerFactory()
	// .getLogger("DroolsTrader");
	
	public static void log(final Object t) {
		// Logger.sl.info(t.toString());
		System.out.println(ToStringBuilder.reflectionToString(t));
	}
	
	public static void log(final String t) {
		// Logger.sl.info(t.toString());
		System.out.println(t);
	}
	
	public static void log(final Throwable t) {
		// Logger.sl.info(t.toString());
		System.out.println(ToStringBuilder.reflectionToString(t));
	}
	
	public static org.slf4j.Logger getLogger() {
		return null; // Logger.sl;
	}
}
