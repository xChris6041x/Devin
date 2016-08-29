package io.xchris6041x.devin.commands;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.bukkit.command.CommandSender;

import io.xchris6041x.devin.DevinException;

class CommandMethod {

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
	
	public static CommandMethod build(Commandable commandable, Method method) throws DevinException {
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
	public static boolean isValidCommandMethod(Method m) throws DevinException {
		if(m.getModifiers() != Modifier.PUBLIC || m.getAnnotation(Command.class) == null) return false;
		
		if(m.getReturnType() != Boolean.TYPE) throw new DevinException("Invalid command: Must have a boolean return type.");
		if(m.getParameterCount() == 0) throw new DevinException("Invalid command: Must have at least one parameter.");
		if(!CommandSender.class.isAssignableFrom(m.getParameterTypes()[0])) throw new DevinException("Invalid command: First parameter must be a subclass CommandSender.");
		
		for(int i = 1; i < m.getParameters().length; i++) {
			Class<?> paramType = m.getParameters()[i].getType();
			if(!ObjectParsing.parserExistsFor(m.getParameters()[i].getType())) throw new DevinException("Invalid command: Object parser for " + paramType.getCanonicalName() + ".");
		}
		
		return true;
	}
	
}
