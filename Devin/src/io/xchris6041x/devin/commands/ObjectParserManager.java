package io.xchris6041x.devin.commands;

import java.util.Map;

public class ObjectParserManager {

	private Map<Class<?>, ObjectParser> parsers;
	
	public ObjectParserManager() {
	}
	
	/**
	 * Add the {@code objParser} parser for the type {@code type}. 
	 * @param type
	 * @param objParser
	 */
	public void registerParser(Class<?> type, ObjectParser objParser) {
		parsers.put(type, objParser);
	}
	
	/**
	 * Parse the string {@code str} to a object with the type {@code type}
	 * @param type
	 * @param str
	 * @return
	 */
	public Object parseObject(Class<?> type, String str) {
		ObjectParser objParser = parsers.get(type);
		if(objParser == null) throw new IllegalArgumentException("There is no registered object parser for " + type.getCanonicalName() + ".");
		
		return objParser.parseObject(str);
	}
	
}
