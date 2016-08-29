package io.xchris6041x.devin.commands;

import java.lang.reflect.Parameter;
import java.lang.reflect.Method;

import org.bukkit.command.CommandSender;

class CommandMethod {

	public static final Class<?>[] VALID_TYPES = { String.class };
	
	
	private Commandable commandable;
	private Method method;
	
	private CommandMethod() {
	}
	
	public Commandable getCommandable() {
		return commandable;
	}
	public Method getMethod() {
		return method;
	}
	
	public int size() {
		return method.getParameterCount();
	}
	
	
	
	public static CommandMethod build(Commandable commandable, Method method) {
		if(!isValidCommandMethod(method)) throw new IllegalArgumentException("Invalid command method.");
		
		CommandMethod cm = new CommandMethod();
		cm.commandable = commandable;
		cm.method = method;
		
		return cm;
	}
	
	/**
	 * @param m
	 * @return Is a method a valid command method.
	 */
	public static boolean isValidCommandMethod(Method m) {
		return m.getReturnType() == Boolean.TYPE && 
			   m.getParameterCount() > 0 && 
			   CommandSender.class.isAssignableFrom(m.getParameterTypes()[0]) &&
			   m.getAnnotation(Command.class) != null;
	}
	
	/**
	 * @param param
	 * @return is a parameter a valid command parameter.
	 */
	public static boolean isValidType(Class<?> type) {
		for(Class<?> validType : CommandMethod.VALID_TYPES) {
			if(validType == type) return true;
		}
		return false;
	}
	
}
