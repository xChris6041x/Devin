package io.xchris6041x.devin.commands;

import java.lang.reflect.InvocationTargetException;
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
	
	public Command getCommandAnnotation() {
		return method.getAnnotation(Command.class);
	}
	
	public int size() {
		return method.getParameterCount();
	}
	
	/**
	 * Execute the CommandMethod.
	 * @param sender - The CommandSender who executed the command.
	 * @param rawArgs - The arguments being passed into the method, in string form.
	 * @return whatever the method returns.
	 * @throws DevinException
	 */
	public boolean invoke(CommandSender sender, String[] rawArgs) throws DevinException {
		if(rawArgs.length < size()) throw new IllegalArgumentException("Invalid args. Not enough string arguments.");
		if(sender.getClass() != method.getParameterTypes()[0]) throw new IllegalArgumentException("Invalid CommandSender, must be a " + method.getParameterTypes()[0].getName());
		
		// Build argument array.
		Object[] args = new Object[size()];
		for(int i = 0; i < args.length; i++) {
			args[i] = ObjectParsing.parseObject(method.getParameterTypes()[i], rawArgs[i]);
		}
		
		
		try {
			return (boolean) method.invoke(commandable, args);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new DevinException("Problem invoking method.", e);
		}
	}
	
	/**
	 * Build the CommandMethod.
	 * @param commandable
	 * @param method
	 * @return
	 * @throws DevinException
	 */
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
