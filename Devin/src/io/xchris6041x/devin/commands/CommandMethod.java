package io.xchris6041x.devin.commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.bukkit.command.CommandSender;

import io.xchris6041x.devin.DevinException;
import io.xchris6041x.devin.MessageSender;

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
	public boolean invoke(CommandSender sender, String[] rawArgs, MessageSender msgSender) throws DevinException {
		if(!method.getParameterTypes()[0].isInstance(sender)){
			msgSender.error(sender, "You cannot use this command.");
			return true;
		}
		if(rawArgs.length < size() - 1){
			msgSender.error(sender, "Invalid number of arguments.");
			return true;
		}
		
		// Build argument array.
		Object[] args = new Object[size()];
		args[0] = sender;
		
		for(int i = 1; i < args.length; i++) {
			args[i] = ObjectParsing.parseObject(method.getParameterTypes()[i], rawArgs[i - 1]);
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
	public static CommandMethod build(Commandable commandable, Method m) throws DevinException {
		// Check if method is valid.
		if(m.getModifiers() != Modifier.PUBLIC) throw new DevinException("Invalid command: Method must be public.");
		if(m.getAnnotation(Command.class) == null) throw new DevinException("Invalid command: Must have @Command annotation on method.");
		
		if(m.getReturnType() != Boolean.TYPE) throw new DevinException("Invalid command: Must have a boolean return type.");
		if(m.getParameterCount() == 0) throw new DevinException("Invalid command: Must have at least one parameter.");
		if(!CommandSender.class.isAssignableFrom(m.getParameterTypes()[0])) throw new DevinException("Invalid command: First parameter must be a subclass CommandSender.");
		
		for(int i = 1; i < m.getParameters().length; i++) {
			Class<?> paramType = m.getParameters()[i].getType();
			if(!ObjectParsing.parserExistsFor(m.getParameters()[i].getType())) throw new DevinException("Invalid command: Object parser for " + paramType.getCanonicalName() + ".");
		}
		
		// Build CommandMethod.
		CommandMethod cm = new CommandMethod();
		cm.commandable = commandable;
		cm.method = m;
		
		return cm;
	}
	
}
