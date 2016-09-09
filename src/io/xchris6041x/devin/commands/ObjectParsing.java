package io.xchris6041x.devin.commands;

import java.util.HashMap;
import java.util.Map;

import io.xchris6041x.devin.DevinException;

/**
 * A class with all static methods for converting strings into objects.
 * @author Christopher Bishop
 */
public final class ObjectParsing {

	private static Map<Class<?>, ObjectParser> parsers = new HashMap<Class<?>, ObjectParser>();
	
	private ObjectParsing(){
	}
	
	/**
	 * Add the {@code objParser} parser for the type {@code type}. 
	 * @param type
	 * @param objParser
	 */
	public static void registerParser(Class<?> type, ObjectParser objParser) {
		parsers.put(type, objParser);
	}
	
	/**
	 * Parse the string {@code str} to a object with the type {@code type}
	 * @param type
	 * @param str
	 * @return
	 */
	public static Object parseObject(Class<?> type, ArgumentStream args) throws DevinException {
		ObjectParser objParser = parsers.get(type);
		if(objParser == null) throw new DevinException("There is no registered object parser for " + type.getCanonicalName() + ".");
		
		return objParser.parseObject(args);
	}
	
	/**
	 * @param type
	 * @return whether a parser exists for {@code type}
	 */
	public static boolean parserExistsFor(Class<?> type) {
		return parsers.get(type) != null;
	}
	
}