package io.xchris6041x.devin.commands;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import io.xchris6041x.devin.AnsiColor;
import io.xchris6041x.devin.DevinException;
import io.xchris6041x.devin.MessageSender;

/**
 * The main class responsible for registering command methods and auto-injecting fields.
 * @author Christopher Bishop
 */
public class CommandRegistrar extends CommandHandlerContainer {
	
	private JavaPlugin plugin;
	
	public CommandRegistrar(JavaPlugin plugin, MessageSender msgSender){
		super(msgSender);
		this.plugin = plugin;
	}
	
	/**
	 * Register all command methods so they can be ran when the command is used.
	 * Note: The commands must be in the plugin.yml.
	 * 
	 * @param commandable - The class to get all the command methods from.
	 * @param registerPermissions - Whether to register permissions on the commands. 
	 */
	public void registerCommands(Commandable commandable, boolean registerPermissions) {
		System.out.println("Registering " + commandable.getClass().getCanonicalName() + ": ");
		System.out.println("---------------------------------------------------------------");
		System.out.println("Looking for @DevinInject...");
		for(Field field : commandable.getClass().getFields()) {
			DevinInject inject = field.getAnnotation(DevinInject.class);
			if(inject == null) continue;
			
			System.out.println("\tAttempting to auto-inject " + AnsiColor.DARK_CYAN + field.getName() + AnsiColor.RESET + ":");
			
			if(field.getType().isAssignableFrom(getMessageSender().getClass())) {
				try {
					field.set(commandable, getMessageSender());
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
				System.out.println(AnsiColor.RED + "\t\tFAILED: Cannot auto-inject type " + field.getType().getCanonicalName() + AnsiColor.RESET);
				continue;
			}
			
			System.out.println(AnsiColor.GREEN + "\t\tSUCCESS" + AnsiColor.RESET);
		}
		
		System.out.println(" ");
		System.out.println("Looking for @Command...");
		for(Method method : commandable.getClass().getMethods()) {
			Command cmd = method.getAnnotation(Command.class);
			if(cmd == null) continue;
			System.out.print("\tAttempting to register " + AnsiColor.DARK_CYAN + method.getName() + AnsiColor.RESET + ":");
			
			// Register permissions
			if(registerPermissions) {
				for(String perm : cmd.perms()) {
					Bukkit.getPluginManager().addPermission(new Permission(perm));
					System.out.println("\t\tRegistered permission " + AnsiColor.CYAN + perm + AnsiColor.RESET);
				}
			}
			
			try {
				CommandMethod commandMethod = CommandMethod.build(commandable, method);
				Command command = commandMethod.getCommandAnnotation();
				String[] struct = command.struct().split(" ");
				
				// Register with bukkit, the first part of the structure (if not registered).
				PluginCommand pcmd = plugin.getCommand(struct[0]);
				if(pcmd != null) {
					pcmd.setExecutor(getHandler(struct[0], true));
				}
				else{
					System.out.println(AnsiColor.RED + "\t\tFAILED: Missing PluginCommand" + AnsiColor.RESET);
					continue;
				}
				
				CommandHandler handler = getHandler(struct);
				handler.setMethod(commandMethod);
				
				System.out.println(AnsiColor.GREEN + "\t\tSUCCESS" + AnsiColor.RESET);
			}
			catch(DevinException e) {
				System.out.println(AnsiColor.RED + "\t\tFAILED: " + e.getMessage() + AnsiColor.RESET);
			}
		}
		
		System.out.println(" ");
	}
	
	/**
	 * Register all command methods so they can be ran when the command is used. This does not register permissions.
	 * Note: The commands must be in the plugin.yml.
	 * @param commandable
	 */
	public void registerCommands(Commandable commandable) {
		registerCommands(commandable, false);
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
