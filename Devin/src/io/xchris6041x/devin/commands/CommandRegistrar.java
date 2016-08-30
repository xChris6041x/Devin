package io.xchris6041x.devin.commands;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.command.PluginCommand;
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
	
	public void registerCommands(Commandable commandable) {
		System.out.println("Registering " + commandable.getClass().getCanonicalName() + ": ");
		System.out.println("---------------------------------------------------------------");
		System.out.println("Looking for @DevinInject...");
		for(Field field : commandable.getClass().getFields()) {
			DevinInject inject = field.getAnnotation(DevinInject.class);
			if(inject == null) continue;
			
			System.out.println("\tAttempting to auto-inject " + AnsiColor.CYAN + field.getName() + AnsiColor.RESET + ":");
			
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
			if(method.getAnnotation(Command.class) == null) continue;
			
			System.out.print("\tAttempting to register " + AnsiColor.CYAN + method.getName() + AnsiColor.RESET + ":");
			try {
				CommandMethod commandMethod = CommandMethod.build(commandable, method);
				Command command = commandMethod.getCommandAnnotation();
				String[] struct = command.struct().split(" ");
				
				// Register with bukkit, the first part of the structure (if not registered).
				PluginCommand cmd = plugin.getCommand(struct[0]);
				if(cmd != null) {
					cmd.setExecutor(getHandler(struct[0], true));
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
