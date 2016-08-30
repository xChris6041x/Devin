package io.xchris6041x.devin.commands;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
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
	
	public void registerCommand(Commandable commandable) {
		System.out.println("[DEVIN] Registering " + commandable.getClass().getSimpleName() + ": ");
		for(Method method : commandable.getClass().getMethods()) {
			System.out.println("[DEVIN] \tAttempting to register " + method.getName() + "...");
			if(method.getAnnotation(Command.class) == null) continue;
			try {
				CommandMethod commandMethod = CommandMethod.build(commandable, method);
				
				System.out.println("[DEVIN] \t\tSuccessfully created CommandMethod.");
				Command command = commandMethod.getCommandAnnotation();
				
				System.out.print("[DEVIN] \t\tUsing structure \"" + command.struct() + "\" to find handler.");
				String[] struct = command.struct().split(" ");
				
				// Register with bukkit, the first part of the structure (if not registered).
				PluginCommand cmd = plugin.getCommand(struct[0]);
				if(cmd != null) {
					System.out.println("[DEVIN] \t\tFound PluginCommand.");
					cmd.setExecutor(getHandler(struct[0], true));
				}
				else{
					System.out.println(ChatColor.RED + "[DEVIN] \t\tMissing PluginCommand.");
				}
				
				CommandHandler handler = getHandler(struct);
				handler.setMethod(commandMethod);
			}
			catch(DevinException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private CommandHandler getHandler(String[] structure) {
		return getHandler(this, Arrays.asList(structure));
	}
	private CommandHandler getHandler(CommandHandlerContainer container, List<String> structure) {
		if(structure.size() == 1) {
			return container.getHandler(structure.get(0), true);
		}
		else{
			String next = structure.get(0);
			structure.remove(0);
			
			return getHandler(getHandler(next, true), structure);
		}
	}
	
}
