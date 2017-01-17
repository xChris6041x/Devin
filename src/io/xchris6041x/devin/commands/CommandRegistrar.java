package io.xchris6041x.devin.commands;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import io.xchris6041x.devin.AnsiColor;
import io.xchris6041x.devin.Devin;
import io.xchris6041x.devin.DevinException;
import io.xchris6041x.devin.MessageSender;

/**
 * The main class responsible for registering command methods and auto-injecting fields.
 * @author Christopher Bishop
 */
public class CommandRegistrar extends CommandHandlerContainer {
	
	private JavaPlugin plugin;
	private List<Object> injectionObjects;
	
	public CommandRegistrar(JavaPlugin plugin, MessageSender msgSender){
		super(null, msgSender);
		this.plugin = plugin;
		this.injectionObjects = new ArrayList<Object>();
	}
	
	/**
	 * Inject this object into commands that will be registered.
	 * Note: This must be done before calling registerCommands.
	 * 
	 * @param obj
	 */
	public void inject(Object obj) {
		for(Object injectObject : injectionObjects) {
			if(injectObject.getClass().equals(obj.getClass())) throw new IllegalArgumentException("Cannot inject two objects of the same type.");
		}
		
		injectionObjects.add(obj);
	}
	
	/**
	 * Register all command methods so they can be ran when the command is used.
	 * @param commandable - The class to get all the command methods from.
	 * @param msgSender - A MessageSender for these commands only.
	 */
	public void registerCommands(Commandable commandable, MessageSender msgSender) {
		Devin.debug("Registering " + commandable.getClass().getCanonicalName() + ": ");
		Devin.debug("---------------------------------------------------------------");
		Devin.debug("Looking for @DevinInject...");
		for(Field field : commandable.getClass().getFields()) {
			DevinInject inject = field.getAnnotation(DevinInject.class);
			if(inject == null) continue;
			
			Devin.debug("\tAttempting to auto-inject " + AnsiColor.DARK_CYAN + field.getName() + AnsiColor.RESET + ":");
			
			if(field.getType().isAssignableFrom(getMessageSender().getClass())) {
				try {
					field.set(commandable, msgSender);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			else if(field.getType().isAssignableFrom(plugin.getClass())) {
				try {
					field.set(commandable, plugin);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			else{
				boolean injected = false;
				for(Object injectObject : injectionObjects) {
					if(field.getType().isAssignableFrom(injectObject.getClass())) {
						try {
							field.set(commandable, injectObject);
							
							injected = true;
							break;
						} catch (IllegalArgumentException | IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				}
				
				if(!injected) {
					Devin.debug(AnsiColor.RED + "\t\tFAILED: Cannot auto-inject type " + field.getType().getCanonicalName() + AnsiColor.RESET);
					continue;
				}
			}
			
			Devin.debug(AnsiColor.GREEN + "\t\tSUCCESS" + AnsiColor.RESET);
		}
		
		Devin.debug(" ");
		Devin.debug("Looking for @Command...");
		for(Method method : commandable.getClass().getMethods()) {
			Command cmd = method.getAnnotation(Command.class);
			if(cmd == null) continue;
			Devin.debug("\tAttempting to register " + AnsiColor.CYAN + method.getName() + AnsiColor.RESET + ":");
			
			try {
				CommandMethod commandMethod = CommandMethod.build(commandable, method);
				Command command = commandMethod.getCommandAnnotation();
				String[] struct = command.struct().split(" ");
				
				CommandHandler handler = getHandler(struct);
				handler.setMessageSender(msgSender);
				handler.setAliases(Arrays.copyOf(command.aliases(), command.aliases().length));
				handler.setMethod(commandMethod);
				
				// Register permissions
				for(String perm : cmd.perms()) {
					Bukkit.getPluginManager().addPermission(new Permission(perm));
					Devin.debug("\t\tRegistered permission " + AnsiColor.CYAN + perm + AnsiColor.RESET + " /w Spigot");
				}
				// Register Command
				CommandHandler root = registerCommand(handler);
				if(root != null) Devin.debug("\t\tRegistered command " + AnsiColor.CYAN + root.getName() + AnsiColor.RESET + " /w Spigot");
				
				Devin.debug(AnsiColor.GREEN + "\t\tSUCCESS" + AnsiColor.RESET);
			}
			catch(DevinException e) {
				Devin.debug(AnsiColor.RED + "\t\tFAILED: " + e.getMessage() + AnsiColor.RESET);
			}
		}
		
		Devin.debug(" ");
	}
	
	/**
	 * Register all command methods so they can be ran when the command is used.
	 * @param commandable - The class to get all the command methods from.
	 */
	public void registerCommands(Commandable commandable) {
		registerCommands(commandable, getMessageSender());
	}
	
	private CommandHandler registerCommand(CommandHandler handler) throws DevinException {
		// Find the root handler.
		CommandHandler root = handler;
		boolean isRoot = true;
		boolean wasRegistered = false;
		
		while(root.getParent() != this) {
			root = (CommandHandler) root.getParent();
			if(isRoot) isRoot = false;
		}
		
		// Double check for registered command.
		PluginCommand cmd = plugin.getCommand(root.getName());
		if(cmd == null) {
			try {
				// Construct plugin command.
				Constructor<PluginCommand> con = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
				con.setAccessible(true);
				
				cmd = con.newInstance(root.getName(), plugin);
				
				// Register Command
				Field commandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
				commandMap.setAccessible(true);
				
				((CommandMap) commandMap.get(Bukkit.getServer())).register(handler.getName(), cmd);
				
				// Cleanup
				con.setAccessible(false);
				commandMap.setAccessible(false);
				
				wasRegistered = true;
			} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
				throw new DevinException(e.getClass().getSimpleName() + " - " + e.getMessage(), e);
			}
		}
		else{
			if(cmd.getPlugin() != plugin) throw new DevinException("Your plugin does not own \"" + root.getName() + "\"");
		}
		
		cmd.setExecutor(root);
		if(isRoot) {
			cmd.setAliases(Arrays.asList(root.getAliases()));
			cmd.setUsage(handler.getMethod().getUsage());
			cmd.setExecutor(root);
		}
		return (wasRegistered) ? root : null;
	}
	private CommandHandler getHandler(String[] structure) {
		return getHandler(this, structure, 0);
	}
	private CommandHandler getHandler(CommandHandlerContainer container, String[] structure, int offset) {
		if(offset == structure.length - 1) {
			return container.getHandler(structure[offset], true);
		}
		else{
			return getHandler(container.getHandler(structure[offset], true), structure, offset + 1);
		}
	}
	
}
