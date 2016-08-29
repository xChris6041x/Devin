package io.xchris6041x.devin.commands;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

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
		for(Method method : commandable.getClass().getMethods()) {
			try {
				CommandMethod commandMethod = CommandMethod.build(commandable, method);
				Command command = commandMethod.getCommandAnnotation();
				
				String[] struct = command.struct().split(" ");
				
				// Register with bukkit, the first part of the structure (if not registered).
				PluginCommand cmd = plugin.getCommand(struct[0]);
				if(cmd != null && cmd.getExecutor() == null) {
					cmd.setExecutor(getHandler(struct[0]));
				}
				
				CommandHandler handler = getHandler(command.struct().split(" "));
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
			return container.getHandler(structure.get(0));
		}
		else{
			String next = structure.get(0);
			structure.remove(0);
			
			return getHandler(getHandler(next), structure);
		}
	}
	
}
