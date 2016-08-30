package io.xchris6041x.devin.commands;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import io.xchris6041x.devin.DevinException;
import io.xchris6041x.devin.MessageSender;

public class CommandRegistrar extends CommandHandlerContainer {
	
	private JavaPlugin plugin;
	
	public CommandRegistrar(JavaPlugin plugin, MessageSender msgSender){
		super(msgSender);
		this.plugin = plugin;
	}
	
	public void registerCommands(Commandable commandable) {
		System.out.println("Registering " + commandable.getClass().getCanonicalName() + ": ");
		System.out.println("---------------------------------------------------------------");
		System.out.println("Looking for @Inject...");
		for(Field field : commandable.getClass().getFields()) {
			Inject inject = field.getAnnotation(Inject.class);
			if(inject == null) continue;
			
			System.out.println("\tAttempting to auto-inject " + field.getName() + ":");
			
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
				System.out.println("\t\tCannot auto-inject type " + field.getType().getCanonicalName());
				continue;
			}
			
			System.out.println("\t\tSuccess");
		}
		
		System.out.println(" ");
		System.out.println("Looking for @Command...");
		for(Method method : commandable.getClass().getMethods()) {
			if(method.getAnnotation(Command.class) == null) continue;
			
			System.out.print("\tAttempting to register " + method.getName() + ":");
			try {
				CommandMethod commandMethod = CommandMethod.build(commandable, method);
				
				System.out.println("\t\tSuccessfully created CommandMethod.");
				Command command = commandMethod.getCommandAnnotation();
				String[] struct = command.struct().split(" ");
				
				// Register with bukkit, the first part of the structure (if not registered).
				PluginCommand cmd = plugin.getCommand(struct[0]);
				if(cmd != null) {
					System.out.println("\t\tFound PluginCommand.");
					cmd.setExecutor(getHandler(struct[0], true));
				}
				else{
					System.out.println("\t\tMissing PluginCommand.");
					continue;
				}
				
				CommandHandler handler = getHandler(struct);
				handler.setMethod(commandMethod);
				
				System.out.println("\t\tSuccessfully registered command.");
			}
			catch(DevinException e) {
				e.printStackTrace();
			}
		}
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
