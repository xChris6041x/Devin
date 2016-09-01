package io.xchris6041x.devin.commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;

import io.xchris6041x.devin.AnsiColor;
import io.xchris6041x.devin.DevinException;
import io.xchris6041x.devin.MessageSender;
import io.xchris6041x.devin.commands.CommandResult.Status;

class CommandMethod {

	private Commandable commandable;
	private Method method;
	
	private String[] permissions;
	private String usage;
	
	private int optionalOffset = -1;
	private Object[] defaults = { };
	private boolean endless = false;
	
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
	
	public int minSize() {
		return (optionalOffset < 0) ? method.getParameterCount() - 1: optionalOffset;
	}
	public int maxSize() {
		return (endless) ? Integer.MAX_VALUE : method.getParameterCount() - 1;
	}
	
	/**
	 * Execute the CommandMethod.
	 * @param sender - The CommandSender who executed the command.
	 * @param rawArgs - The arguments being passed into the method, in string form.
	 * @return whatever the method returns.
	 * @throws DevinException
	 */
	public void invoke(CommandSender sender, String[] rawArgs, MessageSender msgSender) throws DevinException {
		// Check whether this is being validly invoked.
		if(!method.getParameterTypes()[0].isInstance(sender)){
			msgSender.error(sender, "Only " + method.getParameterTypes()[0].getSimpleName() + " can use this command.");
			return;
		}
		
		// Check permissions
		for(String perm : permissions) {
			if(!sender.hasPermission(perm)) {
				msgSender.error(sender, "You do not permission to use this command.");
				return;
			}
		}
		
		if(rawArgs.length < minSize()){
			msgSender.error(sender, usage);
			return;
		}
		
		// Build argument array.
		ArgumentStream argStream = new ArgumentStream(rawArgs);
		Object[] args = new Object[method.getParameterCount()];
		args[0] = sender;
		
		for(int i = 1; i < args.length; i++) {
			if(argStream.hasNext()) {
				// If there are arguments, then continue parsing them into objects.
				try {
					args[i] = ObjectParsing.parseObject(method.getParameterTypes()[i], argStream);
				}
				catch(DevinException | IllegalArgumentException e) {
					if(e instanceof NumberFormatException) {
						msgSender.error("Invalid number: " + e.getMessage());
					}
					else{
						msgSender.error(sender, e.getMessage());
					}
					msgSender.error(sender, usage);
					return;
				}
			}
			else {
				// Use optional's default.
				args[i] = defaults[i - optionalOffset - 1];
			}
		}
		
		
		try {
			// Execute command.
			CommandResult result = (CommandResult) method.invoke(commandable, args);
			
			// Process result.
			if(result.getMessage() != null) {
				if(result.usePrefix()) {
					switch (result.getStatus()) {
					case SUCCESS:
						msgSender.info(sender, result.getMessage());
						break;
						
					case FAILED:
					case USAGE:
						msgSender.error(sender, result.getMessage());
						break;
						
					default:
						msgSender.send(sender, result.getMessage());
					}
				}
				else {
					msgSender.send(sender, result.getMessage());
				}
			}
			if(result.getStatus() == Status.USAGE) {
				msgSender.error(sender, usage);
			}
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
		// Check if method is public.
		if(m.getModifiers() != Modifier.PUBLIC) throw new DevinException("Method must be public.");
		
		// Get @Command
		Command cmd = m.getAnnotation(Command.class);
		if(cmd == null) throw new DevinException("Missing @Command annotation.");
		
		// Check if method is valid.
		if(m.getReturnType() != CommandResult.class) throw new DevinException("Must have CommandResult as the return type.");
		if(m.getParameterCount() == 0) throw new DevinException("Must have one parameter.");
		if(!CommandSender.class.isAssignableFrom(m.getParameterTypes()[0])) throw new DevinException("First parameter must be a CommandSender or a subclass of it.");
		
		// Start building USAGE message.
		String usage = "/" + cmd.struct();
		int unknownParamCount = 0;
		
		boolean expectedEnd = false;
		int optionalOffset = -1;
		List<Object> defaults = new ArrayList<Object>();
		
		// Loop through all parameters to create optionals and check for validity.
		for(int i = 1; i < m.getParameters().length; i++) {
			if(expectedEnd) throw new DevinException("Cannot have more parameters after an array or ArgumentStream.");
			
			Parameter param = m.getParameters()[i];
			
			// Check if parameter type can be parsed.
			Class<?> paramType = param.getType();
			if(!ObjectParsing.parserExistsFor(m.getParameters()[i].getType())) throw new DevinException("No parser extists for " + paramType.getCanonicalName() + ".");
			if(paramType.isArray() || paramType == ArgumentStream.class) {
				expectedEnd = true;
			}
			
			// Build optional.
			OptionalArg optionalArg = param.getAnnotation(OptionalArg.class);
			String paramUsage = "<name>";
			if(optionalArg == null) {
				if(optionalOffset > -1) throw new DevinException("Cannot have a required parameter after and optional parameter.");
			}
			else {
				if(optionalOffset == -1) {
					optionalOffset = i - 1;
				}
				
				if(optionalArg.value().length == 0) {
					defaults.add(null);
				}
				else {
					defaults.add(ObjectParsing.parseObject(paramType, new ArgumentStream(optionalArg.value())));
				}
				
				paramUsage = "[name]";
			}
			
			// Add parameter name to usage statement.
			String paramName;
			if(i - 1 < cmd.params().length) {
				paramName = cmd.params()[i - 1];
			}
			else{
				paramName = "arg" + unknownParamCount;
				unknownParamCount++;
			}
			usage += " " + paramUsage.replace("name", paramName);
		}
		
		if(unknownParamCount > 0) {
			System.out.println("\t\t" + AnsiColor.YELLOW + "WARNING: " + unknownParamCount + " UNKNOWN PARAMETER" + (unknownParamCount > 1 ? "S" : "") + AnsiColor.RESET);
		}
		
		// Build CommandMethod.
		CommandMethod cm = new CommandMethod();
		cm.commandable = commandable;
		cm.method = m;
		
		cm.permissions = Arrays.copyOf(cmd.perms(), cmd.perms().length);
		cm.usage = usage;
		cm.endless = expectedEnd;
		
		if(optionalOffset > -1) {
			cm.optionalOffset = optionalOffset;
			cm.defaults = defaults.toArray(new Object[0]);
		}
		
		return cm;
	}
	
}
