package io.xchris6041x.devin.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * @author Christopher Bishop
 */
public class LayeredCommandExecutor implements CommandExecutor {
	
	private CommandExecutor executor;
	private Map<String, LayeredCommandExecutor> layers = new HashMap<String, LayeredCommandExecutor>();
	
	
	public LayeredCommandExecutor() { }
	public LayeredCommandExecutor(CommandExecutor executor) {
		this.executor = executor;
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length > 0) {
			// Search through each layer to find a matching layer.
			for(Entry<String, LayeredCommandExecutor> layer : layers.entrySet()) {
				if(args[0].equalsIgnoreCase(layer.getKey())) {
					// If a layer matches, build new args without the first element (the sub-command name).
					String[] newArgs = new String[args.length - 1];
					for(int i = 1; i < args.length; i++) {
						newArgs[i - 1] = args[i];
					}
					
					// Execute the next layered command executor with modified label and args.
					return layer.getValue().onCommand(sender, cmd, label + " " + args[0], newArgs);
				}
			}
		}
		
		if(executor == null)
			return false;
		else
			return executor.onCommand(sender, cmd, label, args);
	}
	
	
	/**
	 * @return the executer that is used when none of the layers a present.
	 */
	public CommandExecutor getExecutor() {
		return executor;
	}
	
	/**
	 * Set the executor that is used when none of the layers are present.
	 * @param executor - The executor.
	 * @return This LayeredCommandExecutor for chaining.
	 */
	public LayeredCommandExecutor setExecutor(CommandExecutor executor) {
		this.executor = executor;
		return this;
	}
	
	/**
	 * Add a new command layer (sub command).
	 * @param label - The label the new layer will use when called.
	 * @param executor - The LayredCommandExecutor which will be executed when the first argument is the label.
	 * @return This LayeredCommandExecutor for chaining.
	 */
	public LayeredCommandExecutor addLayer(String label, LayeredCommandExecutor executor) {
		layers.put(label, executor);
		return this;
	}
	
	/**
	 * Add a new command layer (sub command).
	 * @param label - The label the new layer will use when called.
	 * @param executor - The CommandExecutor which will be executed when the first argument is the label.
	 * @return This LayeredCommandExecutor for chaining.
	 */
	public LayeredCommandExecutor addLayer(String label, CommandExecutor executor) {
		layers.put(label, new LayeredCommandExecutor(executor));
		return this;
	}
	
	/**
	 * @return all the layers with their name.
	 */
	public Map<String, LayeredCommandExecutor> getLayerMap() {
		return layers;
	}
	
}
